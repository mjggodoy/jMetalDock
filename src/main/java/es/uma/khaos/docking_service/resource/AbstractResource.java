package es.uma.khaos.docking_service.resource;

import es.uma.khaos.docking_service.model.ErrorResponse;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.response.JspResponseBuilder;
import es.uma.khaos.docking_service.response.PojoResponseBuilder;
import es.uma.khaos.docking_service.response.ResponseBuilder;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class AbstractResource extends Application {
    protected ResponseBuilder getResponseBuilder(HttpHeaders headers, String jsp) {
        List<MediaType> acceptableTypes = headers.getAcceptableMediaTypes();
        if (acceptableTypes.contains(MediaType.TEXT_HTML_TYPE)) {
            return new JspResponseBuilder(jsp);
        } else return new PojoResponseBuilder();
    }

    protected Response internalServerError(ResponseBuilder builder) {
        return builder.buildResponse(
                new ErrorResponse(
                        Response.Status.INTERNAL_SERVER_ERROR,
                        Constants.RESPONSE_INTERNAL_SERVER_ERROR),
                Response.Status.INTERNAL_SERVER_ERROR
        );
    }
}
