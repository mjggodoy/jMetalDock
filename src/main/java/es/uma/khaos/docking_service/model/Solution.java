package es.uma.khaos.docking_service.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="Result", description = "Results that contain the final binding energy, the objectives minimized, "
		+ "the intermolecular and intramolecular energies and the rmsd score.")

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
	
	@ApiModelProperty(value = "id")
	public int getId() {
		return id;
	}

	@ApiModelProperty(value = "Final Binding energy")
	public double getFinalBindingEnergy() {
		return finalBindingEnergy;
	}
	
	@ApiModelProperty(value = "Objectives")
	public List<String> getObjectives() {
		return objectives;
	}
	
	@ApiModelProperty(value = "RMSD")
	public Double getRmsd() {
		return rmsd;
	}
	
	@ApiModelProperty(value = "Intermolecular energy")
	public double getIntermolecularEnergy() {
		return intermolecularEnergy;
	}
	
	@ApiModelProperty(value = "Intramolecular energy")
	public double getIntramolecularEnergy() {
		return intramolecularEnergy;
	}
	
	@ApiModelProperty(value = "Get result id")
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
