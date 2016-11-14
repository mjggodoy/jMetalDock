package es.uma.khaos.docking_service.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.uma.khaos.docking_service.threadpool.WorkerThread;

public class ThreadPool {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread(""+i, i);
            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

}
