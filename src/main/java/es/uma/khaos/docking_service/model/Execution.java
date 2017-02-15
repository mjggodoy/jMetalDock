package es.uma.khaos.docking_service.model;

public class Execution {
	
	public int id;
	public int task_id;
	public int run;

	public Execution(int id, int task_id, int run) {
		super();
		this.id = id;
		this.task_id = task_id;
		this.run = run;
	}

	public int getId() {
		return id;
	}

	public int getTask_id() {
		return task_id;
	}

	public int getRun() {
		return run;
	}

}
