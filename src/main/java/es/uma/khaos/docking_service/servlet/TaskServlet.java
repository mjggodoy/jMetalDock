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
		
		response.setContentType("application/json");
		
		try {
			if (id==null) {
				response.sendError(
						HttpServletResponse.SC_BAD_REQUEST,
						String.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR, "id"));
			} else {
				taskId = Integer.parseInt(id);
				Task task = DatabaseService.getInstance().getTask(taskId);
				if (task==null || !task.getHash().equals(token)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED);
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					objResp = new TaskResponse(taskId, token, task.getState());
				}
			}
			
		} catch (NumberFormatException e) {
			response.sendError(
					HttpServletResponse.SC_BAD_REQUEST,
					String.format(Constants.RESPONSE_NOT_A_NUMBER_ERROR, "id"));
		} catch (DatabaseException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Constants.RESPONSE_ERROR_DATABASE);
		}
		
		if (objResp!=null) {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(objResp));
			out.flush();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//TODO: Check correctness of parameters
		
		String runsParam  = request.getParameter("runs");
		String algorithm  = request.getParameter("algorithm");
		String evalsParam = request.getParameter("evaluations");
		
		int objectiveOpt = 0;
		int runs = Integer.parseInt(runsParam);
		int evals = Integer.parseInt(evalsParam);
		
		response.setContentType("application/json");
		
		try {
			Random sr = SecureRandom.getInstance("SHA1PRNG");
			String token = new BigInteger(130, sr).toString(32);
			
			Task task = DatabaseService.getInstance().insertTask(token);
			DatabaseService.getInstance().insertParameter(algorithm, evals, runs, objectiveOpt, task.getId());
			Runnable worker = new WorkerThread("DOCKING", task.getId(), algorithm, runs, evals, objectiveOpt);
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
