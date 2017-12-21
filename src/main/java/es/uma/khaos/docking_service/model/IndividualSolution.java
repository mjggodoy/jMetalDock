package es.uma.khaos.docking_service.model;

import java.util.List;

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

	public int getId() {
		return id;
	}

	public double getFinalBindingEnergy() {
		return finalBindingEnergy;
	}

	public List<String> getObjectives() {
		return objectives;
	}
	
	public Double getRmsd() {
		return rmsd;
	}
	
	public double getIntermolecularEnergy() {
		return intermolecularEnergy;
	}

	public double getIntramolecularEnergy() {
		return intramolecularEnergy;
	}

	public int getRun()  {
		return run;
	}

	public int getTaskId() {
		return taskId;
	}

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
