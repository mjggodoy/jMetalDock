package es.uma.khaos.docking_service.model.response;

import java.util.ArrayList;

public class TaskRunResponse {
	
	private int id;
	private float finalBindingEnergy;
	private ArrayList<String> objectives;
	private int execution_task_id;
	
	public TaskRunResponse(int id, float finalBindingEnergy,
			ArrayList<String> objectives, int execution_task_id) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objectives = objectives;
		this.execution_task_id = execution_task_id;
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

	public int getExecution_task_id() {
		return execution_task_id;
	}

}
