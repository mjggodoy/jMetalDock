package es.uma.khaos.docking_service.model;

public class SingleObjectiveResults {
	
	private int result_id;
	private String finalBindingEnergy;
	private String objective;
	private int run;
	private int parameters_parameter_id;
	private int parameter_tasks_id;
	
	
	public SingleObjectiveResults(int result_id, String finalBindingEnergy,
			String objective, int run, int parameters_parameter_id,
			int parameter_tasks_id) {
		super();
		this.result_id = result_id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objective = objective;
		this.run = run;
		this.parameters_parameter_id = parameters_parameter_id;
		this.parameter_tasks_id = parameter_tasks_id;
	}


	public int getResult_id() {
		return result_id;
	}


	public String getFinalBindingEnergy() {
		return finalBindingEnergy;
	}


	public String getObjective() {
		return objective;
	}


	public int getRun() {
		return run;
	}


	public int getParameters_parameter_id() {
		return parameters_parameter_id;
	}


	public int getParameter_tasks_id() {
		return parameter_tasks_id;
	}	

}
