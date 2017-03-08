package es.uma.khaos.docking_service.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.mysql.jdbc.StringUtils;

import es.uma.khaos.docking_service.autodock.WorkerThread;
import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.ErrorResponse;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.response.JspResponseBuilder;
import es.uma.khaos.docking_service.response.PojoResponseBuilder;
import es.uma.khaos.docking_service.response.ResponseBuilder;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;
import es.uma.khaos.docking_service.utils.Utils;

@Path("/task")
public class TaskResource extends Application {
	
	private final String BASE_FOLDER = Constants.DIR_BASE;
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJsonOrXml(@NotNull @PathParam("id") int id,  @QueryParam("token") String token) throws DatabaseException {
		return getTaskResponse(id, token, new PojoResponseBuilder());
	}
	
	@GET
	@Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response doGetAsHtml(@NotNull @PathParam("id") int id, @QueryParam("token") String token) throws DatabaseException {
		// TODO: Sacar path del jsp a fichero de propiedades (PRIVATE)
		return getTaskResponse(id, token, new JspResponseBuilder("/task.jsp"));
    }
	
	// TODO: Tratar error de FTP y tratar instancia no existente
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doPost(
			@QueryParam("algorithm") String algorithm,
			@QueryParam("runs") @DefaultValue("2") int runs,
			@QueryParam("population_size") @DefaultValue("150") int populationSize, 
			@QueryParam("evaluations") @DefaultValue("1500000") int evaluations,
			@QueryParam("use_rmsd_as_obj") @DefaultValue("False") boolean useRmsdAsObjective,
			@QueryParam("instance") String instance) {
		System.out.println("HERE I AM!");
		
		try {
			String token = Utils.generateHash();
			ParameterSet params;
			
			if (instance==null) {
				String zipFile = BASE_FOLDER + token + ".zip";
				String zipTestFile = Constants.TEST_DIR_INSTANCE + Constants.TEST_FILE_ZIP;
				Utils.copyFile(zipTestFile, zipFile);
				params = new ParameterSet(0, algorithm, evaluations, populationSize,
						runs, useRmsdAsObjective, 0, Constants.TEST_FILE_ZIP, zipFile);
			} else {
				params = new ParameterSet(0, algorithm, evaluations, populationSize,
						runs, useRmsdAsObjective, 0, instance);
			}
			
			return createTaskResponse(token, params);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response doPost(
			@QueryParam("algorithm") String algorithm,
			@QueryParam("runs") @DefaultValue("2") int runs,
			@QueryParam("population_size") @DefaultValue("150") int populationSize, 
			@QueryParam("evaluations") @DefaultValue("1500000") int evaluations,
			@QueryParam("use_rmsd_as_obj") @DefaultValue("False") boolean useRmsdAsObjective,
			@FormDataParam("file") final FormDataContentDisposition fileDetails,
			@FormDataParam("file") final InputStream inputStream) throws IOException {
		System.out.println("HERE I STAY!");
		
		try {
			String token = Utils.generateHash();
			String zipFile = BASE_FOLDER + token + ".zip";
			Utils.saveFile(inputStream, zipFile);
			
			ParameterSet params = new ParameterSet(0, algorithm, evaluations, populationSize,
					runs, useRmsdAsObjective, 0, fileDetails.getName(), zipFile);
			return createTaskResponse(token, params);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	private Response getTaskResponse(int id, String token, ResponseBuilder builder) {
		try{

			Task task = DatabaseService.getInstance().getTaskParameter(id);
		
			if (task == null || !task.getToken().equals(token)) {
				return Response
						.status(Response.Status.FORBIDDEN)
						.entity(new ErrorResponse(Response.Status.FORBIDDEN,Constants.RESPONSE_TASK_MSG_UNALLOWED))
						.build();			
			}else{
				return builder.buildResponse(task);
			}
		
		}catch (NumberFormatException e) {
			
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(Constants.RESPONSE_NOT_A_NUMBER_ERROR).build();
		
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	private Response createTaskResponse(String token, ParameterSet params) {
		
		try{
			if (StringUtils.isNullOrEmpty(params.getAlgorithm())) {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity(new ErrorResponse(
								Response.Status.BAD_REQUEST,
								String.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR, "algorithm")))
						.build();			
			} else if (params.getObjectiveOption()==0) {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity(new ErrorResponse(
								Response.Status.BAD_REQUEST,
								String.format(Constants.RESPONSE_NOT_VALID_PARAMETER_ERROR, "algorithm")))
						.build();
			} else {
				Task task = createTask(token, params);
				return Response.ok(task).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
//	private Task createTask(int popSize, int evals, int runs, String algorithm, int objectiveOpt,
//			String zipFile) throws Exception {
	private Task createTask(String token, ParameterSet params) throws Exception {

		Task task = DatabaseService.getInstance().insertTask(token);
		params.setTaskId(task.getId());
		
		ParameterSet parameters = DatabaseService.getInstance().insertParameter(params);
		task.setParameters(parameters);
		
		Runnable worker = new WorkerThread("DOCKING", task);
		ThreadPoolService.getInstance().execute(worker);
		
		return task;
	}
	
}
