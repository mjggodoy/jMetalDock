package es.uma.khaos.docking_service.model;

import javax.validation.constraints.Min;

public class ParameterSet {
	

	private int run;
	private int id;
	private String algorithm;
	private int evaluation;
	private int populationSize;
	private int objective;
	private int task_id;
	
	public ParameterSet(){}
	
	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
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

	public void setId(int id) {
		this.id = id;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public void setRun(int run) {
		this.run = run;
	}

	public void setObjective(int objective) {
		this.objective = objective;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
}
