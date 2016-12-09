package es.uma.khaos.docking_service.model.dlg;

import java.util.List;

import es.uma.khaos.docking_service.exception.PuaException;

public interface DLGResult<T> extends List<T> {
	
	public boolean checkRmsdCorrectCalculation(Reference reference) throws PuaException;
	
	public boolean checkRmsdCorrectCalculation(Reference reference, boolean verbose) throws PuaException;
	
}
