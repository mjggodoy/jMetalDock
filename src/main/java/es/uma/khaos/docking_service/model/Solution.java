package es.uma.khaos.docking_service.model;

import java.util.List;

public class Solution {
	
	public int id;
	public double finalBindingEnergy;
	public List<String> objectives;
	public int resultId;
	public double intermolecularEnergy;
	public double intramolecularEnergy;
	public Double rmsd;
	
	public Solution() {
		
	}
	
	public Solution(int id) {
		super();
		this.id = id;
	}

	public Solution(int id, double finalBindingEnergy,
			List<String> objectives, double intermolecularEnergy, double intramolecularEnergy, Double rmsd, int resultId) {
		super();
		this.id = id;
		this.finalBindingEnergy = finalBindingEnergy;
		this.objectives = objectives;
		this.intermolecularEnergy = intermolecularEnergy;
		this.intramolecularEnergy = intramolecularEnergy;
		this.rmsd = rmsd;
		this.resultId = resultId;
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

	public int getResultId() {
		return resultId;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", finalBindingEnergy=" + finalBindingEnergy + ", objectives=" + objectives
				+ ", resultId=" + resultId + ", intermolecularEnergy=" + intermolecularEnergy
				+ ", intramolecularEnergy=" + intramolecularEnergy + ", rmsd=" + rmsd + "]";
	}
	
}
