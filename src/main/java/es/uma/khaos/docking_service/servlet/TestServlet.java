package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uma.khaos.docking_service.threadpool.WorkerThread;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String token = "PUAAAA";
		
		try {
			Task task = DatabaseService.getInstance().insertTask(token);
			Runnable worker = new WorkerThread("DOCKING", task.getId());
			ThreadPoolService.getInstance().execute(worker);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
