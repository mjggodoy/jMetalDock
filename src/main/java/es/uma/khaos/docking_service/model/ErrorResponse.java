package es.uma.khaos.docking_service.model;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value="Error response",
description = "This class describes the status code and the message.")

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

	@ApiModelProperty(value = "status code")
	public Status getStatusCode() {
		return statusCode;
	}

	@ApiModelProperty(value = "message")
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
