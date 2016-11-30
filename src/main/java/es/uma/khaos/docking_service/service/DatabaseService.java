package es.uma.khaos.docking_service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Execution;
import es.uma.khaos.docking_service.model.Parameter;
import es.uma.khaos.docking_service.model.Result;
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

	public Parameter getParameter(int id) throws Exception {

		Parameter parameter = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from parameter where id=?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {

				id = rs.getInt("id");
				String algorithm = rs.getString("algorithm");
				int evaluations = rs.getInt("evaluation");
				int runs = rs.getInt("run");
				int objective = rs.getInt("objective");
				int task_id = rs.getInt("task_id");
				parameter = new Parameter(id, algorithm, evaluations, runs, objective, task_id);
				
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
	
	
	public Execution getExecution(int id) throws Exception{
		

		Execution execution = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from execution where id=?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {

				id = rs.getInt("id");
				int task_id = rs.getInt("task_id");
				execution = new Execution(id, task_id);
	
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
	
		return execution;
		
		
	}
	
	
	public Result getResult(int id) throws Exception{
		
		Result result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from result where id=?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {

				id = rs.getInt("id");
				String finalBindingEnergy = rs.getString("finalBindingEnergy");
				int objective1 = rs.getInt("objective1");
				int objective2 = rs.getInt("objective2");
				int executionTaskId = rs.getInt("execution_task_id");
				result = new Result(id,finalBindingEnergy, objective1, objective2, executionTaskId);

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
	
		return result;
		
	}
	
	
	
	/*public Parameter insert(int parameter_id, String algorithm, int evaluations, int runs, int objectives, int tasks_id ) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		Parameter parameter = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("insert into parameters values (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, parameter_id);
			stmt.setString(2, algorithm);
			stmt.setInt(3, evaluations);
			stmt.setInt(4, runs);
			stmt.setInt(5, objectives);
			stmt.setInt(6, tasks_id);
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
		return parameter;
	}
	*/

	
	
	
	public void startTask(int id) throws Exception {
		this.updateTaskState(id, RUNNING_STATE);
	}

	public void finishTask(int id) throws Exception {
		this.updateTaskState(id, FINISHED_STATE);
	}

	public static void main(String[] args) throws Exception {

		/*int id = 4;
		String algorithm = "GA";
		int evaluations = 150000;
		int runs = 30;
		int objectives = 2;
		int tasks_id = 38;*/
		//int result_id = 2;
		//String energy = "13,4 kcal/mol";
		//String objective  = "RMSD";
		//String objective1 = "RMSD";
		//String objective2 = "intermolecular";
		//String frontId = "4";
		//int parameters_parameter_id = 3;
		//int parameters_tasks_id = 37;
		int execution_id=1;
		int run = 31;
		int single_objective_results_result_id=1;
		int single_objective_results_parameters_parameter_id = 1;
		int single_objective_results_parameters_tasks_id = 35;
		int task_id=31;

		DatabaseService ds = new DatabaseService();
		String hash= "XUXA";
		
		//Inserters:
		
		System.out.println("Start insert");
		Task task = ds.insertTask(hash);
		//Parameter parameter = ds.insert(id, algorithm, evaluations, runs, objectives, tasks_id);
		//SingleObjectiveResults sor = ds.insertSingleObjectiveResults(result_id, energy, objective, run, parameters_parameter_id, parameters_tasks_id);
		//MultiObjectiveResults mor = ds.insertMultiObjectiveResults(result_id, objective1, objective2, frontId, parameters_parameter_id, parameters_tasks_id);
		
		ExecutionSingleObjective eso = ds.insertExecutionSingleObjective(execution_id, run, single_objective_results_result_id, 
				single_objective_results_parameters_parameter_id, single_objective_results_parameters_tasks_id, task_id);
		
		System.out.println("End insert");
		
		
		//Getters:
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
		
		/*ExecutionSingleObjective eso = ds.getExecutionSingleObjective(id);
		System.out.println("ExecutionSingleObjective: " + eso.getExecution_id() 
				+ " run: " + eso.getRun() + 
				" single_objective_results_result_id " + eso.getSingle_objective_results_result_id() + 
				" single_objective_results_parameters_parameters_id: " + eso.getSingle_objective_results_parameters_parameters_id() + 
				" single_objective_results_parameters_tasks_id: " + eso.getSingle_objective_results_parameters_tasks_id() +
				" task_id " + eso.getTasks_id());*/
		
		
		/*ExecutionMultiObjective emo = ds.getExecutioMultiObjective(id);
		System.out.println("ExecutionSingleObjective: " + emo.getExecution_id() 
				+ " run: " + emo.getRun() + 
				" multi_objective_results_result_id " + emo.getMulti_objective_results_result_id() + 
				" multi_objective_results_parameters_parameters_id: " + emo.getMulti_objective_results_parameters_parameter_id() + 
				" multi_objective_results_parameters_tasks_id: " + emo.getMulti_objective_results_parameters_tasks_id() +
				" task_id " + emo.getTasks_id());*/
		
		
	}

}
