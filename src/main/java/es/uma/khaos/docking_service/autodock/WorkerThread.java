package es.uma.khaos.docking_service.autodock;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import es.uma.khaos.docking_service.autodock.dlg.DLGMonoParser;
import es.uma.khaos.docking_service.autodock.dlg.DLGMultiParser;
import es.uma.khaos.docking_service.autodock.dlg.DLGParser;
import es.uma.khaos.docking_service.exception.CommandExecutionException;
import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.exception.DlgNotFoundException;
import es.uma.khaos.docking_service.exception.DlgParseException;
import es.uma.khaos.docking_service.exception.DpfNotFoundException;
import es.uma.khaos.docking_service.exception.DpfWriteException;
import es.uma.khaos.docking_service.exception.FtpException;
import es.uma.khaos.docking_service.model.Instance;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution;
import es.uma.khaos.docking_service.model.dlg.AutoDockSolution.Optimization;
import es.uma.khaos.docking_service.model.dlg.Front;
import es.uma.khaos.docking_service.model.dlg.result.DLGResult;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.FtpService;
import es.uma.khaos.docking_service.utils.Utils;

public class WorkerThread implements Runnable {
	
	private final String COMMAND_TEMPLATE = "%s%s -p %s -l %s";
	private final String AUTODOCK_LOCATION = Constants.DIR_AUTODOCK;
	private final String AUTODOCK_EXECUTABLE = Constants.FILE_AUTODOCK;
	private final String BASE_FOLDER = Constants.DIR_BASE;
	
	private String name;
	
	private Task task;
//	private ParameterSet params;
//	private int id;
//	
//	private String algorithm;
//	private int runs;
//	private int evals;
//	private int populationSize;
//	private int objectiveOpt;
	private boolean useRmsdAsObjective = false;
	
//	private String zipFile;
	
	public WorkerThread(String name, Task task) {
		this.name = name;
		this.task = task;
//		this.id = id;
//		this.algorithm = algorithm;
//		this.runs = runs;
//		this.evals = evals;
//		this.populationSize = populationSize;
//		this.objectiveOpt = objectiveOpt;
		if (task.getParameters().getObjective()==3) this.useRmsdAsObjective = true;
//		this.zipFile = zipFile;
	}
	
