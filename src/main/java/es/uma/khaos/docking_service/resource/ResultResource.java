package es.uma.khaos.docking_service.resource;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.*;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.response.PojoResponseBuilder;
import es.uma.khaos.docking_service.response.ResponseBuilder;
import es.uma.khaos.docking_service.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="Result")
@Path("/task")
public class ResultResource extends AbstractResource {

	@GET
	@Path("/{taskId}/result")
	@ApiOperation(value = "Get results from a task id",
	notes= "This method returns the resuls from a task id",
	response = Results.class)
	@ApiResponses(value ={
			@ApiResponse(code = 403, 
					message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, 
					message = "Internal server error")
	})	
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response getResults(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/results.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getResultsResponse(taskId, token, builder, errorBuilder);
	}
	
	@GET
	@Path("/{taskId}/result/minimumEnergy")
	@ApiOperation(value = "Get the minimum final binding energy score from the results")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response getMinimumEnergy(
			@ApiParam(value = "Task id: the identification number of a task", required = true)  @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers){
		ResponseBuilder builder = getResponseBuilder(headers, "/minimunEnergy.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getMinimumEnergy(taskId, token, builder, errorBuilder);	
	}
	

	@GET
	@Path("/{taskId}/result/{run}")
	@ApiOperation(value = "Get results from a given run",
	notes= "This method returns the results obtained from a given run specified by the user.",
	response = Result.class)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	@ApiResponses(value ={
			@ApiResponse(code = 403, 
					message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, 
					message = "Internal server error")
	})
	public Response getResult(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Number of task runs", required = true) @NotNull @PathParam("run") int run,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/result.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getResultResponse(taskId, run, token, builder, errorBuilder);
	}

	@GET
	@Path("/{taskId}/result/{run}/{solutionId}")
	@ApiOperation(value = "Get a solution from a solution id, run and task id",
	notes= "This method returns a specific solution in which the final binding energy, the objectives to minimize such as "
			+ "the intermolecular, intramolecular energies and the RMSD score are shown",
	response = Solution.class)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response getSolution(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Number of runs executed for a task", required = true) @NotNull @PathParam("run") int run,
			@ApiParam(value = "Solution id from a set of results returned by the algorithm", required = true) @NotNull @PathParam("solutionId") int solutionId,
			@ApiParam(value = "Token: a code related to a task", required = true)  @QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/solution.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getSolutionResponse(taskId, solutionId, token, builder, errorBuilder);
	}

	@GET
	@Path("/{taskId}/result/{run}/{solutionId}/pdbqt")
	@ApiOperation(value = "Get the .pdbqt file from a solution by introducing the task id, run and the solution id",
	notes= "With the PDBQT file the user can visualize using a software")
	@Produces({MediaType.TEXT_PLAIN})
	public Response getPdbqt(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Number of runs executed for a task", required = true) @NotNull @PathParam("run") int run,
			@ApiParam(value = "Solution id from a set of results returned by the algorithm", required = true) @NotNull @PathParam("solutionId") int solutionId,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = new PojoResponseBuilder();
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getPdbqtResponse(taskId, solutionId, token, builder, errorBuilder);

	}
	
	//TODO: Código repetido. Mí no gusta.

	private Response getResultsResponse(int taskId, String token, ResponseBuilder builder,
										ResponseBuilder errorBuilder) {
		try{
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.FORBIDDEN,
								Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN
				);
			}else{
				List<Result> resultList = getResultsFromTask(task.getId());
				Results results = new Results(resultList);
				return builder.buildResponse(results);
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}

	private List<Result> getResultsFromTask(int taskId) throws DatabaseException {
		// TODO: Hacerlo en una sóla consulta
		List<Result> resultList = DatabaseService.getInstance().getResults(taskId);
		for (Result result : resultList) {
			result.setSolutions(getSolutionsFromResult(result.getId()));
		}
		return resultList;
	}

	private Response getResultResponse(int taskId, int run, String token, ResponseBuilder builder,
									   ResponseBuilder errorBuilder) {
		try{
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.FORBIDDEN,
								Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN
				);
			}else{
				
				Result result = getResultFromTask(taskId, run);
				return builder.buildResponse(result);
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}

	private Response getSolutionResponse(int taskId, int solutionId, String token,
								 ResponseBuilder builder, ResponseBuilder errorBuilder) {
		try{

			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.FORBIDDEN,
								Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN
				);
			}else{

				IndividualSolution solution =
						DatabaseService.getInstance().getSolution(taskId, solutionId);
				return builder.buildResponse(solution);
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}
	
	
	private Response getMinimumEnergy(int taskId, String token, ResponseBuilder builder, ResponseBuilder errorBuilder){
		
		try{
			
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);			
			if (task == null || !task.getToken().equals(token)) {
			
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.FORBIDDEN,
								Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);

			}else{
				
				IndividualSolution solution = DatabaseService.getInstance().getMinimumEnergyfromResult(taskId);
				return builder.buildResponse(solution);
				
			}
		
		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}
	
	

	private Response getPdbqtResponse(int taskId, int solutionId, String token,
										 ResponseBuilder builder, ResponseBuilder errorBuilder) {
		try{

			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(
								Response.Status.FORBIDDEN,
								Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN
				);
			}else{

				IndividualSolution solution =
						DatabaseService.getInstance().getSolution(taskId, solutionId);
				return builder.buildResponse(solution.getPdbqt());
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}

	private Result getResultFromTask(int taskId, int run) throws DatabaseException {
		// TODO: Hacerlo en una sóla consulta
		Result result = DatabaseService.getInstance().getResult(taskId, run);
		result.setSolutions(getSolutionsFromResult(result.getId()));
		return result;
	}

	private List<Solution> getSolutionsFromResult(int resultId) throws DatabaseException {
		return DatabaseService.getInstance().getSolutionsFromResult(resultId);
	}
}
