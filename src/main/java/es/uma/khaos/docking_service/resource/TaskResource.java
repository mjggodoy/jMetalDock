package es.uma.khaos.docking_service.resource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;
import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/task2/{id}")
public class TaskResource extends Application {
	
	//TODO: Tratar mejor las excepciones
	@GET
	@Path("{token}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJson(@NotNull @PathParam("id") int id,  @PathParam("token") String token) throws DatabaseException {
		
		try{
		
				Task task = DatabaseService.getInstance().getTaskParameter(id);
			
				if (task == null || !task.getHash().equals(token)) {
				
					return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();			
			
				}else{
					
					return Response.ok(task).build();
				}
			
		
		}catch (NumberFormatException e) {
			
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(Constants.RESPONSE_NOT_A_NUMBER_ERROR).build();
		
		} catch (DatabaseException e) {
			
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
	@GET
    @Produces("text/html")
    public Response index(@NotNull @PathParam("id") int id, @PathParam("token") String token) throws DatabaseException {
		
		try{
				Task task = DatabaseService.getInstance().getTaskParameter(id);
			
				if (task == null || !task.getHash().equals(token)) {
			

					return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();			
			
				}else{
					 

					Viewable v = new Viewable("/task.jsp", task);
				    Response resp = Response.ok(v).build();
				    return resp;
				}
			
		
		}catch (NumberFormatException e) {
			
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(Constants.RESPONSE_NOT_A_NUMBER_ERROR).build();
		
		} catch (DatabaseException e) {
			
			e.printStackTrace();
			return Response.serverError().build();
		}	   
    }
    
}
