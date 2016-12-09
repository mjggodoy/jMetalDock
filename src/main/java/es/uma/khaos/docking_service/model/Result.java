package es.uma.khaos.docking_service.model;

import java.util.ArrayList;

public class Result {
	
	
	public int id;
	public int finalBindingEnergy;
	public ArrayList<String> objectives;
	public int executionTaskId;
	


	public Result(int id) {
		super();
		this.id = id;
	}

	public Result(int id, int finalBindingEnergy,
			ArrayList<String> objectives, int executionTaskId) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objectives = objectives;
		this.executionTaskId = executionTaskId;
	}


	public int getId() {
		return id;
	}


	public int getFinalBindingEnergy() {
		return finalBindingEnergy;
	}



	public ArrayList<String> getObjectives() {
		return objectives;
	}


	public int getExecutionTaskId() {
		return executionTaskId;
	}

}
