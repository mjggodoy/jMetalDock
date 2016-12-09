package es.uma.khaos.docking_service.model;

public class Task {

	private int id;
	private String hash;
	private String state;
	//TODO: Hacer que los dos IDs no aparezcan en el JSON
	private Parameter parameters;

	public Task(int id, String hash, String state) {
		super();
		this.id = id;
		this.hash = hash;
		this.state = state;
	}
	
	public Task(int id, String hash, String state,
			Parameter parameters) {
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

	public Parameter getParameters() {
		return parameters;
	}
	
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}

}
