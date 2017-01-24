package es.uma.khaos.docking_service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/task2")
@Produces("text/plain")
public class TaskResource {
	
	@GET
	public String doGetAsPlainText() {
		return "HELLO PUA";
	}

}
