package es.uma.khaos.docking_service.model;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorResponse {
	
	private Status statusCode;
	private String message;
	
	public ErrorResponse() {
		super();
	}

	public ErrorResponse(Status statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public Status getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setStatusCode(Status statusCode) {
		this.statusCode = statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
