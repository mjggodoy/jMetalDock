package es.uma.khaos.docking_service.model;

public class Task {

	private int id;
	private String hash;
	private String state;

	public Task(int id, String hash) {
		super();
		this.id = id;
		this.hash = hash;
	}
	
	public Task(int id, String hash, String state) {
		super();
		this.id = id;
		this.hash = hash;
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

}
