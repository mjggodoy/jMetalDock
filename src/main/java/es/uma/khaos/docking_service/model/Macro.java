package es.uma.khaos.docking_service.model;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Macro", 
description = "This class corresponds to the macro class and all attributes related to it")

public class Macro {
	
	
	public int id;
	public String macro;
	
	
	public Macro(int id, String macro) {
		this.id = id;
		this.macro = macro;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMacro() {
		return macro;
	}


	public void setMacro(String macro) {
		this.macro = macro;
	}
	
}
