package es.uma.khaos.docking_service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@XmlRootElement
public class Task {

	private int id;
	private String token;
	private String state;
	//TODO: Hacer que los dos IDs no aparezcan en el JSON
	private String email;
	private Timestamp startTime;
	private Timestamp endTime;
	private ParameterSet parameters;

	public Task() { }
	
	public Task(int id, String token, String state, String email) {
		super();
		this.id = id;
		this.token = token;
		this.state = state;
		this.email = email;
	}
	
	public Task(int id, String hash, String state, String email, Timestamp startTime, Timestamp endTime,
				ParameterSet parameters) {
		this(id, hash, state, email);
		this.startTime = startTime;
		this.endTime =  endTime;
		this.parameters = parameters;
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

	public Timestamp getStartTime() {
		return startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}

}
