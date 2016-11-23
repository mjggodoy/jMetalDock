package es.uma.khaos.docking_service.model;

public class ExecutionSingleObjective {

	public int execution_id;
	public int run;
	public int single_objective_results_result_id;
	public int single_objective_results_parameters_parameters_id;
	public int single_objective_results_parameters_tasks_id;
	public int tasks_id;

	public ExecutionSingleObjective(int execution_id, int run,
			int single_objective_results_result_id,
			int single_objective_results_parameters_parameters_id,
			int single_objective_results_parameters_tasks_id, int tasks_id) {
		super();
		this.execution_id = execution_id;
		this.run = run;
		this.single_objective_results_result_id = single_objective_results_result_id;
		this.single_objective_results_parameters_parameters_id = single_objective_results_parameters_parameters_id;
		this.single_objective_results_parameters_tasks_id = single_objective_results_parameters_tasks_id;
		this.tasks_id = tasks_id;
	}

	public int getExecution_id() {
		return execution_id;
	}

	public int getRun() {
		return run;
	}

	public int getSingle_objective_results_result_id() {
		return single_objective_results_result_id;
	}

	public int getSingle_objective_results_parameters_parameters_id() {
		return single_objective_results_parameters_parameters_id;
	}

	public int getSingle_objective_results_parameters_tasks_id() {
		return single_objective_results_parameters_tasks_id;
	}

	public int getTasks_id() {
		return tasks_id;
	}

}
