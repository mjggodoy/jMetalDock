package es.uma.khaos.docking_service.resource;

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
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.response.JspResponseBuilder;
import es.uma.khaos.docking_service.response.PojoResponseBuilder;
import es.uma.khaos.docking_service.response.ResponseBuilder;
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/task/{id}")
public class TaskResource extends Application {
	
	private Response getTask(int id, String token, ResponseBuilder builder) {
		
		try{
			Task task = DatabaseService.getInstance().getTaskParameter(id);
			
			System.out.println(task);
			System.out.println(token);
			System.out.println(task.getHash());
			
			if (task == null || !task.getHash().equals(token)) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();			
			}else{
				return builder.buildResponse(task);
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJson(@NotNull @PathParam("id") int id,  @QueryParam("token") String token) throws DatabaseException {
		return getTask(id, token, new PojoResponseBuilder());
	}
	
	
	@GET
    @Produces("text/html")
    public Response index(@NotNull @PathParam("id") int id, @QueryParam("token") String token) throws DatabaseException {
		// TODO: Sacar path del jsp a fichero de propiedades (PRIVATE)
		return getTask(id, token, new JspResponseBuilder("/task.jsp"));
    }
    
}
