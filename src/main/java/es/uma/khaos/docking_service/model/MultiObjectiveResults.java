package es.uma.khaos.docking_service.model;

public class MultiObjectiveResults {
	
	private int result_id;
	private String objective1;
	private String objective2;
	private String front_id;
	private int parameters_parameters_id;
	private int parameters_tasks_id;
	
	
	public MultiObjectiveResults(int result_id, String objective1,
			String objective2, String front_id, int parameters_parameters_id,
			int parameters_tasks_id) {
		super();
		this.result_id = result_id;
		this.objective1 = objective1;
		this.objective2 = objective2;
		this.front_id = front_id;
		this.parameters_parameters_id = parameters_parameters_id;
		this.parameters_tasks_id = parameters_tasks_id;
	}


	public int getResult_id() {
		return result_id;
	}


	public String getObjective1() {
		return objective1;
	}


	public String getObjective2() {
		return objective2;
	}


	public String getFront_id() {
		return front_id;
	}


	public int getParameters_parameters_id() {
		return parameters_parameters_id;
	}


	public int getParameters_tasks_id() {
		return parameters_tasks_id;
	}	

}
