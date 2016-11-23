package es.uma.khaos.docking_service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.uma.khaos.docking_service.exception.DatabaseException;
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
	
	public DatabaseService() {}
	
	public static synchronized DatabaseService getInstance()  {
		if(instance == null) {
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
			stmt = conn.prepareStatement(
					"insert into tasks (hash) values (?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, hash);
			stmt.execute();
			
			rs = stmt.getGeneratedKeys();
	        if (rs.next()){
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
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
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
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
	}
	
	public Task getTask(int id) throws Exception{
		
		Task task = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{		
		
			conn = openConnection();
			stmt = conn.prepareStatement("select * from tasks where id=?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
			
			if (rs.next()) {
				int task_id = rs.getInt("id");
				String hash = rs.getString("hash");
				task = new Task(task_id, hash);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
		
		return task;
	
	}
	
public Parameter getParameter(int tasks_id) throws Exception{
		
		Parameter parameter = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			
			conn = openConnection();
			stmt = conn.prepareStatement("select * from parameters where tasks_id=?");
			stmt.setInt(1, tasks_id);

			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				int parameter_id = rs.getInt("id");
				String algorithm = rs.getString("algorithm");
				int evaluations = rs.getInt("evaluations");
				int runs = rs.getInt("runs");
				int objectives = rs.getInt("objectives");
				tasks_id = rs.getInt("tasks_id");
				parameter = new Parameter(parameter_id, algorithm, evaluations,runs, objectives, tasks_id);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
		return parameter;
		
		
	}
	
	public SingleObjectiveResults getSingleObjectiveResults(int parameters_tasks_id) throws Exception{
		
		SingleObjectiveResults sor = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			
			conn = openConnection();
			stmt = conn.prepareStatement("select * from single_objective_results where parameters_tasks_id=?");
			stmt.setInt(1, parameters_tasks_id);

			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				int result_id = rs.getInt("result_id");
				String finalbindingenergy = rs.getString("finalbindingenergy");
				String objective = rs.getString("objective");
				int runs = rs.getInt("run");
				int parameters_parameter_id = rs.getInt("parameters_parameter_id");
				parameters_tasks_id = rs.getInt("parameters_tasks_id");
				sor = new SingleObjectiveResults(result_id, finalbindingenergy, objective, runs, parameters_parameter_id, parameters_tasks_id);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
		
		return sor;	
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
		Task task = ds.getTask(id);
		//System.out.println("id: " + task.getId() + " Hash " + task.getHash());
		Parameter parameter = ds.getParameter(id);
		//System.out.println("task_id: " + parameter.getTasks_id() + " algorithm: " + parameter.getAlgorithm() 
			//	+ " evaluations: " + parameter.getEvaluations() + " objectives: " 
			//	+ parameter.getObjectives() + " runs: " + parameter.getRuns());

		
		
		
	}
	
	

}
