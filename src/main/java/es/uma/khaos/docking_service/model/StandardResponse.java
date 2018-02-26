package es.uma.khaos.docking_service.model;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Standard Response",
description= "Standard response returns a status code, a message and a URL. "
		+ "It provides information to the user about any request on the web service")


@XmlRootElement
public class StandardResponse {

	private Status statusCode;
	private String message;
	private String url;

	public StandardResponse() {
		super();
	}

	public StandardResponse(Status statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public StandardResponse(Status statusCode, String message, String url) {
		this(statusCode, message);
		this.url = url;
	}
	
	@ApiModelProperty(value = "status code")
	public Status getStatusCode() {
		return statusCode;
	}

	@ApiModelProperty(value = "message")
	public String getMessage() {
		return message;
	}
	
	@ApiModelProperty(value = "url")
	public String getUrl() {
		return url;
	}

	public void setStatusCode(Status statusCode) {
		this.statusCode = statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
