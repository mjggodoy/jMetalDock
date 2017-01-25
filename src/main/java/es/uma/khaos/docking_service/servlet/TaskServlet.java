package es.uma.khaos.docking_service.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;
import es.uma.khaos.docking_service.autodock.WorkerThread;

/**
 * Servlet implementation class Task
 */

@WebServlet("/Task")
@MultipartConfig
public class TaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int taskId = 0;
		Task task = null;

		String id = request.getParameter("id");
		String token = request.getParameter("token");

		response.setContentType("application/json");

		try {
			if (id == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, String
						.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR,
								"id"));
			} else {
				taskId = Integer.parseInt(id);
				task = DatabaseService.getInstance().getTaskParameter(taskId);
				if (task == null || !task.getHash().equals(token)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN,
							Constants.RESPONSE_TASK_MSG_UNALLOWED);
					task = null;
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
				}
			}

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					String.format(Constants.RESPONSE_NOT_A_NUMBER_ERROR, "id"));
		} catch (DatabaseException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					Constants.RESPONSE_ERROR_DATABASE);
		}

		if (task != null) {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(task));
			out.flush();
		}
	}

	private void launchTask(int popSize, int evals, int runs,
			String algorithm, int objectiveOpt, HttpServletResponse response)
					throws Exception {

		response.setContentType("application/json");

		Random sr = SecureRandom.getInstance("SHA1PRNG");
		String token = new BigInteger(130, sr).toString(32);

		Task task = DatabaseService.getInstance().insertTask(token);
		ParameterSet parameters = DatabaseService.getInstance()
				.insertParameter(algorithm, evals, popSize, runs, objectiveOpt,
						task.getId());

		Gson gson = new Gson();
		task.setParameters(parameters);
		String json = gson.toJson(task);

		response.setStatus(HttpServletResponse.SC_CREATED);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		Runnable worker = new WorkerThread("DOCKING", task.getId(), algorithm,
				runs, popSize, evals, objectiveOpt);
		ThreadPoolService.getInstance().execute(worker);

	}
	
	private int inRangeCheck(int value, int minValue, int maxValue) {
		if (value > maxValue) return maxValue;
		else if (value < minValue) return minValue;
		else return value;
	}
	
	
	public static void unzip(){
	    String source = "some/compressed/file.zip";
	    String destination = "some/destination/folder";

	    try {
	         
	    	ZipFile zipFile = new ZipFile(source);
	        zipFile.extractAll(destination);
	    
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void readFile(InputStream in) throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
        	System.out.println(line);
        }
        reader.close();
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO: Tratar objectiveOpt
				
		int objectiveOpt = 0, popSize, evals, runs;
		
		//File as Parameter
		
	    Part filePart = request.getPart("file"); 
	    InputStream fileContent = filePart.getInputStream();
	    readFile(fileContent);
	    
	    //Parameter

		String runsParam = request.getParameter("runs");
		String algorithm = request.getParameter("algorithm");
		String evalsParam = request.getParameter("evaluations");
		String popSizeParam = request.getParameter("population_size");
		
		response.setContentType("application/json");

		try {

			if (StringUtils.isNullOrEmpty(algorithm)){

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, String
						.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR,
								"algorithm"));
			} else {
				
				
				if (StringUtils.isNullOrEmpty(runsParam)) {
					runs = Constants.DEFAULT_NUMBER_RUNS;
				} else {
					runs = Integer.parseInt(runsParam);
					runs = inRangeCheck(
							runs,
							Constants.DEFAULT_MIN_NUMBER_RUNS,
							Constants.DEFAULT_MAX_NUMBER_RUNS);
				}
				
				if (StringUtils.isNullOrEmpty(popSizeParam)) {
					popSize = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
				} else {
					popSize = Integer.parseInt(popSizeParam);
					popSize = inRangeCheck(
							popSize,
							Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE,
							Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE);
				}
				
				if (StringUtils.isNullOrEmpty(evalsParam)) {
					evals = Constants.DEFAULT_NUMBER_EVALUATIONS;
				} else {
					evals = Integer.parseInt(evalsParam);
					evals = inRangeCheck(
							evals,
							Constants.DEFAULT_MIN_NUMBER_EVALUATIONS,
							Constants.DEFAULT_MAX_NUMBER_EVALUATIONS);
				}
				
				launchTask(popSize, evals, runs, algorithm, objectiveOpt, response);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					Constants.RESPONSE_ERROR_DATABASE);
		}

	}
}
