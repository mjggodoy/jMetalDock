package es.uma.khaos.docking_service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {

	private int id;
	private String hash;
	private String state;
	//TODO: Hacer que los dos IDs no aparezcan en el JSON
	private ParameterSet parameters;
	
	public Task() { }

	public Task(int id, String hash, String state) {
		super();
		this.id = id;
		this.hash = hash;
		this.state = state;
	}
	
	public Task(int id, String hash, String state,
			ParameterSet parameters) {
		super();
		this.id = id;
		this.hash = hash;
		this.state = state;
		this.parameters = parameters;
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
	
	public void setId(int id) {
		this.id = id;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ParameterSet getParameters() {
		return parameters;
	}
	
	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}

}
