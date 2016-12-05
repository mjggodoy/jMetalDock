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

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.model.response.TaskResponse;
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
		
		int taskId = 0;
		TaskResponse objResp = null;
		
		String id = request.getParameter("id");
		String token = request.getParameter("token");
		
		System.out.println(id);
		System.out.println(token);
		
		response.setContentType("application/json");
		
		try {
			if (id==null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, Constants.RESPONSE_TASK_MSG_NOT_ID);
			} else {
				taskId = Integer.parseInt(id);
				Task task = DatabaseService.getInstance().getTask(taskId);
				if (task==null || !task.getHash().equals(token)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED);
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					System.out.println(task.getState());
					objResp = new TaskResponse(taskId, token, task.getState());
				}
			}
			
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, Constants.RESPONSE_TASK_MSG_ID_NOT_NUMBER);
		} catch (DatabaseException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Constants.RESPONSE_ERROR_DATABASE);
		}
		
		if (objResp!=null) {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(objResp));
			System.out.println(gson.toJson(objResp));
			out.flush();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		try {
			Random sr = SecureRandom.getInstance("SHA1PRNG");
			String token = new BigInteger(130, sr).toString(32);
			
			Task task = DatabaseService.getInstance().insertTask(token);
			Runnable worker = new WorkerThread("DOCKING", task.getId());
			ThreadPoolService.getInstance().execute(worker);
			
			Gson gson = new Gson();
			TaskResponse objResp
				= new TaskResponse(
						task.getId(), token, task.getState());
			String json = gson.toJson(objResp);
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Constants.RESPONSE_ERROR_DATABASE);
		}
	}

}
