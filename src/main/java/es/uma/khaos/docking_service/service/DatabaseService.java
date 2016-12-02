package es.uma.khaos.docking_service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

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
	
	private static final String SSH_HOST = Constants.SSH_HOST;
	private static final String SSH_PORT = Constants.SSH_PORT;
	private static final String SSH_USER = Constants.SSH_USER;
	private static final String SSH_PASS = Constants.SSH_PASS;
	
	private static DatabaseService instance;
	private Session jschSession = null;
	
	public DatabaseService() {
		if (!SSH_HOST.isEmpty()) {
			Properties config = new Properties();
	        JSch jsch = new JSch();
	        int sshPortNumber = Integer.valueOf(SSH_PORT);
			try {
				jschSession = jsch.getSession(SSH_USER, SSH_HOST, sshPortNumber);
				jschSession.setPassword(SSH_PASS);
		        config.put("StrictHostKeyChecking", "no");
		        //config.put("ConnectionAttempts", "3");
		        jschSession.setConfig(config);
		        jschSession.connect();
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
			stmt = conn.prepareStatement("select * from task where id=?");
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
				String objective1 = rs.getString("objective1");
				String objective2 = rs.getString("objective2");
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
	
	
	
	public Parameter insertParameter(int parameter_id, String algorithm, int evaluation, int run, int objective, int tasks_id ) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		Parameter parameter = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("insert into parameter values (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, parameter_id);
			stmt.setString(2, algorithm);
			stmt.setInt(3, evaluation);
			stmt.setInt(4, run);
			stmt.setInt(5, objective);
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
	
	public Execution insertExecution(int id, int task_id ) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		Execution execution = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("insert into execution values (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setInt(2, task_id);
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
		return execution;
	}
	

	public Result insertResult(int id, String finalbinidngenergy, String objective1, String objective2, int execution_task_id ) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		Result result = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("insert into result values (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setString(2, finalbinidngenergy);
			stmt.setString(3, objective1);
			stmt.setString(4, objective2);
			stmt.setInt(5, execution_task_id);
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
		return result;
	}
	
	
	
	public void startTask(int id) throws Exception {
		this.updateTaskState(id, RUNNING_STATE);
	}

	public void finishTask(int id) throws Exception {
		this.updateTaskState(id, FINISHED_STATE);
	}
	
	public static void main(String[] args) throws Exception {

		DatabaseService ds = new DatabaseService();
		int id = 35;
		//Getters:
		Task task = ds.getTask(id);
		System.out.println("id: " + task.getId() + " Hash " + task.getHash());
		
	}

		Result result = ds.insertResult(result_id,finalBingingEnergy, objective1, objective2, execution_task_id);
		
	}*/

}
