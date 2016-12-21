package es.uma.khaos.docking_service.model;

import java.util.ArrayList;

public class Result {
	
	
	public int id;
	public float finalBindingEnergy;
	public ArrayList<String> objectives;
	public int executionId;
	public float intermolecularEnergy;
	public float intramolecuarEnergy;
	public float rmsd;
	
	public Result(int id) {
		super();
		this.id = id;
	}

	public Result(int id, float finalBindingEnergy,
			ArrayList<String> objectives, float intermolecularEnergy, float intramolecuarEnergy, float rmsd, int executionTaskId) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objectives = objectives;
		this.rmsd = rmsd;
		this.executionId = executionTaskId;
	}


	public int getId() {
		return id;
	}

	public float getFinalBindingEnergy() {
		return finalBindingEnergy;
	}


	public ArrayList<String> getObjectives() {
		return objectives;
	}
	
	public float getRmsd() {
		return rmsd;
	}
	
	public float getIntermolecularEnergy() {
		return intermolecularEnergy;
	}

	public float getIntramolecuarEnergy() {
		return intramolecuarEnergy;
	}

	public int getExecutionTaskId() {
		return executionId;
	}

	
}
