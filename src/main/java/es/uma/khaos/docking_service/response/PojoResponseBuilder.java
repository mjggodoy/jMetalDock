package es.uma.khaos.docking_service.response;

import javax.ws.rs.core.Response;

public class PojoResponseBuilder implements ResponseBuilder {

	@Override
	public Response buildResponse(Object o) {
		return Response.ok(o).build();
	}

	@Override
	public Response buildCreatedResponse(Object o) {
		return Response
				.status(Response.Status.CREATED)
				.entity(o)
				.build();
	}

}
