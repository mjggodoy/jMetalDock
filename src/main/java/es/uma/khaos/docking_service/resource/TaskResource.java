package es.uma.khaos.docking_service.resource;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

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
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doPost(
			@QueryParam("algorithm") String algorithm,
			@QueryParam("runs") @DefaultValue("2") int runs,
			@QueryParam("population_size") @DefaultValue("150") int populationSize, 
			@QueryParam("evaluations") @DefaultValue("1500000") int evaluations,
			@QueryParam("objectives") @DefaultValue("1") int objectiveOpt,
			@QueryParam("instance") String instance) {
		System.out.println("HERE I AM!");
		
		// TODO: DESCARGAR Y PREPARAR instancia seleccionada
		String zipFile = Constants.TEST_DIR_INSTANCE + Constants.TEST_FILE_ZIP;
		
		return createTaskResponse(populationSize, evaluations, runs, algorithm, objectiveOpt, zipFile);
		
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response doPost(
			@QueryParam("algorithm") String algorithm,
			@QueryParam("runs") @DefaultValue("2") int runs,
			@QueryParam("population_size") @DefaultValue("150") int populationSize, 
			@QueryParam("evaluations") @DefaultValue("1500000") int evaluations,
			@QueryParam("objectives") @DefaultValue("1") int objectiveOpt,
			@FormDataParam("file") final FormDataContentDisposition fileDetails,
			@FormDataParam("file") final InputStream inputStream) throws IOException {
		System.out.println("HERE I STAY!");
		
		// TODO: Autogenerar nombre de zip
		String zipFile = BASE_FOLDER + fileDetails.getFileName();
		Utils.saveFile(inputStream, zipFile);
		
		return createTaskResponse(populationSize, evaluations, runs, algorithm, objectiveOpt, zipFile);
	}
	
	private Response getTaskResponse(int id, String token, ResponseBuilder builder) {
		try{

			Task task = DatabaseService.getInstance().getTaskParameter(id);
		
			if (task == null || !task.getHash().equals(token)) {
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
	
	private Response createTaskResponse(int popSize, int evals, int runs, String algorithm, int objectiveOpt,
			String zipFile) {
		
		try{
			if (StringUtils.isNullOrEmpty(algorithm)) {
				//TODO: EXPECTATION_FAILED no debería ser el error. Cambiarlo por otro. 400, quizá
				return Response
						.status(Response.Status.EXPECTATION_FAILED)
						.entity(new ErrorResponse(
								Response.Status.EXPECTATION_FAILED,
								String.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR, "algorithm")))
						.build();			
			}else{
				runs = inRangeCheck(
						runs,
						Constants.DEFAULT_MIN_NUMBER_RUNS,
						Constants.DEFAULT_MAX_NUMBER_RUNS);
				popSize = inRangeCheck(
						popSize,
						Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE,
						Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE);
				evals = inRangeCheck(
						evals,
						Constants.DEFAULT_MIN_NUMBER_EVALUATIONS,
						Constants.DEFAULT_MAX_NUMBER_EVALUATIONS);
				Task task = createTask(popSize, evals, runs, algorithm, objectiveOpt, zipFile);
				return Response.ok(task).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	private Task createTask(int popSize, int evals, int runs, String algorithm, int objectiveOpt,
			String zipFile) throws Exception {

		Random sr = SecureRandom.getInstance("SHA1PRNG");
		String token = new BigInteger(130, sr).toString(32);

		Task task = DatabaseService.getInstance().insertTask(token);
		ParameterSet parameters = DatabaseService.getInstance().insertParameter(algorithm, evals, popSize, runs, objectiveOpt,
						task.getId());
		task.setParameters(parameters);
		Runnable worker = new WorkerThread("DOCKING", task.getId(), algorithm,
				runs, popSize, evals, objectiveOpt, zipFile);
		ThreadPoolService.getInstance().execute(worker);
		
		return task;
	}
	
	private int inRangeCheck(int value, int minValue, int maxValue) {
		if (value > maxValue) return maxValue;
		else if (value < minValue) return minValue;
		else return value;
	}

}
