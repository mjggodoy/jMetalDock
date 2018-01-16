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
import javax.ws.rs.core.*;

import es.uma.khaos.docking_service.model.StandardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.mysql.jdbc.StringUtils;

import es.uma.khaos.docking_service.autodock.WorkerThread;
import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.ErrorResponse;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.response.ResponseBuilder;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;
import es.uma.khaos.docking_service.utils.Utils;

@Api(value="Task")
@Path("/task")
public class TaskResource extends AbstractResource {
	
	private final String BASE_FOLDER = Constants.DIR_BASE;
	
	@GET
	@Path("/{id}")
    @ApiResponse(code = 403, message = "You are not allowed to see this task")
	@ApiOperation(value = "Get a task by id and token",
	notes="Get a task with the corresponding id, task, state, "
			+ "start and end times and the parameters that were set for the algorithm execution",
	response = Task.class)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response doGet(
			@NotNull @PathParam("id") int id,
			@ApiParam(value = "task's id", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) throws DatabaseException {
		
		ResponseBuilder builder = getResponseBuilder(headers, "/task.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getTaskResponse(id, token, builder, errorBuilder);
	}
	
	// TODO: Tratar error de FTP y tratar instancia no existente
	@POST
	@ApiOperation(value = "Post a task ",
	notes="Post a task in which the algorithm and its parameters are set up"  )
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiResponse(code = 500, message = "Internal server error")
	public Response doPost(
			@ApiParam(value = "algorithm", required = true) @FormDataParam("algorithm") String algorithm,
			@ApiParam(value = "number of run for each executions", required = false) @FormDataParam("runs") @DefaultValue("1") int runs,
			@ApiParam(value = "population or swarm size", required = false) @FormDataParam("population_size") @DefaultValue("150") int populationSize, 
			@ApiParam(value = "number of evaluations", required = false) @FormDataParam("evaluations") @DefaultValue("1500000") int evaluations,
			@ApiParam(value = "RMSD as objective to optimize", required = false) @FormDataParam("use_rmsd_as_obj") @DefaultValue("false") boolean useRmsdAsObjective,
			@ApiParam(value = "Instance selected from the set of instances provided", required = false) @FormDataParam("instance") String instance,
			@ApiParam(value = "User's email", required = false) @FormDataParam("email") String email,
			@ApiParam(value = "file") @FormDataParam("file") final FormDataContentDisposition fileDetails,
			@ApiParam(value = "file") @FormDataParam("file") final InputStream inputStream,
			@Context HttpHeaders headers,
			@Context UriInfo uriInfo) throws IOException {

		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");

		try {
			
			String token = Utils.generateHash();
			if ("".equals(email)) email = null;
			ParameterSet params;
			
			if (fileDetails != null && fileDetails.getFileName() != null && !fileDetails.getFileName().equals("")) {
				
				String zipFile = BASE_FOLDER + token + ".zip";
				Utils.saveFile(inputStream, zipFile);
				params = new ParameterSet(0, algorithm, evaluations, populationSize,
						runs, useRmsdAsObjective, 0, fileDetails.getName(), zipFile);
				
			} else {
				
				params = new ParameterSet(0, algorithm, evaluations, populationSize,
						runs, useRmsdAsObjective, 0, instance);
				
			}
			
			if(inputStream == null && instance == null){
				return internalServerError(errorBuilder);
			}

			ResponseBuilder builder = getResponseBuilder(headers, "/standardResponse.jsp");
			return createTaskResponse(token, params, email, uriInfo, builder, errorBuilder);

		
		} catch (Exception e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}
	
	private Response getTaskResponse(int id, String token, ResponseBuilder builder, ResponseBuilder errorBuilder) {
		try{

			Task task = DatabaseService.getInstance().getTaskParameter(id);
		
			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.FORBIDDEN,
								Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN
				);

			}else{
				return builder.buildResponse(task);
			}
		
		}catch (NumberFormatException e) {

			return errorBuilder.buildResponse(
					new ErrorResponse(
							Response.Status.NOT_ACCEPTABLE,
							Constants.RESPONSE_NOT_A_NUMBER_ERROR),
					Response.Status.NOT_ACCEPTABLE
			);

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}
	
	private Response createTaskResponse(
			String token, ParameterSet params, String email, UriInfo uriInfo,
			ResponseBuilder builder, ResponseBuilder errorBuilder) {
		
		try{
			if (StringUtils.isNullOrEmpty(params.getAlgorithm())) {
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.BAD_REQUEST,
								String.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR, "algorithm")),
						Response.Status.BAD_REQUEST
						);
			} else if (params.getObjectiveOption()==0) {
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.BAD_REQUEST,
								String.format(Constants.RESPONSE_NOT_VALID_PARAMETER_ERROR, "algorithm")),
						Response.Status.BAD_REQUEST
				);
			} else {
				Task task = createTask(token, params, email);
				Object o = new StandardResponse(
						Response.Status.CREATED,
						Constants.RESPONSE_TASK_CREATED,
						String.format("%s/%s?token=%s",uriInfo.getAbsolutePath().toString(),
								task.getId(), task.getToken()));
				return builder.buildCreatedResponse(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}
	
	private Task createTask(String token, ParameterSet params, String email) throws Exception {

		Task task = DatabaseService.getInstance().insertTask(token, email);
		params.setTaskId(task.getId());
		
		ParameterSet parameters = DatabaseService.getInstance().insertParameter(params);
		task.setParameters(parameters);
		
		Runnable worker = new WorkerThread("DOCKING", task);
		ThreadPoolService.getInstance().execute(worker);
		
		return task;
	}

}
