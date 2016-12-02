package es.uma.khaos.docking_service.model.response;

public class TaskResponse {
	
	private String msg;
	private int id;
	private String token;
	private String state;
	
	public TaskResponse(String msg) {
		super();
		this.msg = msg;
		this.id = 0;
		this.token = null;
	}
	
	public TaskResponse(String msg, int id, String token) {
		super();
		this.msg = msg;
		this.id = id;
		this.token = null;
	}
	
	public TaskResponse(String msg, int id, String token, String state) {
		super();
		this.msg = msg;
		this.id = id;
		this.token = token;
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
	
	public String getState() {
		return state;
	}

}
