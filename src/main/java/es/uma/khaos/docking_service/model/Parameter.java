package es.uma.khaos.docking_service.model;

public class Parameter {

	private int id;
	private String algorithm;
	private int evaluation;
	private int populationSize;
	private int run;
	private int objective;
	private int task_id;
	
	
	public Parameter(int id, String algorithm, int evaluations, int populationSize, int runs,
			int objectives, int task_id) {
		super();
		this.id = id;
		this.algorithm = algorithm;
		this.evaluation = evaluations;
		this.populationSize = populationSize;
		this.run = runs;
		this.objective = objectives;
		this.task_id = task_id;
	}


	public int getId() {
		return id;
	}


	public String getAlgorithm() {
		return algorithm;
	}


	public int getEvaluation() {
		return evaluation;
	}
	

	public int getPopulationSize() {
		return populationSize;
	}


	public int getRun() {
		return run;
	}


	public int getObjective() {
		return objective;
	}


	public int getTask_id() {
		return task_id;
	}
	
}
