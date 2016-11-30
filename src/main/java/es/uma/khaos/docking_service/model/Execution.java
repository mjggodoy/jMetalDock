package es.uma.khaos.docking_service.model;

public class Execution {
	
	
	public int id;
	public int task_id;
	
	
	public Execution(int id, int task_id) {
		super();
		this.id = id;
		this.task_id = task_id;
	}


	public int getId() {
		return id;
	}


	public int getTask_id() {
		return task_id;
	}



}
