package es.uma.khaos.docking_service.model;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
