package es.uma.khaos.docking_service.model.response;

import es.uma.khaos.docking_service.model.Parameter;

public class TaskResponse {
	
	private int id;
	private String token;
	private String state;
	private Parameter parameter;

	
	public TaskResponse(int id, String token, String state, Parameter parameter) {
		super();
		this.id = id;
		this.token = token;
		this.state = state;
		this.parameter = parameter;
	}
	
	public int getId() {
		return id;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getState() {
		return state;
	}

	public Parameter getParameter() {
		return parameter;
	}

}
