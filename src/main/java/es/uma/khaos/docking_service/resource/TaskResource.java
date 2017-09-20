package es.uma.khaos.docking_service.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response doGet(
			@NotNull @PathParam("id") int id,
			@QueryParam("token") String token,
			@Context HttpHeaders headers) throws DatabaseException {
		
		ResponseBuilder builder = getResponseBuilder(headers, "/task.jsp");
		return getTaskResponse(id, token, builder);
	}
	
	// TODO: Tratar error de FTP y tratar instancia no existente
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response doPost(
			@FormDataParam("algorithm") String algorithm,
			@FormDataParam("runs") @DefaultValue("2") int runs,
			@FormDataParam("population_size") @DefaultValue("150") int populationSize, 
			@FormDataParam("evaluations") @DefaultValue("1500000") int evaluations,
			@FormDataParam("use_rmsd_as_obj") @DefaultValue("False") boolean useRmsdAsObjective,
			@FormDataParam("instance") String instance,
			@FormDataParam("file") final FormDataContentDisposition fileDetails,
			@FormDataParam("file") final InputStream inputStream,
			@Context HttpHeaders headers) throws IOException {
		
		ResponseBuilder builder = getResponseBuilder(headers, "/task.jsp");
		
		try {
			String token = Utils.generateHash();
			ParameterSet params;
			
			if (fileDetails != null) {
				
				String zipFile = BASE_FOLDER + token + ".zip";
				Utils.saveFile(inputStream, zipFile);
				params = new ParameterSet(0, algorithm, evaluations, populationSize,
						runs, useRmsdAsObjective, 0, fileDetails.getName(), zipFile);
				
			} else {
				
				params = new ParameterSet(0, algorithm, evaluations, populationSize,
						runs, useRmsdAsObjective, 0, instance);
				
			}
			
			if(inputStream == null && instance == null){
				
				return Response.serverError().build();
				
			}
			
			return createTaskResponse(token, params, builder);
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
	
	private Response createTaskResponse(String token, ParameterSet params, ResponseBuilder builder) {
		
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
				return builder.buildResponse(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	private Task createTask(String token, ParameterSet params) throws Exception {

		Task task = DatabaseService.getInstance().insertTask(token);
		params.setTaskId(task.getId());
		
		ParameterSet parameters = DatabaseService.getInstance().insertParameter(params);
		task.setParameters(parameters);
		
		Runnable worker = new WorkerThread("DOCKING", task);
		ThreadPoolService.getInstance().execute(worker);
		
		return task;
	}
	
	private ResponseBuilder getResponseBuilder(HttpHeaders headers, String jsp) {
	    List<MediaType> acceptableTypes = headers.getAcceptableMediaTypes();
	    if (acceptableTypes.contains(MediaType.TEXT_HTML_TYPE)) {
			return new JspResponseBuilder(jsp);
		} else return new PojoResponseBuilder();
	}
	
}
