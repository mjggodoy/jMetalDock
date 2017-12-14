package es.uma.khaos.docking_service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Instance;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Solution;
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

	public Task insertTask(String hash, String email) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Task task = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement(
					"insert into task (hash, email ) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, hash);
			stmt.setString(2, email);
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				
				task = new Task(rs.getInt(1), hash, "sent", email);

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
				task = new Task(rs.getInt("id"), rs.getString("hash"), rs.getString("state"), rs.getString("email"));
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
				task = new Task(rs.getInt("id"), rs.getString("hash"), rs.getString("state"), p, rs.getString("email"));

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
	
	public ParameterSet insertParameter(ParameterSet parameters) throws Exception { 

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String statement = "insert into parameters_set (algorithm, evaluations, population_size, runs, objective, task_id)"
				+ " values (?, ?, ?, ?, ?, ?)"; 

		try {
			conn = openConnection();
			stmt = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, parameters.getAlgorithm());
			stmt.setInt(2, parameters.getEvaluations());
			stmt.setInt(3, parameters.getPopulationSize());
			stmt.setInt(4, parameters.getRuns());
			stmt.setInt(5, parameters.getObjectiveOption());
			stmt.setInt(6, parameters.getTaskId());
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				parameters.setId(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return parameters;
	}
	
	/*
	 * RESULT
	 */
	
	public List<Result> getResults(int taskId)  throws DatabaseException {

		List<Result> results = new ArrayList<Result>();
		Result result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from result where task_id=?");
			stmt.setInt(1, taskId);

			rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				taskId = rs.getInt("task_id");
				int run = rs.getInt("run");
				result = new Result(id, taskId, run);
				results.add(result);	
			}

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
	
		return results;
	}
		
	public Result getResult(int taskId, int run) throws DatabaseException {

		Result result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from result where task_id=? and run=?");
			stmt.setInt(1, taskId);
			stmt.setInt(2, run);

			rs = stmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				taskId = rs.getInt("task_id");
				result = new Result(id, taskId, run);
			}

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
	
	public Result insertResult(int taskId, int run) throws DatabaseException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Result result = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("insert into result (task_id, run) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, taskId);
			stmt.setInt(2, run);
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				result = new Result(rs.getInt(1), taskId, run);
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
	
	/*
	 * SOLUTION
	 */
	
	private Solution getSolution(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		double finalBindingEnergy = rs.getDouble("final_binding_energy");
		String objective1 = rs.getString("objective1");
		String objective2 = rs.getString("objective2");
		List <String> objectives = new ArrayList<String>();
		objectives.add(objective1);
		objectives.add(objective2);
		double intermolecularEnergy = rs.getDouble("intermolecular_energy");
		double intramolecularEnergy = rs.getDouble("intramolecular_energy");
		Double rmsd = rs.getDouble("rmsd");
		if (rs.wasNull()) rmsd = null;
		int resultId = rs.getInt("result_id");
		return new Solution(id,finalBindingEnergy, objectives, intermolecularEnergy, intramolecularEnergy, rmsd, resultId);
	}
	
	public List<Solution> getSolutionsFromResult(int resultId) throws DatabaseException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Solution> solutions = new ArrayList<Solution>();
		
		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from solution where result_id=?");
			stmt.setInt(1, resultId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				solutions.add(getSolution(rs));
			}

		} catch (Exception e) {
			throw new  DatabaseException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}
	
		return solutions;
	}
	
	public Solution getSolution(int id) throws Exception{
		
		Solution solution = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from solution where id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				solution = getSolution(rs);
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
	
		return solution;
		
	}
	
	public Solution getResultByTaskIdAndRun(int taskId, int run) throws Exception{

		Solution solution = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = openConnection();
			stmt = conn
					.prepareStatement("select * from result a, solution b where task_id = ? and run = ? and a.id = b.result_id;");
			stmt.setInt(1, taskId);
			stmt.setInt(2, run);
			rs = stmt.executeQuery();

			while (rs.next()) {
				solution = getSolution(rs);
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
	
		return solution;
	}

	public Solution insertSolution(double finalBindingEnergy, String objective1, String objective2, double intermolecularEnergy, double intramolecularEnergy, Double rmsd, int resultId) throws DatabaseException {

		Connection conn = null;
		PreparedStatement stmt = null;
		Solution solution = null;
		ResultSet rs = null;
		ArrayList<String> objectives = new ArrayList<String>();
		objectives.add(objective1);
		objectives.add(objective2);

		try {
			
			conn = openConnection();
			stmt = conn.prepareStatement("insert into solution (final_binding_energy, objective1, objective2, intermolecular_energy, intramolecular_energy, rmsd, result_id) "
					+ "values (?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, finalBindingEnergy);
			stmt.setString(2, objective1);
			stmt.setString(3, objective2);
			stmt.setDouble(4, intermolecularEnergy);
			stmt.setDouble(5, intramolecularEnergy);
			if (rmsd == null) stmt.setNull(6, Types.FLOAT);
			else stmt.setDouble(6, rmsd);
			stmt.setInt(7, resultId);
			stmt.execute();
			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				solution = new Solution(rs.getInt(1), finalBindingEnergy, objectives, intermolecularEnergy, intramolecularEnergy, rmsd, resultId);
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
		return solution;
	}
	
	/*
	 * INSTANCE
	 */
	
	public Instance getInstance(String name) throws DatabaseException {

		Instance instance = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = openConnection();
			stmt = conn.prepareStatement("select * from instance where name=?");
			stmt.setString(1, name);

			rs = stmt.executeQuery();

			if (rs.next()) {
				instance = new Instance(rs.getInt("id"), rs.getString("name"), rs.getString("filename"));
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

		return instance;
	}
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Testing...");
		
		DatabaseService ds = new DatabaseService();
		ds.insertTask("94ovljntrpg6n8s1ef8lrmqtmu", "mjgarciag//2");
		
	}
	
}
