package es.uma.khaos.docking_service.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolService {
	
	private static ThreadPoolService instance;
	private ExecutorService executor = null;
	
	public ThreadPoolService() {
		 executor = Executors.newFixedThreadPool(5);
	}
	
	public static synchronized ThreadPoolService getInstance()  {
		if(instance == null) {
			instance = new ThreadPoolService();
		}
		return instance;
	}
	
	public void execute(Runnable worker) {
		executor.execute(worker);
	}
	
	public void shutdown() {
		executor.shutdown();
		while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
	}

}
