package es.uma.khaos.docking_service.threadpool;

import es.uma.khaos.docking_service.service.DatabaseService;

public class WorkerThread implements Runnable {

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
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		return this.command;
	}
}
