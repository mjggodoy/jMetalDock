package es.uma.khaos.docking_service.model.response;

public class TaskResponse {
	
	private int id;
	private String token;
	private String state;
	
	public TaskResponse(int id, String token, String state) {
		super();
		this.id = id;
		this.token = token;
		this.state = state;
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

}
