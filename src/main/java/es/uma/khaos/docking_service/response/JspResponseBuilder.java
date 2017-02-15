package es.uma.khaos.docking_service.response;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

public class JspResponseBuilder implements ResponseBuilder {
	
	private String jspFile;
	
	public JspResponseBuilder(String jspFile) {
		this.jspFile = jspFile;
	}

	@Override
	public Response buildResponse(Object o) {
	    return Response.ok(new Viewable(jspFile, o)).build();
	}

}
