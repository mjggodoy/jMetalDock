package es.uma.khaos.docking_service.model;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Ligand", 
description = "This class corresponds to the ligand class and all attributes related to it.")

public class Ligand {
	
	
	
	public int id;
	public String ligand;

	public Ligand(int id, String ligand) {
		this.id = id;
		this.ligand = ligand;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLigand() {
		return ligand;
	}

	public void setLigand(String ligand) {
		this.ligand = ligand;
	}
	

}
