package es.uma.khaos.docking_service.model;

public class Result {
	
	
	public int id;
	public String finalBindingEnergy;
	public int objective1;
	public int objective2;
	public int executionTaskId;
	
	
	public Result(int id, String finalBindingEnergy, int objective1,
			int objective2, int executionTaskId) {
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


	public int getObjective1() {
		return objective1;
	}


	public int getObjective2() {
		return objective2;
	}


	public int getExecutionTaskId() {
		return executionTaskId;
	}

}
