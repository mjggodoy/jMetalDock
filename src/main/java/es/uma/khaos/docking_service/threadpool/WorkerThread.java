package es.uma.khaos.docking_service.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import es.uma.khaos.docking_service.service.DatabaseService;

public class WorkerThread implements Runnable {
	
	private final String AUTODOCK_LOCATION = "/opt/autodock/";
	private final String AUTODOCK_EXECUTABLE = "autodock4";

	private String command;
	private int id;
	
	public WorkerThread(String s, int id) {
		this.command = s;
		this.id = id;
	}
	
	public void run() {
		
		try {
			System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
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
		
		String command = AUTODOCK_LOCATION + AUTODOCK_EXECUTABLE;
		String s = null;
		
		try {
		
			// Ejcutamos el comando
	        Process p = Runtime.getRuntime().exec(command);
	
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
	        
	        Thread.sleep(50000);
        
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String toString(){
		return this.command;
	}
}
