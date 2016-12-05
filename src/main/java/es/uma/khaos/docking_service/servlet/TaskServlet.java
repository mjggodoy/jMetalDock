package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.model.response.TaskResponse;
import es.uma.khaos.docking_service.model.response.TaskRunResponse;
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
		TaskResponse objResp;
		
		String id = request.getParameter("id");
		String token = request.getParameter("token");
		
		System.out.println(id);
		System.out.println(token);
		
		try {
			if (id==null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				objResp = new TaskResponse(
						Constants.RESPONSE_TASK_MSG_NOT_ID,
						taskId, token);
			} else if (token==null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				objResp = new TaskResponse(
						Constants.RESPONSE_TASK_MSG_NOT_TOKEN,
						taskId, token);
			} else {
				taskId = Integer.parseInt(id);
				Task task = DatabaseService.getInstance().getTask(taskId);
				if (task==null || !token.equals(task.getHash())) {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					objResp = new TaskResponse(
							Constants.RESPONSE_TASK_MSG_UNALLOWED,
							taskId, token);
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					objResp = new TaskResponse(
							Constants.RESPONSE_TASK_RETURNED_OK,
							taskId, token, task.getState());
				}
			}
			
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			objResp = new TaskResponse(
					Constants.RESPONSE_TASK_MSG_ID_NOT_NUMBER,
					0, token);
		} catch (DatabaseException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			objResp = new TaskResponse(
					Constants.RESPONSE_ERROR_DATABASE,
					0, token);
		}
		
		response.setContentType("application/json");
		
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(objResp));
		out.flush();
		
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
			TaskResponse objResp
				= new TaskResponse(
						Constants.RESPONSE_NEWTASK_MSG_OK,
						task.getId(), token);
			String json = gson.toJson(objResp);
			
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_CREATED);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			Gson gson = new Gson();
			TaskResponse objResp
				= new TaskResponse(
						Constants.RESPONSE_ERROR_DATABASE);
			String json = gson.toJson(objResp);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		}
	}
	
}
