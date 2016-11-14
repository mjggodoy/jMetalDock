package es.uma.khaos.docking_service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Task;

public final class DatabaseService {
	
	private static final String PROPERTIES_FILE = "docking_service.properties";
	
	private static final String RUNNING_STATE = "running";
	private static final String FINISHED_STATE = "finished"; 
	
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
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE));
			String dbUrl = props.getProperty("url");
			String dbUser = props.getProperty("user");
			String dbPassword = props.getProperty("password");
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
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
	
	public void startTask(int id) throws Exception {
		this.updateTaskState(id, RUNNING_STATE);
	}
	
	public void finishTask(int id) throws Exception {
		this.updateTaskState(id, FINISHED_STATE);
	}

}
