package es.uma.khaos.docking_service.resource;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.ErrorResponse;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Results;
import es.uma.khaos.docking_service.model.Solution;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.response.PojoResponseBuilder;
import es.uma.khaos.docking_service.response.ResponseBuilder;
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/task")
public class ResultResource extends Application {
	
	@GET
	@Path("/{taskId}/result")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getResultsAsJsonOrXml(
			@NotNull @PathParam("taskId") int taskId,
			@QueryParam("token") String token) {
		return getResultsResponse(taskId, token, new PojoResponseBuilder());
	}
	
	@GET
	@Path("/{taskId}/result/{run}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getResultAsJsonOrXml(
			@NotNull @PathParam("taskId") int taskId,
			@NotNull @PathParam("run") int run,
			@QueryParam("token") String token) {
		return getResultResponse(taskId, run, token, new PojoResponseBuilder());
	}
	
	//TODO: Sacar los GETS de HTML
	
	private List<Solution> getSolutionsFromResult(int resultId) throws DatabaseException {
		return DatabaseService.getInstance().getSolutionsFromResult(resultId);
	}
	
	private List<Result> getResultsFromTask(int taskId) throws DatabaseException {
		// TODO: Hacerlo en una sóla consulta
		List<Result> resultList = DatabaseService.getInstance().getResults(taskId);
		for (Result result : resultList) {
			result.setSolutions(getSolutionsFromResult(result.getId()));
		}
		return resultList;
	}
	
	private Result getResultFromTask(int taskId, int run) throws DatabaseException {
		// TODO: Hacerlo en una sóla consulta
		Result result = DatabaseService.getInstance().getResult(taskId, run);
		result.setSolutions(getSolutionsFromResult(result.getId()));
		return result;
	}
	
	
	//TODO: Código repetido. Mí no gusta.
	private Response getResultsResponse(int taskId, String token, ResponseBuilder builder) {
		try{
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);
			
			if (task == null || !task.getHash().equals(token)) {
				return Response
						.status(Response.Status.FORBIDDEN)
						.entity(new ErrorResponse(Response.Status.FORBIDDEN,Constants.RESPONSE_TASK_MSG_UNALLOWED))
						.build();			
			}else{
				List<Result> resultList = getResultsFromTask(task.getId());
				Results results = new Results(resultList);
				return builder.buildResponse(results);
			}
		
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	private Response getResultResponse(int taskId, int run, String token, ResponseBuilder builder) {
		try{
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);
			
			if (task == null || !task.getHash().equals(token)) {
				return Response
						.status(Response.Status.FORBIDDEN)
						.entity(new ErrorResponse(Response.Status.FORBIDDEN,Constants.RESPONSE_TASK_MSG_UNALLOWED))
						.build();			
			}else{
				Result result = getResultFromTask(taskId, run);
				return builder.buildResponse(result);
			}
		
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
