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
import es.uma.khaos.docking_service.model.ErrorResponse;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Results;
import es.uma.khaos.docking_service.model.Solution;
import es.uma.khaos.docking_service.model.Task;
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
	
	//TODO: Sacar los GETS de HTML
	
	
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
			return errorBuilder.buildResponse(
					new ErrorResponse(
							Response.Status.INTERNAL_SERVER_ERROR,
							Constants.RESPONSE_INTERNAL_SERVER_ERROR),
					Response.Status.INTERNAL_SERVER_ERROR
			);
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
			return errorBuilder.buildResponse(
					new ErrorResponse(
							Response.Status.INTERNAL_SERVER_ERROR,
							Constants.RESPONSE_INTERNAL_SERVER_ERROR),
					Response.Status.INTERNAL_SERVER_ERROR
			);
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
