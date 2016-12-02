package es.uma.khaos.docking_service.model;

public class Result {
	
	
	public int id;
	public String finalBindingEnergy;
	public String objective1;
	public String objective2;
	public int executionTaskId;
	
	
	public Result(int id, String finalBindingEnergy, String objective1,
			String objective2, int executionTaskId) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objective1 = objective1;
		this.objective2 = objective2;
		this.executionTaskId = executionTaskId;
	}


	public int getId() {
		return id;
	}


	public String getFinalBindingEnergy() {
		return finalBindingEnergy;
	}


	public String getObjective1() {
		return objective1;
	}


	public String getObjective2() {
		return objective2;
	}


	public int getExecutionTaskId() {
		return executionTaskId;
	}

}
