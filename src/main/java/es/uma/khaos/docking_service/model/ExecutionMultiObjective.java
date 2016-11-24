package es.uma.khaos.docking_service.model;

public class ExecutionMultiObjective {

	public int execution_id;
	public int run;
	public int multi_objective_results_result_id;
	public int multi_objective_results_parameters_parameter_id;
	public int multi_objective_results_parameters_tasks_id;
	public int tasks_id;

	public ExecutionMultiObjective(int execution_id, int run,
			int multi_objective_results_result_id, int multi_objective_results_parameters_parameter_id,
			int multi_objective_results_parameters_tasks_id, int tasks_id) {
		super();
		this.execution_id = execution_id;
		this.run = run;
		this.multi_objective_results_result_id = multi_objective_results_result_id;
		this.multi_objective_results_parameters_parameter_id = multi_objective_results_parameters_parameter_id;
		this.multi_objective_results_parameters_tasks_id = multi_objective_results_parameters_tasks_id;
		this.tasks_id = tasks_id;
	}

	public int getExecution_id() {
		return execution_id;
	}

	public int getRun() {
		return run;
	}

	public int getMulti_objective_results_result_id() {
		return multi_objective_results_result_id;
	}
	
	public int getMulti_objective_results_parameters_parameter_id() {
		return multi_objective_results_parameters_parameter_id;
	}

	public int getMulti_objective_results_parameters_tasks_id() {
		return multi_objective_results_parameters_tasks_id;
	}

	public int getTasks_id() {
		return tasks_id;
	}

}
