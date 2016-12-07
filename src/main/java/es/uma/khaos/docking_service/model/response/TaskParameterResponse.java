package es.uma.khaos.docking_service.model.response;

import java.util.ArrayList;

import es.uma.khaos.docking_service.model.Parameter;

public class TaskParameterResponse {
	
	private int id;
	private String token;
	private String state;
	private ArrayList<Parameter> parameter;
	

	public TaskParameterResponse(int id, String token, String state,
			ArrayList<Parameter> parameter) {
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


	public ArrayList<Parameter> getParameter() {
		return parameter;
	}
	
}
