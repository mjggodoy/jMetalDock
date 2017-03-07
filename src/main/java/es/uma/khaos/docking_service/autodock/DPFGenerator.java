package es.uma.khaos.docking_service.autodock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import es.uma.khaos.docking_service.exception.DpfNotFoundException;
import es.uma.khaos.docking_service.exception.DpfWriteException;
import es.uma.khaos.docking_service.properties.Constants;

public class DPFGenerator {
	
	private File originalFile;
	private File outputFile;
	
	private String algorithm;
	private boolean useRmsdAsObjective = false;
	
	private int numEvals =  Constants.DEFAULT_NUMBER_EVALUATIONS;
	private int numRuns = Constants.DEFAULT_NUMBER_RUNS;
	private int populationSize = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
	
	private final String EVALS_DPF_KEY = "ga_num_evals";
	private final String POPULATION_SIZE_DPF_KEY = "ga_pop_size";
	private final String JMETAL_USE_RMSD_AS_OBJ_KEY = "jmetal_RMSD_as_objective";
	private final String JMETAL_ALGORITHM_DPF_KEY = "jmetal_alg";
	private final String JMETAL_RUNS_DPF_KEY = "jmetal_run";
	
	public DPFGenerator(File originalFile, File outputFile, String algorithm) {
		this.originalFile = originalFile;
		this.outputFile = outputFile;
		this.algorithm = algorithm;
	}

	public void setNumEvals(int numEvals) {
		this.numEvals = numEvals;
	}

	public void setNumRuns(int numRuns) {
		this.numRuns = numRuns;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	
	public void setUseRmsdAsObjective() {
		this.useRmsdAsObjective = true;
	}
	
	public void generate() throws DpfWriteException, DpfNotFoundException {
		
		PrintWriter pw = null;
		BufferedReader br = null;
		
		try {
			
			pw = new PrintWriter(outputFile);
			br = new BufferedReader(new FileReader(originalFile));
			
			String line;
			while ( (null!=(line=br.readLine())) && (!endGeneration(line)) ) {
				if (line.startsWith(EVALS_DPF_KEY)) {
					line = EVALS_DPF_KEY + " " + numEvals;
				} else if (line.startsWith(POPULATION_SIZE_DPF_KEY)) {
					line = POPULATION_SIZE_DPF_KEY + " " + populationSize;
				}
				pw.println(line);
			}
			if (useRmsdAsObjective) {
				pw.println(JMETAL_USE_RMSD_AS_OBJ_KEY);
			}
			pw.println(JMETAL_ALGORITHM_DPF_KEY + " " + algorithm);
			pw.println(JMETAL_RUNS_DPF_KEY + " " + numRuns);

		} catch (FileNotFoundException e) {
			throw new DpfNotFoundException(e);
		} catch (IOException e) {
			throw new DpfWriteException(e);
		} finally {
			if (pw!=null) pw.close();
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new DpfWriteException(e);
				}
			}
		}
    }
	
	private boolean endGeneration(String line) {
        return line.startsWith("ga_run") || line.startsWith("jmetal") || line.startsWith("do_global_only");
    }

}
