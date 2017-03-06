package es.uma.khaos.docking_service.model;

import javax.xml.bind.annotation.XmlTransient;

import es.uma.khaos.docking_service.properties.Constants;

public class ParameterSet {

	private int run;
	private int id;
	private String algorithm;
	private int evaluation;
	private int populationSize;
	private int objective;
	private int task_id;
	
	protected String instance;
	protected String uploadedFile;
	protected String zipFile;
	
	public ParameterSet() { }
	
	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
			int objectiveOption, int taskId) {
		super();
		this.id = id;
		this.algorithm = algorithm;
		this.run = inRangeCheck(
				runs,
				Constants.DEFAULT_MIN_NUMBER_RUNS,
				Constants.DEFAULT_MAX_NUMBER_RUNS);
		this.populationSize = inRangeCheck(
				populationSize,
				Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE,
				Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE);
		this.evaluation = inRangeCheck(
				evaluations,
				Constants.DEFAULT_MIN_NUMBER_EVALUATIONS,
				Constants.DEFAULT_MAX_NUMBER_EVALUATIONS);
		this.objective = objectiveOption;
		this.task_id = taskId;
	}
	
	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
			boolean useRmsdAsObjective, int taskId) {
		this(id, algorithm, evaluations, populationSize, runs, getObjectiveOption(algorithm, useRmsdAsObjective), taskId);
	}
	
	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
			boolean useRmsdAsObjective, int taskId, String instance) {
		this(id, algorithm, evaluations, populationSize, runs, useRmsdAsObjective, taskId);
		this.instance = instance;
	}
	
	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
			boolean useRmsdAsObjective, int taskId, String uploadedFile, String zipFile) {
		this(id, algorithm, evaluations, populationSize, runs, useRmsdAsObjective, taskId);
		this.uploadedFile = uploadedFile;
		this.zipFile = zipFile;
	}

	@XmlTransient
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

	@XmlTransient
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

	public String getInstance() {
		return instance;
	}
	
	public String getUploadedFile() {
		return uploadedFile;
	}

	@XmlTransient
	public String getZipFile() {
		return zipFile;
	}
	
	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}
	
	private int inRangeCheck(int value, int minValue, int maxValue) {
		if (value > maxValue) return maxValue;
		else if (value < minValue) return minValue;
		else return value;
	}
	
	private static int getObjectiveOption(String algorithm, boolean useRmsdAsObjective) {
		if (Constants.SINGLE_OBJECTIVE_ALGORITHMS.contains(algorithm)) return 1;
		else if (Constants.MULTI_OBJECTIVE_ALGORITHMS.contains(algorithm)) {
			if (useRmsdAsObjective) return 3;
			else return 2;
		} else return 0;
		
	}
	
}
