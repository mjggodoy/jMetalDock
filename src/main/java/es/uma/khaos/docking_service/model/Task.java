package es.uma.khaos.docking_service.model;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel(value="Task", 
description = "Task that provides the task id, token, state, the end and start times, "
		+ "the algorithm and parameters which were selected by the user")
@XmlRootElement
public class Task {

	private int id;
	private String token;
	private String state;
	//TODO: Hacer que los dos IDs no aparezcan en el JSON
	private String email;
	private Timestamp startTime;
	private Timestamp endTime;
	private ParameterSet parameters;

	public Task() { }
	
	public Task(int id, String token, String state, String email) {
		super();
		this.id = id;
		this.token = token;
		this.state = state;
		this.email = email;
	}
	
	public Task(int id, String hash, String state, String email, Timestamp startTime, Timestamp endTime,
				ParameterSet parameters) {
		this(id, hash, state, email);
		this.startTime = startTime;
		this.endTime =  endTime;
		this.parameters = parameters;
	}

	@ApiModelProperty(value = "id")
	public int getId() {
		return id;
	}

	@ApiModelProperty(value = "token")
	public String getToken() {
		return token;
	}
	
	@ApiModelProperty(value = "status", allowableValues = "finished, error, running, queued")
	public String getState() {
		return state;
	}
	
	@ApiModelProperty(value = "email", required = false)
	public String getEmail() {
		return email;
	}

	@ApiModelProperty(value = "start time")
	public Timestamp getStartTime() {
		return startTime;
	}
	
	@ApiModelProperty(value = "end time")
	public Timestamp getEndTime() {
		return endTime;
	}

	@ApiModelProperty(value = "parameter set")
	public ParameterSet getParameters() {
		return parameters;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}

}
