package es.uma.khaos.docking_service.model;

import java.util.List;

public class Result {
	
	private int id;
	private int taskId;
	private int run;
	private List<Solution> solutions;
	
	public Result() {
		super();
	}

	public Result(int id, int taskId, int run) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.run = run;
	}

//	public Result(int run, List<Solution> solutions) {
//		super();
//		this.run = run;
//		this.solutions = solutions;
//	}
	
	public int getRun() {
		return run;
	}

	public int getId() {
		return id;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setRun(int run) {
		this.run = run;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}
	
}
