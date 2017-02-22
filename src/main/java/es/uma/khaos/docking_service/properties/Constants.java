package es.uma.khaos.docking_service.properties;

import java.util.List;

public class Constants {
	
	public static final String PATH_DOCKING_SERVICE_PROPS = "docking_service.properties";
	
	// MYSQL database
	public static final String MYSQL_IP = Configurations.getProperty(Properties.MYSQL_IP);
	public static final String MYSQL_PORT = Configurations.getProperty(Properties.MYSQL_PORT);
	public static final String MYSQL_SCHEMA = Configurations.getProperty(Properties.MYSQL_SCHEMA);
	public static final String MYSQL_USER = Configurations.getProperty(Properties.MYSQL_USER);
	public static final String MYSQL_PASS = Configurations.getProperty(Properties.MYSQL_PASS);
	
	// SSH Connection
	public static final String SSH_HOST = Configurations.getProperty(Properties.SSH_HOST);
	public static final String SSH_PORT = Configurations.getProperty(Properties.SSH_PORT);
	public static final String SSH_USER = Configurations.getProperty(Properties.SSH_USER);
	public static final String SSH_PASS = Configurations.getProperty(Properties.SSH_PASS);
	public static final String SSH_REDIRECTION_PORT = Configurations.getProperty(Properties.SSH_REDIRECTION_PORT);
	
	// Directories
	public static final String DIR_AUTODOCK = Configurations.getProperty(Properties.DIR_AUTODOCK);
	public static final String DIR_BASE = Configurations.getProperty(Properties.DIR_BASE);
	
	// Files
	public static final String FILE_AUTODOCK = Configurations.getProperty(Properties.FILE_AUTODOCK);
	
	// Test
	public static final String TEST_DIR_INSTANCE = Configurations.getProperty(Properties.TEST_DIR_INSTANCE);
	public static final String TEST_FILE_DPF = Configurations.getProperty(Properties.TEST_FILE_DPF);
	public static final String TEST_FILE_ZIP = Configurations.getProperty(Properties.TEST_FILE_ZIP);
	
	// Messages
	public static final String RESPONSE_TASK_MSG_NOT_ID = "Task id is a mandatory parameter";
	public static final String RESPONSE_TASK_MSG_ID_NOT_NUMBER = "Task id should be a valid number";
	public static final String RESPONSE_TASK_MSG_NOT_TOKEN = "Token is a mandatory parameter";
	public static final String RESPONSE_TASK_MSG_UNALLOWED = "You are not allowed to see this task";
	
	public static final String RESPONSE_ERROR_DATABASE = "There was an error with the database. Contact with the administrators.";
	public static final String RESPONSE_NOT_A_NUMBER_ERROR = "%s should be a valid number";
	public static final String RESPONSE_MANDATORY_PARAMETER_ERROR = "Mandatory parameter not provided: %s";
	public static final String RESPONSE_NOT_VALID_PARAMETER_ERROR = "Parameter with a non allowed value: %s. Check possible values.";
	public static final String RESPONSE_MIN_MAX_VALUES = "There was an error based on the parameters introduced. The minimum values for evaluations, runs and population sizes are 1500000, 10 and 100, respectively. The maximum values for evaluations, runs and population are 25000000, 50 and 500, respectively";

	// Algorithm values allowed
	public static final List<String> SINGLE_OBJECTIVE_ALGORITHMS = Configurations.getListProperties(Properties.SINGLE_OBJ_ALGORITHMS);
	public static final List<String> MULTI_OBJECTIVE_ALGORITHMS = Configurations.getListProperties(Properties.MULTI_OBJ_ALGORITHMS);
	
	//Input default parameters
	public static final int DEFAULT_NUMBER_EVALUATIONS = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_NUMBER_EVALUATIONS));
	public static final int DEFAULT_NUMBER_RUNS = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_NUMBER_RUNS));
	public static final int DEFAULT_NUMBER_POPULATION_SIZE = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_NUMBER_POPULATION_SIZE));

	//Input min parameters
	public static final int DEFAULT_MIN_NUMBER_EVALUATIONS = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_MIN_NUMBER_EVALUATIONS));
	public static final int DEFAULT_MIN_NUMBER_RUNS = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_MIN_NUMBER_RUNS));
	public static final int DEFAULT_MIN_NUMBER_POPULATION_SIZE = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_MIN_NUMBER_POPULATION_SIZE));
	
	//Input max parameters
	public static final int DEFAULT_MAX_NUMBER_EVALUATIONS = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_MAX_NUMBER_EVALUATIONS));
	public static final int DEFAULT_MAX_NUMBER_RUNS = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_MAX_NUMBER_RUNS));
	public static final int DEFAULT_MAX_NUMBER_POPULATION_SIZE = Integer.parseInt(Configurations.getProperty(Properties.DEFAULT_MAX_NUMBER_POPULATION_SIZE));
	
}
