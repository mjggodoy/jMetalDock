package es.uma.khaos.docking_service.autodock.dlg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.result.DLGMonoResult;
import es.uma.khaos.docking_service.model.dlg.result.DLGResult;

/**
 * Parseador de DLGs creados con algoritmos mono-objetivo
 *
 * Created by esteban on 14/01/14.
 */
public class DLGMonoParser extends DLGParser<AutoDockSolution> {

	public DLGMonoParser() {
	}

	/**
	 * Lee los valores de energía totales de las soluciones del fichero DLG
	 * 
	 * @throws java.io.IOException
	 * @throws DlgParseException
	 */
	protected DLGResult<AutoDockSolution> parseDLGFile(String dlgFilePath)
			throws IOException, DlgParseException {

		DLGResult<AutoDockSolution> monoResult = new DLGMonoResult();

		BufferedReader br = new BufferedReader(new FileReader(dlgFilePath));

		int i = 1;
		String line;
		while ((line = br.readLine()) != null) {

			String startRunLine = "DOCKED: USER    Run = " + i;
			String lineRmsdTable = "RMSD TABLE";

			if (line.equals(startRunLine)) {

				AutoDockSolution solution = getSolution(br);
				//solution.setConformation(getConformation(br));
				i++;
				monoResult.add(solution);

			} else if (line.contains(lineRmsdTable)) {
				readRmsdTable(dlgFilePath, br, monoResult);
			}
		}

		br.close();

		return monoResult;

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
