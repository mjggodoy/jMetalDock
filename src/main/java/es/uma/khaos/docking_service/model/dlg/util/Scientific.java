package es.uma.khaos.docking_service.model.dlg.util;

public class Scientific {
	
	private double value;
	private int exp;
	
	public Scientific(double value, int exp) {
		this.value = value;
		this.exp = exp;
	}
	
	public String toString() {
		return value + "e" + exp;
	}

}
