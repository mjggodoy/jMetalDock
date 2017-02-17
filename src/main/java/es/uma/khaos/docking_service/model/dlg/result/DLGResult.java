package es.uma.khaos.docking_service.model.dlg.result;

import java.util.List;

import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.dlg.Reference;

public interface DLGResult<T> extends List<T> {
	
	public boolean checkRmsdCorrectCalculation(Reference reference) throws DlgParseException;
	
	public boolean checkRmsdCorrectCalculation(Reference reference, boolean verbose) throws DlgParseException;
	
}
