package es.uma.khaos.docking_service.response;

import javax.ws.rs.core.Response;

public interface ResponseBuilder {
	
	public Response buildResponse(Object o);

}
