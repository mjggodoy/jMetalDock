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
import es.uma.khaos.docking_service.model.dlg.Reference;
import es.uma.khaos.docking_service.model.dlg.result.DLGResult;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.FtpService;
import es.uma.khaos.docking_service.service.MailService;
import es.uma.khaos.docking_service.utils.Utils;

public class WorkerThread implements Runnable {
	
	private final String COMMAND_TEMPLATE = "%s%s -p %s -l %s";
	private final String AUTODOCK_LOCATION = Constants.DIR_AUTODOCK;
	private final String AUTODOCK_EXECUTABLE = Constants.FILE_AUTODOCK;
	private final String BASE_FOLDER = Constants.DIR_BASE;
	
	private String name;
	
	private Task task;

	private boolean useRmsdAsObjective = false;

	public WorkerThread(String name, Task task) {
		this.name = name;
		this.task = task;
		if (task.getParameters().getObjectiveOption()==3) this.useRmsdAsObjective = true;
	}
	
	public void run() {
		
		try {
			System.out.println(Thread.currentThread().getName()+" Start. ID = "+task.getId());
			DatabaseService.getInstance().startTask(task.getId());
			processCommand();
			DatabaseService.getInstance().finishTask(task.getId());
			if (task.getEmail()!=null && !"".equals(task.getEmail())) {
				MailService.getInstance()
						.sendFinishedTaskMail(task.getEmail(), task.getId(), task.getToken());
			}
			System.out.println(Thread.currentThread().getName()+" End.");
		} catch (Exception e) {
			try {
				DatabaseService.getInstance().finishTaskWithError(task.getId());
				if (task.getEmail()!=null && !"".equals(task.getEmail())) {
					MailService.getInstance()
							.sendErrorTaskMail(task.getEmail(), task.getId(), task.getToken());
				}
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
		boolean fromFTP = false;
		String dpfExtension = "dpf";
		
		String dpfFileName;
		
		System.out.println(workDir);
		
		// DESCARGAMOS DEL FTP SI NO TENEMOS UN ZIP
		if (task.getParameters().getZipFile()==null) {
			task.getParameters().setZipFile(BASE_FOLDER + task.getToken() + ".zip");
			Instance inst = DatabaseService.getInstance().getInstance(task.getParameters().getInstance());
			FtpService.getInstance().download(inst.getFileName(), task.getParameters().getZipFile());
			fromFTP = true;
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
		
		if (fromFTP) dpfExtension = "-AUTODOCK-LGA.dpf";
		List<String> dpfs = Utils.searchFileWithExtension(workDir, dpfExtension);
		if (dpfs.size()!=1) throw new DpfNotFoundException();
		dpfFileName = dpfs.get(0);
		
		// PREPARAMOS DPF CON LOS PARÁMETROS
		System.out.println("VOY A PROBAR CON ..." + task.getParameters().getEvaluations() + " EVALUATIONS");
		formatDPF(new File(workDir+"/"+dpfFileName), new File(workDir+"/"+inputFile));

		// LEEMOS EL FICHERO DE REFERENCIA (EN EL CASO DE QUE HAYA)
		//TODO: Tratar mejor excepción para que continue sin RMSDs
		Reference reference = null;
		try {
			reference = new Reference(new File(workDir+"/"+inputFile), workDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
			readDLG(workDir+"/"+outputFile, reference);

			//Intentamos guardar el DLG en la BD
			try {
				DatabaseService.getInstance().insertDLG(workDir+"/"+outputFile, task.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
		dpfGen.setNumEvals(params.getEvaluations());
		dpfGen.setNumRuns(params.getRuns());
		dpfGen.setPopulationSize(params.getPopulationSize());
		dpfGen.generate();
	}
	
	// TODO: Cambiar método a otra manera.
	private void readDLG(String dlgFile, Reference reference)
			throws DlgParseException, DlgNotFoundException, DatabaseException {
		
		if (task.getParameters().getObjectiveOption()==1) {
			DLGParser<AutoDockSolution> parser = new DLGMonoParser(reference);
			try {
				parser.storeResults(dlgFile, task.getId());
			} catch (IOException e) {
				throw new DlgNotFoundException(e);
			}
		} else {
			DLGParser<Front> parser;
			if (task.getParameters().getObjectiveOption()==2) {
				parser = new DLGMultiParser(Optimization.SUB_ENERGIES, reference);
			} else if (task.getParameters().getObjectiveOption()==3) {
				parser = new DLGMultiParser(Optimization.TOTAL_ENERGY_AND_RMSD, reference);
			} else {
				throw new DlgParseException("Incorrect objectiveOpt value");
			}
			try {
				parser.storeResults(dlgFile, task.getId());
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
