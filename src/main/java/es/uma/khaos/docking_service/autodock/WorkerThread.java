package es.uma.khaos.docking_service.autodock;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import es.uma.khaos.docking_service.autodock.dlg.DLGMonoParser;
import es.uma.khaos.docking_service.autodock.dlg.DLGParser;
import es.uma.khaos.docking_service.exception.CommandExecutionException;
import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.exception.DlgNotFoundException;
import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.exception.DpfNotFoundException;
import es.uma.khaos.docking_service.exception.DpfWriteException;
import es.uma.khaos.docking_service.model.Execution;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.result.DLGResult;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;

public class WorkerThread implements Runnable {
	
	private final String COMMAND_TEMPLATE = "%s%s -p %s -l %s";
	private final String AUTODOCK_LOCATION = Constants.DIR_AUTODOCK;
	private final String AUTODOCK_EXECUTABLE = Constants.FILE_AUTODOCK;
	private final String BASE_FOLDER = Constants.DIR_BASE;
	
	private final String TEST_DIR_INSTANCE = Constants.TEST_DIR_INSTANCE;
	private final String TEST_FILE_DPF = Constants.TEST_FILE_DPF;
	
	private String name;
	private int id;
	
	private String algorithm;
	private int runs;
	private int evals;
	private int populationSize;
	//TODO: Tratar este parámetro
	private int objectiveOpt;
	
	public WorkerThread(String name, int id, String algorithm, int runs, int populationSize, int evals, int objectiveOpt) {
		this.name = name;
		this.id = id;
		this.algorithm = algorithm;
		this.runs = runs;
		this.evals = evals;
		this.populationSize = populationSize;
		this.objectiveOpt = objectiveOpt;
	}
	
	public void run() {
		
		try {
			System.out.println(Thread.currentThread().getName()+" Start. ID = "+id);
			DatabaseService.getInstance().startTask(id);
			processCommand();
			DatabaseService.getInstance().finishTask(id);
			System.out.println(Thread.currentThread().getName()+" End.");
		} catch (Exception e) {
			try {
				DatabaseService.getInstance().finishTaskWithError(id);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	private void processCommand() throws DpfWriteException, DpfNotFoundException, CommandExecutionException, DlgParseException, DlgNotFoundException, DatabaseException {
		
		String command;
		
		String workDir = String.format("%sexec-%d", BASE_FOLDER, id);
		String inputFile = String.format("exec-%d.dpf", id);
		String outputFile = String.format("exec-%d.dlg", id);
		
		System.out.println(workDir);
		
		// CREAMOS CARPETA
		command = String.format(
				"mkdir %s", workDir);
		executeCommand(command);
		
		// COPIAMOS FICHEROS DE ENTRADA
		command = String.format(
				"cp -r %s. %s", TEST_DIR_INSTANCE, workDir);
		executeCommand(command);
		
		// PREPARAMOS DPF CON LOS PARÁMETROS
		formatDPF(new File(workDir+"/"+TEST_FILE_DPF), new File(workDir+"/"+inputFile));
		
		// EJECUTAMOS AUTODOCK
		command= String.format(COMMAND_TEMPLATE,
				AUTODOCK_LOCATION,
				AUTODOCK_EXECUTABLE,
				inputFile,
				outputFile);
		if (!"".equals(Constants.DIR_AUTODOCK)) executeCommand(command, new File(workDir));
		
		// PROCESAMOS RESULTADOS
		readDLG(workDir+"/"+outputFile);
		
		// BORRAMOS CARPETA?
		
	}
	
	private void executeCommand(String command) throws CommandExecutionException {
		executeCommand(command, null);
	}
	
	private void executeCommand(String command, File workDir) throws CommandExecutionException {
		
		String s = null;
		System.out.println(command);
		
		try {
		
			// Ejecutamos el comando
			Process p;
			if (workDir != null) {
				p = Runtime.getRuntime().exec(command, null, workDir);
			} else {
				p = Runtime.getRuntime().exec(command);
			}
	
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(
	                p.getInputStream()));
	
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(
	                p.getErrorStream()));
	
	        // Leemos la salida del comando
	        System.out.println("Esta es la salida standard del comando:\n");
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }
	
	        // Leemos los errores si los hubiera
	        System.out
	                .println("Esta es la salida standard de error del comando (si la hay):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	        
		} catch (IOException e) {
			throw new CommandExecutionException(e);
		}
		
	}
	
	private void formatDPF(File inputFile, File outputFile) throws DpfWriteException, DpfNotFoundException {
		System.out.println(outputFile.getAbsolutePath());
		DPFGenerator dpfGen = new DPFGenerator(inputFile, outputFile, algorithm);
		dpfGen.setNumEvals(evals);
		dpfGen.setNumRuns(runs);
		dpfGen.setPopulationSize(populationSize);
		System.out.println(objectiveOpt);
		dpfGen.generate();
	}
	
	private void readDLG(String dlgFile) throws DlgParseException, DlgNotFoundException, DatabaseException {
		DLGParser<AutoDockSolution> parser;
		if (objectiveOpt==0) {
			parser = new DLGMonoParser();
			try {
				DLGResult<AutoDockSolution> dlgResult = parser.readFile(dlgFile);
				int run = 1;
				for (AutoDockSolution sol : dlgResult) {
					Execution exec = DatabaseService.getInstance().insertExecution(id, run);
					DatabaseService.getInstance().insertResult(sol.getTotalEnergy(), "Total Binding Energy", null, sol.getEnergy1(), sol.getEnergy2(), null, exec.getId());
					run++;
				}
			} catch (IOException e) {
				throw new DlgNotFoundException(e);
			}
			
		}
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
