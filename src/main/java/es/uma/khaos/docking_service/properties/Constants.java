package es.uma.khaos.docking_service.properties;

public class Constants {
	
	public static final String PATH_DOCKING_SERVICE_PROPS = "docking_service.properties";
	
	// MYSQL database
	public static final String MYSQL_IP = Configurations.getProperty(Properties.MYSQL_IP);
	public static final String MYSQL_PORT = Configurations.getProperty(Properties.MYSQL_PORT);
	public static final String MYSQL_SCHEMA = Configurations.getProperty(Properties.MYSQL_SCHEMA);
	public static final String MYSQL_USER = Configurations.getProperty(Properties.MYSQL_USER);
	public static final String MYSQL_PASS = Configurations.getProperty(Properties.MYSQL_PASS);
	
	// Directories
	public static final String DIR_AUTODOCK = Configurations.getProperty(Properties.DIR_AUTODOCK);
	public static final String DIR_BASE = Configurations.getProperty(Properties.DIR_BASE);
	
	// Files
	public static final String FILE_AUTODOCK = Configurations.getProperty(Properties.FILE_AUTODOCK);
	
	// Test
	public static final String TEST_DIR_INSTANCE = Configurations.getProperty(Properties.TEST_DIR_INSTANCE);
	public static final String TEST_FILE_DPF = Configurations.getProperty(Properties.TEST_FILE_DPF);

}
