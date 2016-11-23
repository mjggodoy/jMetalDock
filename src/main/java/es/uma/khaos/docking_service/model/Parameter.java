package es.uma.khaos.docking_service.model;

public class Parameter {
	
	private int parameter_id;
	private String algorithm;
	private int evaluations;
	private int runs;
	private int objectives;
	private int tasks_id;

	

	public Parameter(int parameter_id, String algorithm, int evaluations,
			int runs, int objectives, int tasks_id) {
		super();
		this.parameter_id = parameter_id;
		this.algorithm = algorithm;
		this.evaluations = evaluations;
		this.runs = runs;
		this.objectives = objectives;
		this.tasks_id = tasks_id;
	}


	public int getParameter_id() {
		return parameter_id;
	}


	public String getAlgorithm() {
		return algorithm;
	}


	public int getEvaluations() {
		return evaluations;
	}

	public int getRuns() {
		return runs;
	}

	public int getObjectives() {
		return objectives;
	}


	public int getTasks_id() {
		return tasks_id;
	}
	
}
