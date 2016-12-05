package es.uma.khaos.docking_service.model.response;

import java.util.ArrayList;

public class TaskRunResponse {
	
	public int id;
	public String finalBindingEnergy;
	public ArrayList<Integer> objectives;
	public int execution_task_id;
	
	
	public TaskRunResponse(int id, String finalBindingEnergy,
			ArrayList<Integer> objectives, int execution_task_id) {
		super();
		this.id = 1;
		this.finalBindingEnergy = "-34,6 kcal/mol";
		this.objectives.add(1);
		this.execution_task_id = 1;
	}



	public int getId() {
		return id;
	}


	public String getFinalBindingEnergy() {
		return finalBindingEnergy;
	}

	
	public ArrayList<Integer> getObjectives() {
		return objectives;
	}


	public int getExecution_task_id() {
		return execution_task_id;
	}

}
