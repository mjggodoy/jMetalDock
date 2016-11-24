package es.uma.khaos.docking_service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.ExecutionSingleObjective;
import es.uma.khaos.docking_service.model.MultiObjectiveResults;
import es.uma.khaos.docking_service.model.Parameter;
import es.uma.khaos.docking_service.model.SingleObjectiveResults;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;

public final class DatabaseService {

	private static final String RUNNING_STATE = "running";
	private static final String FINISHED_STATE = "finished";

	private static final String ip = Constants.MYSQL_IP;
	private static final String port = Constants.MYSQL_PORT;
	private static final String schema = Constants.MYSQL_SCHEMA;
	private static final String user = Constants.MYSQL_USER;
	private static final String pass = Constants.MYSQL_PASS;

	private static DatabaseService instance;

	public DatabaseService() {
	}

	public static synchronized DatabaseService getInstance() {
		if (instance == null) {
			instance = new DatabaseService();
		}
		return instance;
	}

	private Connection openConnection() throws Exception {

		Connection conn = null;

		try {
			String dbUrl = "jdbc:mysql://" + ip + ":" + port + "/" + schema;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return conn;

	}

	/*
	 * TASK
	 */

	public Task insertTask(String hash) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Task task = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("insert into tasks (hash) values (?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, hash);
			stmt.execute();

			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				task = new Task(rs.getInt(1), hash);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return task;
	}

	public void updateTaskState(int id, String state) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("update tasks set state=? where id=?");
			stmt.setString(1, state);
			stmt.setInt(2, id);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public Task getTask(int id) throws Exception {

		Task task = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int task_id = 0;
		String hash = null;

		try {

			conn = openConnection();
			stmt = conn.prepareStatement("select * from tasks where id=?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				task_id = rs.getInt("id");
				hash = rs.getString("hash");
				task = new Task(task_id, hash);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return task;

	}

	public Parameter getParameter(int tasks_id) throws Exception {

		Parameter parameter = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from parameters where tasks_id=?");
			stmt.setInt(1, tasks_id);

			rs = stmt.executeQuery();

			if (rs.next()) {

				int parameter_id = rs.getInt("id");
				String algorithm = rs.getString("algorithm");
				int evaluations = rs.getInt("evaluations");
				int runs = rs.getInt("runs");
				int objectives = rs.getInt("objectives");
				tasks_id = rs.getInt("tasks_id");
				parameter = new Parameter(parameter_id, algorithm, evaluations,
						runs, objectives, tasks_id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return parameter;

	}

	public SingleObjectiveResults getSingleObjectiveResults(
			int parameters_tasks_id) throws Exception {

		SingleObjectiveResults sor = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from single_objective_results where parameters_tasks_id=?");
			stmt.setInt(1, parameters_tasks_id);

			rs = stmt.executeQuery();

			if (rs.next()) {

				int result_id = rs.getInt("id");
				String finalbindingenergy = rs.getString("finalbindingenergy");
				String objective = rs.getString("objective");
				int runs = rs.getInt("run");
				int parameters_parameter_id = rs.getInt("parameters_id");
				parameters_tasks_id = rs.getInt("parameters_tasks_id");
				sor = new SingleObjectiveResults(result_id, finalbindingenergy,
						objective, runs, parameters_parameter_id,
						parameters_tasks_id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return sor;
	}

	public MultiObjectiveResults getMultiObjectiveResults(int parameters_tasks_id) throws Exception {

		MultiObjectiveResults mor = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from multi_objective_results where parameters_tasks_id=?");
			stmt.setInt(1, parameters_tasks_id);
			rs = stmt.executeQuery();

			if (rs.next()) {

				int result_id = rs.getInt("result_id");
				String objective1 = rs.getString("objective1");
				String objective2 = rs.getString("objective2");
				String front_id = rs.getString("front_id");
				int parameters_parameter_id = rs
						.getInt("parameters_parameter_id");
				int parameters_task_id = rs.getInt("parameters_tasks_id");
				mor = new MultiObjectiveResults(result_id, objective1,
						objective2, front_id, parameters_parameter_id,
						parameters_task_id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return mor;
	}
	
	
	public ExecutionSingleObjective getExecutionSingleObjective(int tasks_id) throws Exception{
		
		ExecutionSingleObjective eso = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn.prepareStatement("select * from execution_single_objective where tasks_id=?");
			stmt.setInt(1, tasks_id);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				
				int execution_id = rs.getInt("execution_id");
				int run = rs.getInt("run");
				int single_objective_results_result_id = rs.getInt("single_objective_results_result_id");
				int single_objective_results_parameters_parameters_id = rs.getInt("single_objective_results_parameters_parameter_id");
				int single_objective_results_parameters_tasks_id = rs.getInt("single_objective_results_parameters_tasks_id");
				tasks_id = rs.getInt("tasks_id");
				
				eso = new ExecutionSingleObjective(execution_id, run, single_objective_results_result_id,
						single_objective_results_parameters_parameters_id, single_objective_results_parameters_tasks_id,tasks_id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		
		return eso;
	}
	

	public void startTask(int id) throws Exception {
		this.updateTaskState(id, RUNNING_STATE);
	}

	public void finishTask(int id) throws Exception {
		this.updateTaskState(id, FINISHED_STATE);
	}

	public static void main(String[] args) throws Exception {

		int id = 35;
		DatabaseService ds = new DatabaseService();
		/*Task task = ds.getTask(id);
		System.out.println("id: " + task.getId() + " Hash " + task.getHash());
		Parameter parameter = ds.getParameter(id);
		System.out.println("task_id: " + parameter.getTasks_id()
				+ " algorithm: " + parameter.getAlgorithm() + " evaluations: "
				+ parameter.getEvaluations() + " objectives: "
				+ parameter.getObjectives() + " runs: " + parameter.getRuns());

		SingleObjectiveResults sor = ds.getSingleObjectiveResults(id);
		System.out.println("id: " + sor.getResult_id()
				+ " finalbindingenergy: " + sor.getFinalBindingEnergy()
				+ " objective: " + sor.getObjective() + " run " + sor.getRun()
				+ " parameters_id: " + sor.getParameters_parameter_id()
				+ " parameters_tasks_id: " + sor.getParameter_tasks_id());*/
		
		/*MultiObjectiveResults mor = ds.getMultiObjectiveResults(id);
		System.out.println("MultiObjectiveResults: " + "Results_id: "  + mor.getResult_id() + 
				" objective1: " + mor.getObjective1() + 
				" objective2: " + mor.getObjective2() + 
				" front_id: " + mor.getFront_id() +
				" parameters_parameter_id: " + mor.getParameters_parameters_id()+
				" parameters_tasks_id: " + mor.getParameters_tasks_id());*/
		
		ExecutionSingleObjective eso = ds.getExecutionSingleObjective(id);
		System.out.println("ExecutionSingleObjective: " + eso.getExecution_id() 
				+ " run: " + eso.getRun() + 
				" single_objective_results_result_id " + eso.getSingle_objective_results_result_id() + 
				" single_objective_results_parameters_parameters_id: " + eso.getSingle_objective_results_parameters_parameters_id() + 
				" single_objective_results_parameters_tasks_id: " + eso.getSingle_objective_results_parameters_tasks_id() +
				" task_id " + eso.getTasks_id());
		
		
	}

}
