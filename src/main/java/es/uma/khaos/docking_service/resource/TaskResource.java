package es.uma.khaos.docking_service.resource;

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
import es.uma.khaos.docking_service.service.DatabaseService;

@Path("/task2/{id}")
public class TaskResource extends Application {
	
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public String doGetAsPlainText() {
//		return "HELLO PUA";
//	}
	
	//TODO: Tratar mejor las excepciones
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJson(@PathParam("id") int id) throws DatabaseException {
		Task task = DatabaseService.getInstance().getTaskParameter(id);
		return Response.ok(task).build();
	}
	
	@GET
    @Produces("text/html")
    public Response index(@PathParam("id") int id) throws DatabaseException {
        Task task = DatabaseService.getInstance().getTaskParameter(id);
        Viewable v = new Viewable("/task.jsp", task);
        Response resp = Response.ok(v).build();
        return resp;
    }

}
