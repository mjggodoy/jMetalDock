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
import es.uma.khaos.docking_service.response.JspResponseBuilder;
import es.uma.khaos.docking_service.response.PojoResponseBuilder;
import es.uma.khaos.docking_service.response.ResponseBuilder;
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/task")
public class ResultResource extends AbstractResource {

	@GET
	@Path("/{taskId}/result")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response getResults(
			@NotNull @PathParam("taskId") int taskId,
			@QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/results.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getResultsResponse(taskId, token, builder, errorBuilder);
	}

	@GET
	@Path("/{taskId}/result/{run}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response getResult(
			@NotNull @PathParam("taskId") int taskId,
			@NotNull @PathParam("run") int run,
			@QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/result.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getResultResponse(taskId, run, token, builder, errorBuilder);
	}

	@GET
	@Path("/{taskId}/result/{run}/{solutionId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
	public Response getSolution(
			@NotNull @PathParam("taskId") int taskId,
			@NotNull @PathParam("run") int run,
			@NotNull @PathParam("solutionId") int solutionId,
			@QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/solution.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getSolutionResponse(taskId, solutionId, token, builder, errorBuilder);
	}

	@GET
	@Path("/{taskId}/result/{run}/{solutionId}/pdbqt")
	@Produces({MediaType.TEXT_PLAIN})
	public Response getPdbqt(
			@NotNull @PathParam("taskId") int taskId,
			@NotNull @PathParam("run") int run,
			@NotNull @PathParam("solutionId") int solutionId,
			@QueryParam("token") String token,
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
