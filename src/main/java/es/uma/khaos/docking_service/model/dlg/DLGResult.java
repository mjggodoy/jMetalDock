package es.uma.khaos.docking_service.model.dlg;

import java.util.List;

import org.pua.autodock.exception.PuaException;
import org.pua.autodock.rmsd.bean.Reference;

public interface DLGResult<T> extends List<T> {
	
	public boolean checkRmsdCorrectCalculation(Reference reference) throws PuaException;
	
	public boolean checkRmsdCorrectCalculation(Reference reference, boolean verbose) throws PuaException;
	
}
