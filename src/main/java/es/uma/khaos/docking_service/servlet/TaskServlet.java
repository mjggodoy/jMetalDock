package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	protected void defaultValues(int popSize, int evals, int runs,
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


	protected int maxMinRangeEvals(int evals) {

		if (evals > Constants.DEFAULT_MAX_NUMBER_EVALUATIONS) {

			evals = Constants.DEFAULT_MAX_NUMBER_EVALUATIONS;

		} else if (evals < Constants.DEFAULT_MIN_NUMBER_EVALUATIONS) {

			evals = Constants.DEFAULT_MIN_NUMBER_EVALUATIONS;
		}

		return evals;
	}

	protected int maxMinRangePopSize(int popSize) {

		if (popSize > Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE) {

			popSize = Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE;

		} else if (popSize < Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE) {

			popSize = Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE;
		}

		return popSize;
	}

	protected int maxMinRangeRuns(int runs) {

		if (runs > Constants.DEFAULT_MAX_NUMBER_RUNS) {

			runs = Constants.DEFAULT_MAX_NUMBER_RUNS;

		} else if (runs < Constants.DEFAULT_MIN_NUMBER_RUNS) {

			runs = Constants.DEFAULT_MIN_NUMBER_RUNS;
		}

		return runs;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// TODO: Check correctness of parameters

		String runsParam = request.getParameter("runs");
		String algorithm = request.getParameter("algorithm");
		String evalsParam = request.getParameter("evaluations");
		String popSizeParam = request.getParameter("population_size");
		int objectiveOpt = 0;

		int popSize = 0;
		int evals = 0;
		int runs = 0;

		response.setContentType("application/json");

		try {

			if (StringUtils.isNullOrEmpty(algorithm)) {

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, String
						.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR,
								"algorithm"));

			} else {

				if (StringUtils.isNullOrEmpty(runsParam)) {

					if (popSizeParam != null) {

						popSize = Integer.parseInt(popSizeParam);

					} else {

						popSize = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
					}

					if (evalsParam != null) {

						evals = Integer.parseInt(evalsParam);

					} else {

						evals = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
					}

					runs = Constants.DEFAULT_NUMBER_RUNS;
					System.out.println("runs" + runs);
					popSize = maxMinRangePopSize(popSize);
					evals = maxMinRangeEvals(evals);
					defaultValues(popSize, evals, runs, algorithm,objectiveOpt, response);

				}

				if (StringUtils.isNullOrEmpty(evalsParam)) {

					if (popSizeParam != null) {

						popSize = Integer.parseInt(popSizeParam);

					} else {

						popSize = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
					}

					if (runsParam != null) {

						runs = Integer.parseInt(runsParam);

					} else {

						runs = Constants.DEFAULT_NUMBER_RUNS;
					}

					evals = Constants.DEFAULT_NUMBER_EVALUATIONS;
					System.out.println("evals" + evals);
					popSize = maxMinRangePopSize(popSize);
					runs = maxMinRangeRuns(runs);
					defaultValues(popSize, evals, runs, algorithm,objectiveOpt, response);

				}

				if (StringUtils.isNullOrEmpty(popSizeParam)) {

					if (evalsParam != null) {

						evals = Integer.parseInt(evalsParam);

					} else {

						evals = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
					}

					if (runsParam != null) {

						runs = Integer.parseInt(runsParam);

					} else {

						runs = Constants.DEFAULT_NUMBER_RUNS;
					}

					popSize = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
					System.out.println("popSize" + popSize);
					evals = maxMinRangeEvals(evals);
					runs = maxMinRangeRuns(runs);
					defaultValues(popSize, evals, runs, algorithm,objectiveOpt, response);

				}

				if (!StringUtils.isNullOrEmpty(runsParam)
						&& !StringUtils.isNullOrEmpty(evalsParam)
						&& !StringUtils.isNullOrEmpty(popSizeParam)) {

					runs = Integer.parseInt(runsParam);
					evals = Integer.parseInt(evalsParam);
					popSize = Integer.parseInt(popSizeParam);
					popSize = maxMinRangePopSize(popSize);
					evals = maxMinRangeEvals(evals);
					runs = maxMinRangeRuns(runs);
					defaultValues(popSize, evals, runs, algorithm,objectiveOpt, response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					Constants.RESPONSE_ERROR_DATABASE);
		}

	}
}
