package es.uma.khaos.docking_service.model.dlg;

import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.dlg.util.Scientific;


/**
 * Clase que almacena una solución de AutoDock
 * 
 * Hecha desde que se realizará el cálculo de RMSDs para las ejecuciones
 * de multiobjetivo.
 * 
 * @author Esteban López Camacho
 *
 */
public class AutoDockSolution {
	
	/**
	 * Estimated Free Energy of Binding
	 */
	private Float totalEnergy;
	
	/**
	 * Estimated Inhibition Constant (M (molar))
	 */
	private Scientific ki;
	
	/**
	 * (1) Final Intermolecular Energy
	 */
	private Float energy1;
	
	/**
	 * (2) Final Total Internal Energy
	 */
	private Float energy2;
	
	/**
	 * RMSD
	 */
	private Double rmsd;
	
	private Conformation conformation;
	
	public AutoDockSolution(Float totalEnergy) {
		this.totalEnergy = totalEnergy;
		this.ki = null;
		this.energy1 = null;
		this.energy2 = null;
		this.rmsd = null;
		this.conformation = null;
	}
	
	public AutoDockSolution(Float totalEnergy, Scientific ki, Float energy1, Float energy2) {
		this.totalEnergy = totalEnergy;
		this.ki = ki;
		this.energy1 = energy1;
		this.energy2 = energy2;
		this.rmsd = null;
		this.conformation = null;
	}
	
	public AutoDockSolution(Float totalEnergy, Scientific ki, Float energy1, Float energy2, Double rmsd) {
		this.totalEnergy = totalEnergy;
		this.ki = ki;
		this.energy1 = energy1;
		this.energy2 = energy2;
		this.rmsd = rmsd;
		this.conformation = null;
	}

	public Float getTotalEnergy() {
		return totalEnergy;
	}

	public void setTotalEnergy(Float totalEnergy) {
		this.totalEnergy = totalEnergy;
	}

	public Scientific getKi() {
		return ki;
	}

	public void setKi(Scientific ki) {
		this.ki = ki;
	}

	public Float getEnergy1() {
		return energy1;
	}

	public void setEnergy1(Float energy1) {
		this.energy1 = energy1;
	}

	public Float getEnergy2() {
		return energy2;
	}

	public void setEnergy2(Float energy2) {
		this.energy2 = energy2;
	}

	public Double getRmsd() {
		return rmsd;
	}

	public void setRmsd(Double rmsd) {
		this.rmsd = rmsd;
	}
	
	public Conformation getConformation() {
		return conformation;
	}

	public void setConformation(Conformation conformation) {
		this.conformation = conformation;
	}
	
	public void calculateRMSD(Reference reference) throws DlgParseException {
		if (this.rmsd!=null) throw new DlgParseException("RMSD ya calculado.");
		this.rmsd = this.conformation.calculateRmsd(reference);
	}

	public String toString() {
		String res = "AUTODOCK SOLUTION:\n";
		res += "\tEstimated Free Energy of Binding = " + this.totalEnergy + " kcal/mol\n";
		if (this.ki!=null) res += "\tEstimated Inhibition Constant, Ki = " + this.ki + " M (molar)\n";
		if (this.energy1!=null) res += "\t(1) Final Intermolecular Energy  = " + this.energy1 + " kcal/mol\n";
		if (this.energy2!=null) res += "\t(2) Final Total Internal Energy  = " + this.energy2 + " kcal/mol\n";
		if (this.rmsd!=null) res += "\tRMSD = " + this.rmsd + "\n";
		return res;
	}
	
}
