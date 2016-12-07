package es.uma.khaos.docking_service.model;

import java.util.ArrayList;

public class Task {

	private int id;
	private String hash;
	private String state;
	private ArrayList<Parameter> parameter;


	public Task(int id, String hash, String state) {
		super();
		this.id = id;
		this.hash = hash;
		this.state = state;
	}
	
	
	public Task(int id, String hash, String state,
			ArrayList<Parameter> parameter) {
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


	public ArrayList<Parameter> getParameter() {
		return parameter;
	}
	

}
