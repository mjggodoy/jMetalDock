package es.uma.khaos.docking_service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Instance",
description="A molecular docking problem provided by the user to solve ") 


public class Instance {
	
	private int id;
	private String name;
	private String fileName;
	private String url;
	
	public Instance() {
		super();
	}
	
	public Instance(int id, String name, String fileName) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
	}

	@ApiModelProperty(value = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ApiModelProperty(value = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ApiModelProperty(value = "file name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@ApiModelProperty(value = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
