package es.uma.khaos.docking_service.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Individual",
description = "An individual solution that shows the final energy score, the objectives that were optimized and the .pdbqt")


public class IndividualSolution {

	public int id;
	public double finalBindingEnergy;
	public List<String> objectives;
	public double intermolecularEnergy;
	public double intramolecularEnergy;
	public Double rmsd;
	public int run;
	public int taskId;
	public String pdbqt;

	public IndividualSolution() {

	}

	public IndividualSolution(int id) {
		super();
		this.id = id;
	}

	public IndividualSolution(int id, double finalBindingEnergy, List<String> objectives, double intermolecularEnergy,
							  double intramolecularEnergy, Double rmsd, int run, int taskId, String pdbqt) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objectives = objectives;
		this.intermolecularEnergy = intermolecularEnergy;
		this.intramolecularEnergy = intramolecularEnergy;
		this.rmsd = rmsd;
		this.run = run;
		this.taskId = taskId;
		this.pdbqt = pdbqt;
	}

	@ApiModelProperty(value = "id")
	public int getId() {
		return id;
	}

	@ApiModelProperty(value = "final binding energy")
	public double getFinalBindingEnergy() {
		return finalBindingEnergy;
	}

	@ApiModelProperty(value = "objectives")
	public List<String> getObjectives() {
		return objectives;
	}
	
	@ApiModelProperty(value = "rmsd")
	public Double getRmsd() {
		return rmsd;
	}
	
	@ApiModelProperty(value = "intermolecular energy")
	public double getIntermolecularEnergy() {
		return intermolecularEnergy;
	}

	@ApiModelProperty(value = "intramolecular energy")
	public double getIntramolecularEnergy() {
		return intramolecularEnergy;
	}
	
	@ApiModelProperty(value = "run")
	public int getRun()  {
		return run;
	}

	@ApiModelProperty(value = "task id")
	public int getTaskId() {
		return taskId;
	}

	@ApiModelProperty(value = "pdbqt")
	public String getPdbqt() {
		return pdbqt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFinalBindingEnergy(double finalBindingEnergy) {
		this.finalBindingEnergy = finalBindingEnergy;
	}

	public void setObjectives(List<String> objectives) {
		this.objectives = objectives;
	}

	public void setIntermolecularEnergy(double intermolecularEnergy) {
		this.intermolecularEnergy = intermolecularEnergy;
	}

	public void setIntramolecularEnergy(double intramolecularEnergy) {
		this.intramolecularEnergy = intramolecularEnergy;
	}

	public void setRmsd(Double rmsd) {
		this.rmsd = rmsd;
	}

	public void setRun(int run) {
		this.run = run;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setPdbqt(String pdbqt) {
		this.pdbqt = pdbqt;
	}
}
