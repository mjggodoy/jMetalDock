package es.uma.khaos.docking_service.autodock.dlg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution.Optimization;
import es.uma.khaos.docking_service.model.dlg.Front;
import es.uma.khaos.docking_service.model.dlg.result.DLGMultiResult;
import es.uma.khaos.docking_service.model.dlg.result.DLGResult;

/**
 * Parseador de DLGs creados con algoritmos multi-objetivo
 *
 * Created by esteban on 14/01/14.
 */
public class DLGMultiParser extends DLGParser<Front> {
	
	public DLGMultiParser(Optimization optimizationType) {
		super(optimizationType);
	}

	/**
	 * Lee los valores de energía totales de las soluciones del fichero DLG
	 * @throws DlgParseException 
	 * 
	 */
	@Override
	protected DLGResult<Front> parseDLGFile(String dlgFilePath)
			throws DlgParseException {

		DLGResult<Front> multiResult = new DLGMultiResult();

		String startRunLine = "BEGINNING JMETAL ALGORITHM DOCKING";

		Front front = null;
		BufferedReader br = null;

		try {
		
			br = new BufferedReader(new FileReader(dlgFilePath));
	
			String line;
			int solutionCount = 1;
			int run = 1;
			while ((line = br.readLine()) != null) {
	
				if (line.contains(startRunLine)) {
					if (front != null) {
						if (front.size() == 0) {
							br.close();
							throw new DlgParseException();
						} else {
							//System.out.println("ACABA RUN " + run);
							run++;
							multiResult.add(front);
						}
					}
					//System.out.println("EMPIEZA RUN " + run);
					solutionCount=0;
					front = new Front();
				}
	
				if (line.startsWith(prefixStartModel)) {
					//System.out.println("Leyendo solucion " + solutionCount + " de run " + run);
					AutoDockSolution solution = getSolution(br, optimizationType);
					if (solution.getKi()==null) {
						System.out.println("WARNING: " + dlgFilePath
								+ " (" + run + ":" + solutionCount + ") does not have Ki value");
					}
					//solution.setConformation(getConformation(br));
					front.add(solution);
					solutionCount++;
				}
			}
	
			if ((front != null) && (front.size() > 0)) {
				multiResult.add(front);
				//System.out.println("ACABA RUN " + run);
			}
		} catch (IOException e) {
			throw new DlgParseException(e);
		} finally {
			try {
				if (br!=null) br.close();
			} catch (IOException e) {
				throw new DlgParseException(e);
			}
		}
		return multiResult;
	}

}