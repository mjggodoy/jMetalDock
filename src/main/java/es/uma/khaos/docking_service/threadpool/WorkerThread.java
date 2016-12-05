package es.uma.khaos.docking_service.threadpool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;

public class WorkerThread implements Runnable {
	
	private final String COMMAND_TEMPLATE = "%s%s -p %s -l %s";
	private final String AUTODOCK_LOCATION = Constants.DIR_AUTODOCK;
	private final String BASE_FOLDER = Constants.DIR_BASE;
	private final String AUTODOCK_EXECUTABLE = Constants.FILE_AUTODOCK;
	
	private final String TEST_DIR_INSTANCE = Constants.TEST_DIR_INSTANCE;
	private final String TEST_FILE_DPF = Constants.TEST_FILE_DPF;
	
	private String name;
	private int id;
	
	private String algorithm;
	private int runs;
	private int evals;
	private int objectiveOpt;
	
	
	public WorkerThread(String name, int id, String algorithm, int runs, int evals, int objectiveOpt) {
		this.name = name;
		this.id = id;
		this.algorithm = algorithm;
		this.runs = runs;
		this.evals = evals;
		this.objectiveOpt = objectiveOpt;
	}
	
	public void run() {
		
		try {
			System.out.println(Thread.currentThread().getName()+" Start. ID = "+id);
			DatabaseService.getInstance().startTask(id);
			//processCommand();
			DatabaseService.getInstance().finishTask(id);
			System.out.println(Thread.currentThread().getName()+" End.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void processCommand() {
		
		String command;
		
		try {
			
			String workDir = String.format("%sexec-%d", BASE_FOLDER, id);
			String outputFile = String.format("exec-%d.dlg", id);
			
			// CREAMOS CARPETA
			command = String.format(
					"mkdir %s", workDir);
			executeCommand(command);
			
			// COPIAMOS FICHEROS DE ENTRADA
			command = String.format(
					"cp -r %s. %s", TEST_DIR_INSTANCE, workDir);
			executeCommand(command);
			
			// PREPARAMOS DPF CON LOS PARÁMETROS
			formatDPF();
			
			// EJECUTAMOS AUTODOCK
			command= String.format(COMMAND_TEMPLATE,
					AUTODOCK_LOCATION,
					AUTODOCK_EXECUTABLE,
					TEST_FILE_DPF,
					outputFile);
			executeCommand(command, new File(workDir));
			
			// PROCESAMOS RESULTADOS
			
			// BORRAMOS CARPETA?
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void executeCommand(String command) throws IOException {
		executeCommand(command, null);
	}
	
	private void executeCommand(String command, File workDir) throws IOException {
		
		String s = null;
		System.out.println(command);
		
		// Ejcutamos el comando
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
		
	}
	
	private void formatDPF() {
		System.out.println(algorithm);
		System.out.println(runs);
		System.out.println(evals);
		System.out.println(objectiveOpt);
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
