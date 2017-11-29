package es.uma.khaos.docking_service.autodock.dlg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution.Optimization;
import es.uma.khaos.docking_service.model.dlg.result.DLGMonoResult;
import es.uma.khaos.docking_service.model.dlg.result.DLGResult;
import es.uma.khaos.docking_service.service.DatabaseService;

/**
 * Parseador de DLGs creados con algoritmos mono-objetivo
 *
 * Created by esteban on 14/01/14.
 */
public class DLGMonoParser extends DLGParser<AutoDockSolution> {
	
	private final static Optimization monoOptimizationType
		= Optimization.TOTAL_ENERGY;
	
	public DLGMonoParser() {
		super(monoOptimizationType);
	}

	/**
	 * Lee los valores de energía totales de las soluciones del fichero DLG
	 * @throws DlgParseException 
	 */
	protected DLGResult<AutoDockSolution> parseDLGFile(String dlgFilePath)
			throws DlgParseException {

		DLGResult<AutoDockSolution> monoResult = new DLGMonoResult();
		BufferedReader br = null;
		
		try {
			
			br = new BufferedReader(new FileReader(dlgFilePath));

			int i = 1;
			String line;
			while ((line = br.readLine()) != null) {

				String startRunLine = "DOCKED: USER    Run = " + i;
				String lineRmsdTable = "RMSD TABLE";

				if (line.equals(startRunLine)) {

					AutoDockSolution solution = getSolution(br, optimizationType);
					//solution.setConformation(getConformation(br));
					i++;
					monoResult.add(solution);

				} else if (line.contains(lineRmsdTable)) {
					readRmsdTable(dlgFilePath, br, monoResult);
				}
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

		return monoResult;
	}

	/**
	 * Guarda los valores de energía de las soluciones del fichero DLG
	 * en la base de datos
	 * @throws DlgParseException
	 */
	protected void storeResults(String dlgFilePath, int taskId, int run)
			throws DlgParseException {

		BufferedReader br = null;

		try {

			Result result = DatabaseService.getInstance().insertResult(taskId, run);

			br = new BufferedReader(new FileReader(dlgFilePath));

			int i = 1;
			String line;
			while ((line = br.readLine()) != null) {

				String startRunLine = "DOCKED: USER    Run = " + i;
				String lineRmsdTable = "RMSD TABLE";

				if (line.equals(startRunLine)) {

					AutoDockSolution sol = getSolution(br, optimizationType);
					DatabaseService.getInstance().insertSolution(sol.getTotalEnergy(), "Total Binding Energy", null, sol.getEnergy1(), sol.getEnergy2(), null, result.getId());
					//solution.setConformation(getConformation(br));
					i++;

				//} else if (line.contains(lineRmsdTable)) {
				//	readRmsdTable(dlgFilePath, br, monoResult);
				}
			}

		} catch (IOException e) {
			throw new DlgParseException(e);
		} catch (DatabaseException e) {
			throw new DlgParseException(e);
		} finally {
			try {
				if (br!=null) br.close();
			} catch (IOException e) {
				throw new DlgParseException(e);
			}
		}

	}

	private void readRmsdTable(String dlgFilePath, BufferedReader br,
			DLGResult<AutoDockSolution> monoResult) throws IOException {

		for (int i = 0; i < 7; i++) {
			br.readLine();
		}
		for (int i = 0; i < monoResult.size(); i++) {

			String line = br.readLine();
			if (!line.contains("RANKING")) {
				System.err.println("ERROR EN RMSD LEYENDO: " + dlgFilePath);
				System.exit(-1);
			}

			StringTokenizer st = new StringTokenizer(line);
			st.nextToken(" ");
			st.nextToken(" ");
			String runString = st.nextToken(" ");
			st.nextToken(" ");
			st.nextToken(" ");
			String rmsdString = st.nextToken(" ");
			String endString = st.nextToken(" ");
			if ((!"RANKING".equals(endString)) || st.hasMoreTokens()) {
				System.err.println("ERROR EN RMSD LEYENDO: " + dlgFilePath);
				System.err.println("Error leyendo línea: " + line);
				System.out.println("endString: " + endString);
				System.out.println(st.hasMoreTokens());
				System.exit(-1);
			}

			double rmsd = Double.valueOf(rmsdString);
			int run = Integer.parseInt(runString);
			try {
				monoResult.get(run - 1).setRmsd(rmsd);
			} catch (Exception e) {
				System.out.println(line);
				System.out.println(run);
				System.out.println(rmsd);
				throw e;
			}
		}
	}

}
