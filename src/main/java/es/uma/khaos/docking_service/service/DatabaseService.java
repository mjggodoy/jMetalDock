package es.uma.khaos.docking_service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Execution;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;

public final class DatabaseService {

	private static final String RUNNING_STATE = "running";
	private static final String FINISHED_STATE = "finished";
	private static final String ERROR_STATE = "error";

	private static final String schema = Constants.MYSQL_SCHEMA;
	private static final String user = Constants.MYSQL_USER;
	private static final String pass = Constants.MYSQL_PASS;
	
	private String ip = Constants.MYSQL_IP;
	private String port = Constants.MYSQL_PORT;
	
	private static final String SSH_HOST = Constants.SSH_HOST;
	private static final String SSH_PORT = Constants.SSH_PORT;
	private static final String SSH_USER = Constants.SSH_USER;
	private static final String SSH_PASS = Constants.SSH_PASS;
	private static final String SSH_REDIRECTION_PORT = Constants.SSH_REDIRECTION_PORT;
	
	private static DatabaseService instance;
	private Session jschSession = null;
	
	private DatabaseService() {
		if (!SSH_HOST.isEmpty()) {
			int sshPortNumber = Integer.valueOf(SSH_PORT);
			int sshRedirectionPortNumber = Integer.valueOf(SSH_REDIRECTION_PORT);
			int dbPortNumber = Integer.valueOf(port);
			try {
				jschSession = new JSch().getSession(SSH_USER, SSH_HOST, sshPortNumber);
				jschSession.setPassword(SSH_PASS);
				jschSession.setConfig("StrictHostKeyChecking", "no");
				jschSession.connect();
				jschSession.setPortForwardingL(sshRedirectionPortNumber, ip, dbPortNumber);
				ip = "localhost";
				port = SSH_REDIRECTION_PORT;
				System.out.println("SSH connected.");
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized DatabaseService getInstance()  {
		if(instance == null) {
			instance = new DatabaseService();
		}
		return instance;
	}
	
	public void shutdown() {
		if (jschSession != null) {
			jschSession.disconnect();
			System.out.println("SSH disconected.");
		}
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
			stmt = conn.prepareStatement(
					"insert into task (hash) values (?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, hash);
			stmt.execute();

			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				task = new Task(rs.getInt(1), hash, "sent");
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

	public void updateTaskState(int id, String state) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("update task set state=? where id=?");
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

	//TODO: Tratar las excepciones en todos los métodos como aquí
	public Task getTask(int id) throws DatabaseException {

		Task task = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn.prepareStatement("select * from task where id=?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				task = new Task(rs.getInt("id"), rs.getString("hash"), rs.getString("state"));
			}

		} catch (SQLException e) {
			throw new DatabaseException(e);
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}

		return task;

	}
	
	public Task getTaskParameter(int id) throws DatabaseException {

		Task task = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn.prepareStatement("select * from task a, parameters_set b where b.task_id =? and a.id = b.task_id;");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				
				ParameterSet p = new ParameterSet(rs.getInt("id"), rs.getString("algorithm"), rs.getInt("evaluations"), rs.getInt("population_size"), rs.getInt("runs"), rs.getInt("objective"), rs.getInt("task_id"));
				task = new Task(rs.getInt("id"), rs.getString("hash"), rs.getString("state"), p);
			}

		} catch (SQLException e) {
			throw new DatabaseException(e);
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}

		return task;

	}

	public void startTask(int id) throws Exception {
		this.updateTaskState(id, RUNNING_STATE);
	}

	public void finishTask(int id) throws Exception {
		this.updateTaskState(id, FINISHED_STATE);
	}
	
	public void finishTaskWithError(int id) throws Exception {
		this.updateTaskState(id, ERROR_STATE);
	}
	
	/*
	 * PARAMETER
	 */

	public ParameterSet getParameter(int id) throws Exception {

		ParameterSet parameter = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from parameters_set where id=?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {

				id = rs.getInt("id");
				String algorithm = rs.getString("algorithm");
				int evaluations = rs.getInt("evaluations");
				int populationSize = rs.getInt("population_size");
				int runs = rs.getInt("runs");
				int objective = rs.getInt("objective_opt");
				int task_id = rs.getInt("task_id");
				parameter = new ParameterSet(id, algorithm, evaluations, populationSize, runs, objective, task_id);
				
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
	
	public ParameterSet insertParameter(String algorithm, int evaluations, int populationSize, int runs, int objectiveOpt, int taskId ) throws Exception { 

		Connection conn = null;
		PreparedStatement stmt = null;
		ParameterSet parameter = null;
		ResultSet rs = null;
		
		String statement = "insert into parameters_set (algorithm, evaluations, population_size, runs, objective, task_id)"
				+ " values (?, ?, ?, ?, ?, ?)"; 

		try {
			conn = openConnection();
			stmt = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, algorithm);
			stmt.setInt(2, evaluations);
			stmt.setInt(3, populationSize);
			stmt.setInt(4, runs);
			stmt.setInt(5, objectiveOpt);
			stmt.setInt(6, taskId);
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				parameter = new ParameterSet(rs.getInt(1), algorithm, evaluations, populationSize, runs, objectiveOpt, taskId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return parameter;
	}
	
	/*
	 * EXECUTION
	 */
	
	public List<Execution> getExecutions(int task_id) throws Exception{

		List<Execution> executions = new ArrayList<Execution>();
		Execution execution = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from execution where task_id=?");
			stmt.setInt(1, task_id);

			rs = stmt.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				task_id = rs.getInt("task_id");
				int run = rs.getInt("run");
				execution = new Execution(id, task_id, run);
				executions.add(execution);	
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
	
		return executions;
		
	}
	
	
	//Execution objective with run and id as parameters
	
	public Execution getExecution(int task_id, int run) throws Exception{

		Execution execution = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from execution where task_id=? and run=?");
			stmt.setInt(1, task_id);
			stmt.setInt(2, run);

			rs = stmt.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				task_id = rs.getInt("task_id");
				execution = new Execution(id, task_id, run);
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
	
	public Execution insertExecution(int task_id, int run) throws DatabaseException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Execution execution = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("insert into execution (task_id, run) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, task_id);
			stmt.setInt(2, run);
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				
				execution = new Execution(rs.getInt(1), task_id, run);
			}

		} catch (SQLException e) {
			throw new DatabaseException(e);
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}
		return execution;
	}
	
	/*
	 * RESULT
	 */
	
	public Result getResult(int id) throws Exception{
		
		Result result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> objectives = new ArrayList<String>();
		
		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from result where id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {

				id = rs.getInt("id");
				float finalBindingEnergy = rs.getFloat("final_binding_energy");
				String objective1 = rs.getString("objective1");
				String objective2 = rs.getString("objective2");
				objectives.add(objective1);
				objectives.add(objective2);
				float intermolecularEnergy = rs.getFloat("intermolecular_energy");
				float intramolecularEnergy = rs.getFloat("intramolecular_energy");
				float rmsd = rs.getInt("rmsd");
				int executionId = rs.getInt("execution_id");
				result = new Result(id,finalBindingEnergy, objectives,intermolecularEnergy, intramolecularEnergy, rmsd, executionId);
			
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
	
	public Result getResultByExecutionId(int executionId) throws Exception{
		
		Result result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> objectives = new ArrayList<String>();

		
		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from result where execution_id=?");
			stmt.setInt(1,executionId);
			rs = stmt.executeQuery();

			if (rs.next()) {

				int id = rs.getInt("id");
				float finalBindingEnergy = rs.getFloat("final_binding_energy");
				String objective1 = rs.getString("objective1");
				String objective2 = rs.getString("objective2");
				objectives.add(objective1);
				objectives.add(objective2);
				float intermolecularEnergy = rs.getFloat("intermolecular_energy");
				float intramolecularEnergy = rs.getFloat("intramolecular_energy");
				float rmsd = rs.getFloat("rmsd");
				executionId = rs.getInt("execution_id");
				result = new Result(id,finalBindingEnergy, objectives,intermolecularEnergy, intramolecularEnergy, rmsd, executionId);
			
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
	
	public Result getResultByTaskIdAndRun(int id, int run) throws Exception{

		Result result = null;
		ArrayList<String> objectives = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from execution a, result b where task_id = ? and run = ? and a.id = b.execution_id;");
			stmt.setInt(1, id);
			stmt.setInt(2, run);
			rs = stmt.executeQuery();

			while (rs.next()) {
				
				id = rs.getInt("id");
				float finalBindingEnergy = rs.getFloat("final_binding_energy");
				String objective1 = rs.getString("objective1");
				String objective2 = rs.getString("objective2");
				objectives.add(objective1);
				objectives.add(objective2);
				float intermolecularEnergy = rs.getFloat("intermolecular_energy");
				float intramolecularEnergy = rs.getFloat("intramolecular_energy");
				float rmsd = rs.getFloat("rmsd");
				int executionId = rs.getInt("execution_id");
				result = new Result(id,finalBindingEnergy, objectives, intermolecularEnergy, intramolecularEnergy, rmsd, executionId);		
	
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

	public Result insertResult(float finalBindingEnergy, String objective1, String objective2, float intermolecularEnergy, float intramolecularEnergy, float rmsd, int execution_id) throws DatabaseException {

		Connection conn = null;
		PreparedStatement stmt = null;
		Result result = null;
		ResultSet rs = null;
		ArrayList<String> objectives = new ArrayList<String>();
		objectives.add(objective1);
		objectives.add(objective2);

		try {
			
			conn = openConnection();
			stmt = conn.prepareStatement("insert into result (final_binding_energy, objective1, objective2, execution_id) "
					+ "values (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setFloat(1, finalBindingEnergy);
			stmt.setString(2, objective1);
			stmt.setString(3, objective2);
			stmt.setFloat(4, intermolecularEnergy);
			stmt.setFloat(5, intramolecularEnergy);
			stmt.setFloat(6, rmsd);
			stmt.setInt(7, execution_id);
			stmt.execute();
			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				result = new Result(rs.getInt(1), finalBindingEnergy, objectives, intermolecularEnergy, intramolecularEnergy, rmsd, execution_id);
			}

		} catch (SQLException e) {
			throw new DatabaseException(e);
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		int id=1;
		Result result = DatabaseService.getInstance().getResult(id);
		System.out.println(result.getFinalBindingEnergy());
		
	}
	
}
