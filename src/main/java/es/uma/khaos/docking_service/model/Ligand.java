package es.uma.khaos.docking_service.model;

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
