package es.uma.khaos.docking_service.autodock.dlg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import es.uma.khaos.docking_service.exception.DlgNotFoundException;
import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution.Optimization;
import es.uma.khaos.docking_service.model.dlg.Conformation;
import es.uma.khaos.docking_service.model.dlg.result.DLGResult;
import es.uma.khaos.docking_service.model.dlg.util.Scientific;


/**
 * Parseador general de ficheros DLG que recoge y almacena todos los resultados.
 * Debe ser extendido con una clase específica para DLGs con resultados
 * multiobjetivo o monoobjetivo.
 * 
 * @see DLGMonoParser DLGMultiParser
 *
 *      Created by esteban on 13/01/14.
 */
public abstract class DLGParser<T> {
	
	protected Optimization optimizationType;

	protected final String prefixStartModel = "DOCKED: MODEL";
	private final String prefixEndModel = "DOCKED: ENDMDL";
	
	private final String prefixStartConformation = "DOCKED: ROOT";
	private final String prefixEndConformation = "DOCKED: TORSDOF";
	
	private final String prefixStartAtom = "DOCKED: ATOM";
	
	private final String prefixTotalEnergy =
			"DOCKED: USER    Estimated Free Energy of Binding    =";
	private final String prefixInhibitionConstant =
			"DOCKED: USER    Estimated Inhibition Constant, Ki   =";
	private final String prefixEnergy1 =
			"DOCKED: USER    (1) Final Intermolecular Energy     =";
	private final String prefixEnergy2 =
			"DOCKED: USER    (2) Final Total Internal Energy     =";

	public DLGParser(Optimization optimizationType) {
		this.optimizationType = optimizationType;
	}

	/**
	 * Devuelve todos los valores de los resultados de un fichero DLG en un
	 * DLGResult
	 */
	public DLGResult<T> readFile(String dlgFilePath) throws IOException,
			DlgParseException {
		return parseDLGFile(dlgFilePath);
	}

	protected abstract void storeResults(String dlgFilePath, int taskId, int run)
			throws IOException, DlgParseException;

	protected abstract DLGResult<T> parseDLGFile(String dlgFilePath)
			throws IOException, DlgParseException;

	protected AutoDockSolution getSolution(BufferedReader br, Optimization opt)
			throws IOException, DlgParseException {
		
		String line = null;
		Double totalEnergy = null;
		Double energy1 = null;
		Double energy2 = null;
		Scientific ki = null;
		Conformation conformation = null;
		
		while (((line = br.readLine()) != null) && (!line.startsWith(prefixEndModel))) {
			if (line.startsWith(prefixTotalEnergy)) {
				totalEnergy = getEnergyComponent(line, prefixTotalEnergy);
			} else if (line.startsWith(prefixEnergy1)) {
				energy1 = getEnergyComponent(line, prefixEnergy1);
			} else if (line.startsWith(prefixEnergy2)) {
				energy2 = getEnergyComponent(line, prefixEnergy2);
			} else if (line.startsWith(prefixInhibitionConstant)) {
				ki = getInhibitionConstant(line, prefixInhibitionConstant);
			} else if ((line.startsWith(prefixStartConformation)) && (conformation==null)) {
				conformation = getConformation(br);
			}
		}
		AutoDockSolution solution = new AutoDockSolution(totalEnergy, ki, energy1, energy2);
		solution.setConformation(conformation);
		if ((solution.getTotalEnergy()==null)
				|| (solution.getEnergy1()==null)
				|| (solution.getEnergy2()==null)) {
			throw new DlgParseException("AutoDockSolution energy values not found!");
		}
		if ((solution.getConformation()==null) || (solution.getConformation().getAtoms().size()==0)) {
			throw new DlgParseException("AutoDockSolution does not have conformation!");
		}
		return solution;
	}
	
	private double getEnergyComponent(String line, String prefix) {
		line = line.replace(prefix, "");
		line = line.substring(0, line.indexOf(" kcal/mol"));
		return Double.valueOf(line);
	}
	
	private Scientific getInhibitionConstant(String line, String prefix) throws DlgParseException {
		line = line.replace(prefix, "");
		double ki;
		int exp;
		if (line.contains("mM (millimolar)")) {
			line = line.substring(0, line.indexOf(" mM (millimolar)"));
			ki = Double.valueOf(line);
			exp = -3;
		} else if (line.contains("uM (micromolar)")) {
			line = line.substring(0, line.indexOf(" uM (micromolar)"));
			ki = Double.valueOf(line);
			exp = -6;
		} else if (line.contains("nM (nanomolar)")) {
			line = line.substring(0, line.indexOf(" nM (nanomolar)"));
			ki = Double.valueOf(line);
			exp = -9;
		} else if (line.contains("pM (picomolar)")) {
			line = line.substring(0, line.indexOf(" pM (picomolar)"));
			ki = Double.valueOf(line);
			exp = -12;
		} else if (line.contains("fM (femtomolar)")) {
			line = line.substring(0, line.indexOf(" fM (femtomolar)"));
			ki = Double.valueOf(line);
			exp = -15;
		} else if (line.contains("aM (attomolar)")) {
			line = line.substring(0, line.indexOf(" aM (attomolar)"));
			ki = Double.valueOf(line);
			exp = -18;
		} else if (line.contains("zM (zeptomolar)")) {
			line = line.substring(0, line.indexOf(" zM (zeptomolar)"));
			ki = Double.valueOf(line);
			exp = -18;
		} else if (line.contains("yM (yottomolar)")) {
			line = line.substring(0, line.indexOf(" yM (yottomolar)"));
			ki = Double.valueOf(line);
			exp = -24;
		} else {
			throw new DlgParseException("Unexpected DLG format: "
					+ "Inhibition constant unrecognized\n"
					+ "\t" + line);
		}
		return new Scientific(ki, exp);
	}

	protected Conformation getConformation(BufferedReader br)
			throws NumberFormatException, IOException, DlgParseException {
		return new Conformation(br, Arrays.asList(prefixStartAtom),
				prefixEndConformation);
	}

	/**
	 * Comprueba que todos los valores 3 de las energías sean el mismo
	 * 
	 * @param value
	 * @return true (todos los valores son el mismo), false (caso contrario)
	 */
	public boolean checkSameValue3(String dlgFilePath, double value)
			throws DlgParseException, DlgNotFoundException {

		System.out.println("Checking value 3 (" + value + ") of " + dlgFilePath);

		String lineToFind = "(3) Torsional Free Energy";
		boolean res = true;
		int lineCount = 0;

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(dlgFilePath));
			String line;

			while (((line = br.readLine()) != null) && res) {

				lineCount++;

				if (line.contains(lineToFind)) {

					String stringValue = line
							.substring(line.lastIndexOf("=") + 1);
					stringValue = stringValue.substring(0,
							stringValue.indexOf("kcal/mol"));
					stringValue = stringValue.trim();
					double actualValue = Double.valueOf(stringValue);
					if (actualValue != value) {
						System.out
								.println("ENCONTRADO VALOR DIFERENTE EN LÍNEA ("
										+ lineCount + "):");
						System.out.println(line);
						res = false;
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new DlgNotFoundException("Fichero DLG (" + dlgFilePath
					+ ") no encontrado.", e);
		} catch (IOException e) {
			throw new DlgParseException(e);
		}

		if (res)
			System.out.println("OK!");

		return res;

	}

}