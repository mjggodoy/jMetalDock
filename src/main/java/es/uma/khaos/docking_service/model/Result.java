package es.uma.khaos.docking_service.model;

import java.util.ArrayList;

public class Result {
	
	
	public int id;
	public float finalBindingEnergy;
	public ArrayList<String> objectives;
	public int executionId;
	public float rmsd;
	
	public Result(int id) {
		super();
		this.id = id;
	}

	public Result(int id, float finalBindingEnergy,
			ArrayList<String> objectives, float rmsd, int executionTaskId) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objectives = objectives;
		this.rmsd = rmsd;
		this.executionId = executionTaskId;
	}


	public int getId() {
		return id;
	}

	public float getFinalBindingEnergy() {
		return finalBindingEnergy;
	}


	public ArrayList<String> getObjectives() {
		return objectives;
	}


	public int getExecutionTaskId() {
		return executionId;
	}

	public float getRmsd() {
		return rmsd;
	}
	
}
