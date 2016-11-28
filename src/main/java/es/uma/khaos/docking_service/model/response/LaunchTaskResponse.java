package es.uma.khaos.docking_service.model.response;

public class LaunchTaskResponse {
	
	private String state;
	private String msg;
	private int id;
	private String token;
	
	public LaunchTaskResponse(String state, String msg, int id, String token) {
		super();
		this.state = state;
		this.msg = msg;
		this.id = id;
		this.token = token;
	}
	
	public String getState() {
		return state;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public int getId() {
		return id;
	}
	
	public String getToken() {
		return token;
	}

}
