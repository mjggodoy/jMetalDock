package es.uma.khaos.docking_service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {

	private int id;
	private String token;
	private String state;
	//TODO: Hacer que los dos IDs no aparezcan en el JSON
	private String email;
	private ParameterSet parameters;

	
	public Task() { }
	
	
	public Task(int id, String token, String state, String email) {
		super();
		this.id = id;
		this.token = token;
		this.state = state;
		this.email = email;
		
	}
	
	
	public Task(int id, String hash, String state, ParameterSet parameters, String email) {
		super();
		this.id = id;
		this.token = hash;
		this.state = state;
		this.parameters = parameters;
		this.email = email;
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
	
	public String getEmail() {
		return email;
	}
	
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

	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
