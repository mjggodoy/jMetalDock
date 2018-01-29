package es.uma.khaos.docking_service.model;

import javax.xml.bind.annotation.XmlTransient;
import es.uma.khaos.docking_service.properties.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Parameter set", 
description = "Parameters set includes all the parameters for the algortihm selected by the user")

public class ParameterSet {

	private int runs;
	private int id;
	private String algorithm;
	private int evaluations;
	private int populationSize;
	private int objectiveOption;
	private int taskId;
	
	protected String instance;
	protected String uploadedFile;
	protected String zipFile;
	
	public ParameterSet() { }
	
	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
			int objectiveOption, int taskId) {
		super();
		this.id = id;
		this.algorithm = algorithm;
		this.runs = inRangeCheck(
				runs,
				Constants.DEFAULT_MIN_NUMBER_RUNS,
				Constants.DEFAULT_MAX_NUMBER_RUNS);
		this.populationSize = inRangeCheck(
				populationSize,
				Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE,
				Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE);
		this.evaluations = inRangeCheck(
				evaluations,
				Constants.DEFAULT_MIN_NUMBER_EVALUATIONS,
				Constants.DEFAULT_MAX_NUMBER_EVALUATIONS);
		this.objectiveOption = objectiveOption;
		this.taskId = taskId;
	}

	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
						int objectiveOption, int taskId, String instance) {
		this(id, algorithm, evaluations, populationSize, runs, objectiveOption, taskId);
		this.instance = instance;
	}
	
	public ParameterSet(int id, String algorithm, int evaluations, int populationSize, int runs,
			boolean useRmsdAsObjective, int taskId) {
		this(id, algorithm, evaluations, populationSize, runs, initObjectiveOption(algorithm, useRmsdAsObjective), taskId);
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
	@ApiModelProperty(value = "id")
	public int getId() {
		return id;
	}

	@ApiModelProperty(value = "algorithm")
	public String getAlgorithm() {
		return algorithm;
	}

	@ApiModelProperty(value = "evaluations")
	public int getEvaluations() {
		return evaluations;
	}

	@ApiModelProperty(value = "population size")
	public int getPopulationSize() {
		return populationSize;
	}
	
	@ApiModelProperty(value = "runs")
	public int getRuns() {
		return runs;
	}

	@ApiModelProperty(value = "objective option")
	public int getObjectiveOption() {
		return objectiveOption;
	}

	@ApiModelProperty(value = "task id")
	@XmlTransient
	public int getTaskId() {
		return taskId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setEvaluations(int evaluations) {
		this.evaluations = evaluations;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public void setObjectiveOption(int objectiveOption) {
		this.objectiveOption = objectiveOption;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	@ApiModelProperty(value = "instance")
	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	@ApiModelProperty(value = "uploaded file")
	public String getUploadedFile() {
		return uploadedFile;
	}
	
	
	@ApiModelProperty(value = "zip file")
	@XmlTransient
	public String getZipFile() {
		return zipFile;
	}
	
	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}
	
	@Override
	public String toString() {
		return "ParameterSet [runs=" + runs + ", id=" + id + ", algorithm=" + algorithm + ", evaluations=" + evaluations
				+ ", populationSize=" + populationSize + ", objectiveOption=" + objectiveOption + ", taskId=" + taskId
				+ ", instance=" + instance + ", uploadedFile=" + uploadedFile + ", zipFile=" + zipFile + "]";
	}

	private int inRangeCheck(int value, int minValue, int maxValue) {
		if (value > maxValue) return maxValue;
		else if (value < minValue) return minValue;
		else return value;
	}
	
	private static int initObjectiveOption(String algorithm, boolean useRmsdAsObjective) {
		if (Constants.SINGLE_OBJECTIVE_ALGORITHMS.contains(algorithm)) return 1;
		else if (Constants.MULTI_OBJECTIVE_ALGORITHMS.contains(algorithm)) {
			if (useRmsdAsObjective) return 3;
			else return 2;
		} else return 0;
		
	}
	
}
