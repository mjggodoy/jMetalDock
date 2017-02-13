package es.uma.khaos.docking_service.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.response.TaskRunResponse;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/runTask")
public class RunTaskResource extends Application {
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJson(@NotNull @QueryParam("id") Integer id) throws DatabaseException {
				
		System.out.println("id: " + id );

		try {
			
			if (id == null || id.equals("")) {
				
				return Response.status(Response.Status.FORBIDDEN)
						.entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();			
		
			}else{
				
				Result result = DatabaseService.getInstance().getResult(id);
				TaskRunResponse taskRunResponse = new TaskRunResponse(id, result.getFinalBindingEnergy(),result.getObjectives(), result.getExecutionTaskId());
				
				return Response.ok(taskRunResponse).build();
			
			}
		
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.serverError().build();

		}	
		

	}
}
