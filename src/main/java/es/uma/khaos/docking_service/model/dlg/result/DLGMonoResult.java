package es.uma.khaos.docking_service.model.dlg.result;

import java.util.ArrayList;

import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.Reference;

public class DLGMonoResult extends ArrayList<AutoDockSolution> implements DLGResult<AutoDockSolution> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String toString() {
		String res = "DLG Mono Result (" + this.size() + " runs)\n";
		int run = 1;
		for (AutoDockSolution as : this) {
			res += run + ": " + as;
			run++;
		}
		return res;
	}
	
	public boolean checkRmsdCorrectCalculation(Reference reference) throws DlgParseException {
		return this.checkRmsdCorrectCalculation(reference, false);
	}
	
	public boolean checkRmsdCorrectCalculation(Reference reference, boolean verbose) throws DlgParseException {
		boolean res = true;
		double limit = 0.01;
		int idx = 1;
		for (AutoDockSolution solution : this) {
			double calculatedRmsd = solution.getConformation().calculateRmsd(reference);
			double rmsd = solution.getRmsd();
			if (verbose) {
				System.out.println(idx+": ("+(Math.abs(calculatedRmsd-rmsd))+") "+calculatedRmsd+" - "+rmsd+" ");
			}
			double error = Math.abs(calculatedRmsd-rmsd);
			if (error>limit) {
				System.err.println(idx+": ("+error+") "+calculatedRmsd+" - "+rmsd+" ");
				res = false;
			}
			idx++;
		}
		return res;
	}
	
}
