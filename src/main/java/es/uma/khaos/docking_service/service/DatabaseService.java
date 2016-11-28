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
	
	public static void main(String[] args) throws Exception {
		DatabaseService.getInstance().insertTask("SSH");
		DatabaseService.getInstance().shutdown();
	}

}