	public void run() {
		
		try {
			System.out.println(Thread.currentThread().getName()+" Start. ID = "+task.getId());
			DatabaseService.getInstance().startTask(task.getId());
			processCommand();
			DatabaseService.getInstance().finishTask(task.getId());
			System.out.println(Thread.currentThread().getName()+" End.");
		} catch (Exception e) {
			try {
				DatabaseService.getInstance().finishTaskWithError(task.getId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	private void processCommand() throws DpfWriteException, DpfNotFoundException, CommandExecutionException, DlgParseException, DlgNotFoundException, DatabaseException, IOException, NoSuchAlgorithmException, FtpException {
		
		String command;

		String workDir = String.format("%sexec-%d", BASE_FOLDER, task.getId());
		String inputFile = String.format("exec-%d.dpf", task.getId());
		String outputFile = String.format("exec-%d.dlg", task.getId());
		
		String dpfFileName;
		
		System.out.println(workDir);
		
		if (task.getParameters().getZipFile()==null) {
			task.getParameters().setZipFile(BASE_FOLDER + task.getHash() + ".zip");
			Instance inst = DatabaseService.getInstance().getInstance(task.getParameters().getInstance());
			FtpService.getInstance().download(inst.getFileName(), task.getParameters().getZipFile());
		}
		
		// CREAMOS CARPETA
		command = String.format(
				"mkdir %s", workDir);
		Utils.executeCommand(command);
		
		// DESCOMPRIMIMOS FICHERO ZIP EN CARPETA DE TRABAJO
		Utils.unzip(task.getParameters().getZipFile(), workDir);
		
		// CHEQUEAMOS SI EL ZIP TIENE UNA CARPETA PADRE
		// (Y MOVEMOS LOS FICHEROS EN ESE CASO)
		Utils.containerFolderCheck(workDir);
		
		List<String> dpfs = Utils.searchFileWithExtension(workDir, "dpf");
		if (dpfs.size()!=1) throw new DpfNotFoundException();
		dpfFileName = dpfs.get(0);
		
		// PREPARAMOS DPF CON LOS PARÁMETROS
		formatDPF(new File(workDir+"/"+dpfFileName), new File(workDir+"/"+inputFile));
		
		// SI TENEMOS EL EJECUTABLE DE AUTODOCK:
		if (!"".equals(Constants.DIR_AUTODOCK))  {
			
			// EJECUTAMOS AUTODOCK
			command= String.format(COMMAND_TEMPLATE,
					AUTODOCK_LOCATION,
					AUTODOCK_EXECUTABLE,
					inputFile,
					outputFile);
			Utils.executeCommand(command, new File(workDir));
			
			// PROCESAMOS RESULTADOS
			readDLG(workDir+"/"+outputFile);
			
		}
		
		// BORRAMOS CARPETA Y FICHERO ZIP
		Utils.deleteFolder(workDir);
		Utils.deleteFolder(task.getParameters().getZipFile());
		
	}
	
	private void formatDPF(File inputFile, File outputFile) throws DpfWriteException, DpfNotFoundException {
		ParameterSet params = task.getParameters();
		System.out.println(outputFile.getAbsolutePath());
		DPFGenerator dpfGen = new DPFGenerator(inputFile, outputFile, params.getAlgorithm());
		if (useRmsdAsObjective) dpfGen.setUseRmsdAsObjective();
		dpfGen.setNumEvals(params.getEvaluation());
		dpfGen.setNumRuns(params.getRun());
		dpfGen.setPopulationSize(params.getPopulationSize());
		dpfGen.generate();
	}
	
	// TODO: Cambiar método a otra manera.
	private void readDLG(String dlgFile) throws DlgParseException, DlgNotFoundException, DatabaseException {
		
		if (task.getParameters().getObjective()==1) {
			DLGParser<AutoDockSolution> parser = new DLGMonoParser();
			try {
				DLGResult<AutoDockSolution> dlgResult = parser.readFile(dlgFile);
				int run = 1;
				for (AutoDockSolution sol : dlgResult) {
					Result result = DatabaseService.getInstance().insertResult(task.getId(), run);
					DatabaseService.getInstance().insertSolution(sol.getTotalEnergy(), "Total Binding Energy", null, sol.getEnergy1(), sol.getEnergy2(), null, result.getId());
					run++;
				}
			} catch (IOException e) {
				throw new DlgNotFoundException(e);
			}
		} else {
			DLGParser<Front> parser;
			String obj1, obj2;
			if (task.getParameters().getObjective()==2) {
				parser = new DLGMultiParser(Optimization.SUB_ENERGIES);
				obj1 = "Intermolecular energy";
				obj2 = "Intramolecular energy";
			} else if (task.getParameters().getObjective()==3) {
				parser = new DLGMultiParser(Optimization.TOTAL_ENERGY_AND_RMSD);
				obj1 = "Total Binding Energy";
				obj2 = "RMSD";
			} else {
				throw new DlgParseException("Incorrect objectiveOpt value");
			}
			try {
				DLGResult<Front> dlgResult = parser.readFile(dlgFile);
				int run = 1;
				for (Front front : dlgResult) {
					Result result = DatabaseService.getInstance().insertResult(task.getId(), run);
					for (AutoDockSolution sol : front) {
						// TODO: Crear método que inserte todas las soluciones en una conexión.
						DatabaseService.getInstance().insertSolution(sol.getTotalEnergy(), obj1, obj2, sol.getEnergy1(), sol.getEnergy2(), sol.getRmsd(), result.getId());
					}
					
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
