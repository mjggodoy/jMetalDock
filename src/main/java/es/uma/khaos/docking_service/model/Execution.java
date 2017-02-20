package es.uma.khaos.docking_service.model;

import javax.xml.bind.annotation.XmlRootElement;

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

	@Override
	public String toString() {
		return "Execution [id=" + id + ", task_id=" + task_id + ", run=" + run + "]";
	}
	
}
