package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.model.response.LaunchTaskResponse;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;
import es.uma.khaos.docking_service.threadpool.WorkerThread;

/**
 * Servlet implementation class Task
 */
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Random sr = SecureRandom.getInstance("SHA1PRNG");
			String token = new BigInteger(130, sr).toString(32);
			
			Task task = DatabaseService.getInstance().insertTask(token);
			Runnable worker = new WorkerThread("DOCKING", task.getId());
			ThreadPoolService.getInstance().execute(worker);
			
			Gson gson = new Gson();
			LaunchTaskResponse objResp
				= new LaunchTaskResponse(
						Constants.RESPONSE_NEWTASK_STATE_OK,
						Constants.RESPONSE_NEWTASK_MSG_OK,
						task.getId(), token);
			String json = gson.toJson(objResp);
			
			response.setContentType("application/json");
			response.setStatus(200);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("application/json");
			response.setStatus(500);
			Gson gson = new Gson();
			LaunchTaskResponse objResp
				= new LaunchTaskResponse(
						Constants.RESPONSE_NEWTASK_STATE_ERROR,
						Constants.RESPONSE_NEWTASK_MSG_ERROR,
						0, "");
			String json = gson.toJson(objResp);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		}
	}

}
