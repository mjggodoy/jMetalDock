package es.uma.khaos.docking_service.model;

import java.util.ArrayList;

public class Task {

	private int id;
	private String hash;
	private String state;
	private Parameter parameter;


	public Task(int id, String hash, String state) {
		super();
		this.id = id;
		this.hash = hash;
		this.state = state;
	}
	
	
	public Task(int id, String hash, String state,
			Parameter parameter) {
		super();
		this.id = id;
		this.hash = hash;
		this.state = state;
		this.parameter = parameter;
	}


	public int getId() {
		return id;
	}

	public String getHash() {
		return hash;
	}
	
	public String getState() {
		return state;
	}


	public Parameter getParameter() {
		return parameter;
	}
	

}
