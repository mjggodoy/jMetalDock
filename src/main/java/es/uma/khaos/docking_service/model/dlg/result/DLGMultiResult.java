package es.uma.khaos.docking_service.model.dlg.result;

import java.util.ArrayList;

import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution.Optimization;
import es.uma.khaos.docking_service.model.dlg.Front;
import es.uma.khaos.docking_service.model.dlg.Reference;

public class DLGMultiResult extends ArrayList<Front> implements
		DLGResult<Front> {

	private static final long serialVersionUID = 1L;

	public String toString() {
		String res = "DLG Multi Result (" + this.size() + " runs)\n";
		int run = 1;
		for (Front f : this) {
			res += run + ": [\n" + f + "]\n";
			run++;
		}
		return res;

	}
	
	public Front getReferenceFront(Optimization opt) throws DlgParseException {
		Front rf = new Front();
		for (Front front : this) {
			for (AutoDockSolution solution : front) {
				if (!isDominated(solution, opt)) {
					rf.add(solution);
				}
			}
		}
		return rf;
	}
	
	private boolean isDominated(AutoDockSolution sol, Optimization opt) throws DlgParseException {
		for (Front front : this) {
			for (AutoDockSolution solution : front) {
				switch (opt) {
				case SUB_ENERGIES:
					if ((solution.getEnergy1()<sol.getEnergy1())
							&& (solution.getEnergy2()<=sol.getEnergy2())) {
						return true;
					} else if ((solution.getEnergy1()<=sol.getEnergy1())
							&& (solution.getEnergy2()<sol.getEnergy2())) {
						return true;
					}
					break;
				case TOTAL_ENERGY_AND_RMSD:
					if ((solution.getTotalEnergy()<sol.getTotalEnergy())
							&& (solution.getRmsd()<=sol.getRmsd())) {
						return true;
					} else if ((solution.getTotalEnergy()<=sol.getTotalEnergy())
							&& (solution.getRmsd()<sol.getRmsd())) {
						return true;
					}
					break;
				default:
					throw new DlgParseException("Tipo de optimización no reconocido para crear el rf.");
				}
				
			}
		}
		return false;
	}

	@Override
	public boolean checkRmsdCorrectCalculation(Reference reference)
			throws DlgParseException {
		return this.checkRmsdCorrectCalculation(reference, false);
	}
	
	@Override
	public boolean checkRmsdCorrectCalculation(Reference reference, boolean verbose)
			throws DlgParseException {
		throw new DlgParseException(
				"Imposible chequear correción de RMSD en frentes.");
	}

}

