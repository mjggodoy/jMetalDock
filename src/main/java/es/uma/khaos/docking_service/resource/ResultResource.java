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

@Api(value = "Result")
@Path("/task")
public class ResultResource extends AbstractResource {

	@GET
	@Path("/{taskId}/result")
	@ApiOperation(value = "Get results from a task id", notes = "This method returns the resuls from a task id", response = Results.class)
	@ApiResponses(value = { @ApiResponse(code = 403, message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
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
	@ApiOperation(value = "Get the minimum final binding energy score", notes = "This method returns the minimun final binding energy score", response = Solution.class)
	@ApiResponses(value = { @ApiResponse(code = 403, message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
	public Response getMinimumEnergy(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/solutionMinimum.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getMinimumEnergy(taskId, token, builder, errorBuilder);
	}
	
		//TODO: Produces JSON y XML?
		//TODO: Comprobar que posible
	@GET
	@Path("/{id}/result/{run}/{solutionId}/pv")
	@ApiResponses(value = { @ApiResponse(code = 403, message = "You are not allowed to see this task"),
	@ApiResponse(code = 500, message = "Internal server error") })
	@ApiOperation(value = "This method returns the macro page in .jsp", notes = "This method return the macro page in .jsp", response = Macro.class)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
	public Response getMacroPage(
			@ApiParam(value = "Number of runs executed for a task", required = true) @NotNull @PathParam("run") int run,
			@ApiParam(value = "Solution id from a set of results returned by the algorithm", required = true) @NotNull @PathParam("solutionId") int solutionId,
			@ApiParam(value = "Task id: identification of the task's number", required = true) @NotNull @PathParam("id") int id,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) throws DatabaseException {
			ResponseBuilder builder = getResponseBuilder(headers, "/macro.jsp");
			ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
			return getSolutionResponse(id, solutionId, token, builder, errorBuilder);
	}

	@GET
	@Path("/{taskId}/result/minimumRMSDscore")
	@ApiOperation(value = "Get the minimum RMSD score from the results", notes = "This method returns the lowest RMSD score from the results", response = Solution.class)
	@ApiResponses(value = { @ApiResponse(code = 403, message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
	public Response getMinimumRMSD(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/solution.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getMinimumRMSD(taskId, token, builder, errorBuilder);

	}

	@GET
	@Path("/{taskId}/result/{run}")
	@ApiOperation(value = "Get results from a given run", notes = "This method returns the results obtained from a given run specified by the user.", response = Result.class)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
	@ApiResponses(value = { @ApiResponse(code = 403, message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, message = "Internal server error") })
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
	@ApiOperation(value = "Get a solution from a solution id, run and task id", notes = "This method returns a specific solution in which the final binding energy, the objectives to minimize such as "
			+ "the intermolecular, intramolecular energies and the RMSD score are shown", response = Solution.class)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
	public Response getSolution(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Number of runs executed for a task", required = true) @NotNull @PathParam("run") int run,
			@ApiParam(value = "Solution id from a set of results returned by the algorithm", required = true) @NotNull @PathParam("solutionId") int solutionId,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) {
		ResponseBuilder builder = getResponseBuilder(headers, "/solution.jsp");
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getSolutionResponse(taskId, solutionId, token, builder, errorBuilder);
	}

	@GET
	@Path("/{taskId}/result/{run}/{solutionId}/pdbqt")
	@ApiOperation(value = "Get the .pdbqt file from a solution by introducing the task id, run and the solution id", notes = "With the PDBQT file the user can visualize using a software")
	@Produces({ MediaType.TEXT_PLAIN })
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

	@GET
	@Path("/{taskId}/result/{run}/{solutionId}/pdbqtMacro")
	@ApiOperation(value = "Get the .pdbqt with macro", notes = "Get the .pdbqt with macro from a solution by introducing the task id, run and the solution id")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getPdbqtMacro(
			@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("taskId") int taskId,
			@ApiParam(value = "Number of runs executed for a task", required = true) @NotNull @PathParam("run") int run,
			@ApiParam(value = "Solution id from a set of results returned by the algorithm", required = true) @NotNull @PathParam("solutionId") int solutionId,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) {

		ResponseBuilder builder = new PojoResponseBuilder();
		ResponseBuilder errorBuilder = getResponseBuilder(headers, "/errorResponse.jsp");
		return getPdbqtMacroResponse(taskId, solutionId, token, builder, errorBuilder);

	}

	@GET
	@Path("/{id}/result/{run}/{solutionId}/macro")
	@ApiResponses(value = { @ApiResponse(code = 403, message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@ApiOperation(value = "Get the macro file in .pdb format by id and token", notes = "This method gets the macromolecule in .pdb format", response = Macro.class)
	// @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getMacro(
			@ApiParam(value = "Task id: identification of the task's number", required = true) @NotNull @PathParam("id") int id,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) throws DatabaseException {

		return getMacroTxt(id, token);
	}

	@GET
	@Path("/{id}/result/{run}/{solutionId}/ligand")
	@ApiResponses(value = { @ApiResponse(code = 403, message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@ApiOperation(value = "This method gets the ligand ID", notes = "This method gets the ligand ID that will be used to display on the page", response = Ligand.class)
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getLigand(
			@ApiParam(value = "Task id: identification of the task's number", required = true) @NotNull @PathParam("id") int id,
			@ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
			@Context HttpHeaders headers) throws DatabaseException {

		return getLigandTxt(id, token);
	}

	

	private Response getMacroTxt(int id, String token) throws DatabaseException {

		Task task = DatabaseService.getInstance().getTaskParameter(id);

		try {

			if (task == null || !task.getToken().equals(token)) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();

			} else {

				Macro macro = DatabaseService.getInstance().getMacroFile(id);
				return Response.ok(macro.getMacro()).build();
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(Constants.RESPONSE_INTERNAL_SERVER_ERROR).build();
		}
	}

	private Response getLigandTxt(int id, String token) throws DatabaseException {

		Task task = DatabaseService.getInstance().getTaskParameter(id);

		try {

			if (task == null || !task.getToken().equals(token)) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();
			} else {

				Ligand ligand = DatabaseService.getInstance().getLigand(id);

				return Response.ok(ligand.getLigand()).build();

			}

		} catch (DatabaseException e) {

			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(Constants.RESPONSE_INTERNAL_SERVER_ERROR).build();
		}

	}

	/*private Response getMacroResponse(int id, String token, ResponseBuilder builder, ResponseBuilder errorBuilder)
			throws DatabaseException {

		Task task = DatabaseService.getInstance().getTaskParameter(id);

		try {

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);

			} else {

				IndividualSolution solution = DatabaseService.getInstance().getSolution(id, solutionId);
				return builder.buildResponse(solution);
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}
*/
	// TODO: Código repetido. Mí no gusta.

	private Response getResultsResponse(int taskId, String token, ResponseBuilder builder,
			ResponseBuilder errorBuilder) {
		try {
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);
			} else {
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
		try {
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);
			} else {

				Result result = getResultFromTask(taskId, run);
				return builder.buildResponse(result);
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}

	private Response getSolutionResponse(int taskId, int solutionId, String token, ResponseBuilder builder,
			ResponseBuilder errorBuilder) {
		try {

			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);
			} else {

				IndividualSolution solution = DatabaseService.getInstance().getSolution(taskId, solutionId);
				return builder.buildResponse(solution);
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}

	private Response getMinimumEnergy(int taskId, String token, ResponseBuilder builder, ResponseBuilder errorBuilder) {

		try {

			Task task = DatabaseService.getInstance().getTaskParameter(taskId);
			if (task == null || !task.getToken().equals(token)) {

				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);

			} else {

				IndividualSolution solution = DatabaseService.getInstance().getMinimumEnergyfromResult(taskId);
				return builder.buildResponse(solution);

			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}

	private Response getMinimumRMSD(int taskId, String token, ResponseBuilder builder, ResponseBuilder errorBuilder) {

		try {

			Task task = DatabaseService.getInstance().getTaskParameter(taskId);
			if (task == null || !task.getToken().equals(token)) {

				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);

			} else {

				IndividualSolution solution = DatabaseService.getInstance().getMinimunRMSD(taskId);
				return builder.buildResponse(solution);
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}

	}

	private Response getPdbqtResponse(int taskId, int solutionId, String token, ResponseBuilder builder,
			ResponseBuilder errorBuilder) {
		try {

			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {
				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);
			} else {

				IndividualSolution solution = DatabaseService.getInstance().getSolution(taskId, solutionId);
				return builder.buildResponse(solution.getPdbqt());
			}

		} catch (DatabaseException e) {
			e.printStackTrace();
			return internalServerError(errorBuilder);
		}
	}

	private Response getPdbqtMacroResponse(int taskId, int solutionId, String token, ResponseBuilder builder,
			ResponseBuilder errorBuilder) {

		try {

			Task task = DatabaseService.getInstance().getTaskParameter(taskId);

			if (task == null || !task.getToken().equals(token)) {

				return errorBuilder.buildResponse(
						new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED),
						Response.Status.FORBIDDEN);
			} else {

				IndividualSolution solution = DatabaseService.getInstance().getSolution(taskId, solutionId);
				Macro macro = DatabaseService.getInstance().getMacroFile(taskId);

				return builder.buildResponse(macro.getMacro() + solution.getPdbqt());
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
