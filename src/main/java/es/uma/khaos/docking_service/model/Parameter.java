package es.uma.khaos.docking_service.model;

public class Parameter {
	
	private String algorithm;
	private int evaluations;
	private int runs;
	private int objectives;
	
	
	public Parameter(String algorithm, int evaluations, int runs, int objectives) {
		super();
		this.algorithm = algorithm;
		this.evaluations = evaluations;
		this.runs = runs;
		this.objectives = objectives;
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
}
