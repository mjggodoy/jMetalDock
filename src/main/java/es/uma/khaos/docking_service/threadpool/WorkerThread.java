package es.uma.khaos.docking_service.threadpool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import es.uma.khaos.docking_service.service.DatabaseService;

public class WorkerThread implements Runnable {
	
	private final String COMMAND_TEMPLATE = "%s%s -p %s -l %s";
	private final String AUTODOCK_LOCATION = "/opt/autodock/";
	private final String BASE_FOLDER = "/home/tomcat7/";
	private final String AUTODOCK_EXECUTABLE = "autodock4";
	
	private String name;
	private int id;
	
	public WorkerThread(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public void run() {
		
		try {
			System.out.println(Thread.currentThread().getName()+" Start. ID = "+id);
			DatabaseService.getInstance().startTask(id);
			processCommand();
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
					"cp -r /home/tomcat7/1hsg/. %s", workDir);
			executeCommand(command);
			
			// EJECUTAMOS AUTODOCK
			command= String.format(COMMAND_TEMPLATE,
					AUTODOCK_LOCATION,
					AUTODOCK_EXECUTABLE,
					"1hsg.dpf",
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
	
	@Override
	public String toString(){
		return this.name;
	}
}
