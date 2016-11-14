package es.uma.khaos.docking_service.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import es.uma.khaos.docking_service.service.ThreadPoolService;

@WebListener
public class DockingWebListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent event) {
    	System.out.println("contextDestroyed");
    	ThreadPoolService.getInstance().shutdown();
    }

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("contextInitialized");
	}

}
