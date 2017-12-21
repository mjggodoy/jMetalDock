package es.uma.khaos.docking_service.properties;

public class Properties {

	// Website
	public static final String WEB_URL = "web.url";
	
	// MySQL database
	public static final String MYSQL_IP = "mysql.ip";
	public static final String MYSQL_PORT = "mysql.port";
	public static final String MYSQL_SCHEMA = "mysql.schema";
	public static final String MYSQL_USER = "mysql.user";
	public static final String MYSQL_PASS = "mysql.password";
	
	// FTP
	public static final String FTP_IP = "ftp.ip";
	public static final String FTP_PORT = "ftp.port";
	public static final String FTP_USER = "ftp.user";
	public static final String FTP_PASS = "ftp.password";
	public static final String FTP_FOLDER_INSTANCES = "ftp.folder.instances";
	
	// SSH connection
	public static final String SSH_HOST = "ssh.host";
	public static final String SSH_PORT = "ssh.port";
	public static final String SSH_USER = "ssh.user";
	public static final String SSH_PASS = "ssh.password";
	public static final String SSH_REDIRECTION_PORT = "ssh.redirection.port";
	
	// Directories
	public static final String DIR_AUTODOCK = "dir.autodock";
	public static final String DIR_BASE = "dir.base";
	
	// Files
	public static final String FILE_AUTODOCK = "file.autodock";
	
	// Test
	public static final String TEST_DIR_INSTANCE = "test.dir.instance";
	public static final String TEST_FILE_DPF = "test.file.dpf";
	public static final String TEST_FILE_ZIP = "test.file.zip";
	
	//Input possible algorithms
	public static final String SINGLE_OBJ_ALGORITHMS = "input.algorithm.single";
	public static final String MULTI_OBJ_ALGORITHMS = "input.algorithm.multi";
	
	//Input default parameters
	public static final String DEFAULT_NUMBER_EVALUATIONS = "input.default.evaluations";
	public static final String DEFAULT_NUMBER_RUNS = "input.default.runs";
	public static final String DEFAULT_NUMBER_POPULATION_SIZE = "input.default.population_size";
	
	// Input min parameters
	public static final String DEFAULT_MIN_NUMBER_EVALUATIONS = "input.default.min.evaluations";
	public static final String DEFAULT_MIN_NUMBER_RUNS = "input.default.min.runs";
	public static final String DEFAULT_MIN_NUMBER_POPULATION_SIZE = "input.default.min.population_size";
	
	//Input max parameters
	public static final String DEFAULT_MAX_NUMBER_EVALUATIONS = "input.default.max.evaluations";
	public static final String DEFAULT_MAX_NUMBER_RUNS = "input.default.max.runs";
	public static final String DEFAULT_MAX_NUMBER_POPULATION_SIZE = "input.default.max.population_size";

}
