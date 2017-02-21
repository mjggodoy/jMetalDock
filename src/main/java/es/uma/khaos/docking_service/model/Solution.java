package es.uma.khaos.docking_service.model;

import java.util.List;

public class Solution {
	
	public int id;
	public float finalBindingEnergy;
	public List<String> objectives;
	public int executionId;
	public float intermolecularEnergy;
	public float intramolecularEnergy;
	public Float rmsd;
	
	public Solution() {
		
	}
	
	public Solution(int id) {
		super();
		this.id = id;
	}

	public Solution(int id, float finalBindingEnergy,
			List<String> objectives, float intermolecularEnergy, float intramolecularEnergy, Float rmsd, int executionTaskId) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objectives = objectives;
		this.intermolecularEnergy = intermolecularEnergy;
		this.intramolecularEnergy = intramolecularEnergy;
		this.rmsd = rmsd;
		this.executionId = executionTaskId;
	}

	public int getId() {
		return id;
	}

	public float getFinalBindingEnergy() {
		return finalBindingEnergy;
	}

	public List<String> getObjectives() {
		return objectives;
	}
	
	public Float getRmsd() {
		return rmsd;
	}
	
	public float getIntermolecularEnergy() {
		return intermolecularEnergy;
	}

	public float getIntramolecularEnergy() {
		return intramolecularEnergy;
	}

	public int getExecutionTaskId() {
		return executionId;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", finalBindingEnergy=" + finalBindingEnergy + ", objectives=" + objectives
				+ ", executionId=" + executionId + ", intermolecularEnergy=" + intermolecularEnergy
				+ ", intramolecularEnergy=" + intramolecularEnergy + ", rmsd=" + rmsd + "]";
	}
	
}
