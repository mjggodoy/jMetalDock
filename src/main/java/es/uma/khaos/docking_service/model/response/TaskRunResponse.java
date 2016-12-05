package es.uma.khaos.docking_service.model.response;

import java.util.ArrayList;

public class TaskRunResponse {
	
	public int id;
	public String finalBindingEnergy;
	public ArrayList<String> objectives;
	public int execution_task_id;
	
	
	public TaskRunResponse(int id, String finalBindingEnergy,
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


	public String getFinalBindingEnergy() {
		return finalBindingEnergy;
	}

	
	public ArrayList<String> getObjectives() {
		return objectives;
	}


	public int getExecution_task_id() {
		return execution_task_id;
	}

}
