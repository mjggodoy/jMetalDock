package es.uma.khaos.docking_service.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Execution;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/runTask")
public class RunTaskResource extends Application {
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJson(@NotNull @QueryParam("id") Integer id, @QueryParam("token") String token, 
			@DefaultValue("0") @QueryParam("run") Integer run) throws DatabaseException {
			
		List<Result> results = new ArrayList<Result>();
		int idExecution = 0;

		try {
			
			Task task = DatabaseService.getInstance().getTaskParameter(id);

			if (task == null || !task.getHash().equals(token)) {

				return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();			

			}else{
				
				if(run==0){
				
					List<Execution> executions  = DatabaseService.getInstance().getExecutions(id);
				
					for(Execution execution: executions){
					
						idExecution = execution.getId();
						Result result = DatabaseService.getInstance().getResult(idExecution);
						results.add(result);
					}
					
					return Response.ok(results).build();

				
				}else{
					
					Execution execution = DatabaseService.getInstance().getExecution(id, run);
					idExecution = execution.getId();
					Result result = DatabaseService.getInstance().getResult(idExecution);
					
					return Response.ok(result).build();

					
				}
				
			}
		
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.serverError().build();

		}	
		

	}
}
