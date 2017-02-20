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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Execution;
import es.uma.khaos.docking_service.model.Solution;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/runTask")
public class RunTaskResource extends Application {
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJson(@NotNull @QueryParam("id") Integer id, @QueryParam("token") String token, 
			@DefaultValue("0") @QueryParam("run") Integer run) throws DatabaseException {
			
		try {
			
			Task task = DatabaseService.getInstance().getTaskParameter(id);

			if (task == null || !task.getHash().equals(token)) {

				return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();			

			}else{
				
				if(run==0){
					
					System.out.println("RUN == 0");
				
					List<Solution> results = new ArrayList<Solution>();
					List<Execution> executions  = DatabaseService.getInstance().getExecutions(id);
					
					// TODO: Cambiar a una sóla consulta
					for(Execution execution: executions){
						
						System.out.println(execution);
					
						int executionId = execution.getId();
						Solution result = DatabaseService.getInstance().getResultByExecutionId(executionId);
						results.add(result);
						System.out.println(result);
					}
					
					System.out.println("AQUI");
					GenericEntity<List<Solution>> entity = new GenericEntity<List<Solution>>(results) {};
					System.out.println(entity);
					
					List<String> list = new ArrayList<String>();
					list.add("PUA");
					list.add("XUXA");
					GenericEntity<List<String>> entity2 = new GenericEntity<List<String>>(list) {};
					
					return Response.ok(entity).build();
				
				}else{
					
					System.out.println("RUN == OTRA COSA");
					
					Execution execution = DatabaseService.getInstance().getExecution(id, run);
					int executionId = execution.getId();
					Solution result = DatabaseService.getInstance().getResult(executionId);
					
					return Response.ok(result).build();
					
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}	

	}
}
