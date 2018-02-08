package es.uma.khaos.docking_service.model;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Result", description = "Result that includes a solution or a set of solutions")

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
	
	public Result(int taskId, int id, int run, double minimumFinalBindingEnergy ) {
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
	
	@ApiModelProperty(value = "run")
	public int getRun() {
		return run;
	}

	@ApiModelProperty(value = "id")
	public int getId() {
		return id;
	}
	
	@ApiModelProperty(value = "task id")
	public int getTaskId() {
		return taskId;
	}
	
	@ApiModelProperty(value = "solutions")
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
