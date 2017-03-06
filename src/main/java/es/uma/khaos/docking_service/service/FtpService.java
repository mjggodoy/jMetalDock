package es.uma.khaos.docking_service.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import es.uma.khaos.docking_service.exception.FtpException;
import es.uma.khaos.docking_service.properties.Constants;

public class FtpService {
	
	private static FtpService instance;
	
	private final String FTP_IP;
	private final int FTP_PORT;
	private final String FTP_USER;
	private final String FTP_PASSWORD;
	private final String FTP_FOLDER_INSTANCES;
	
	private FtpService() {
		FTP_IP = Constants.FTP_IP;
		FTP_PORT = Integer.valueOf(Constants.FTP_PORT);
		FTP_USER = Constants.FTP_USER;
		FTP_PASSWORD = Constants.FTP_PASS;
		FTP_FOLDER_INSTANCES = Constants.FTP_FOLDER_INSTANCES;
	}
	
	public static synchronized FtpService getInstance()  {
		if(instance == null) {
			instance = new FtpService();
		}
		return instance;
	}
	
	public void download(String fileName, String destinationFile) throws FtpException {
		
		// get an ftpClient object  
		FTPClient ftpClient = new FTPClient();
		
		try {
			
			// pass directory path on server to connect
			ftpClient.connect(FTP_IP, FTP_PORT);
			
			// pass username and password, returned true if authentication is
			// successful
			boolean login = ftpClient.login(FTP_USER, FTP_PASSWORD);
			
			if (login) {
				ftpClient.changeWorkingDirectory(FTP_FOLDER_INSTANCES);
				
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				
				FileOutputStream fos = new FileOutputStream(new File(destinationFile));
				
				boolean download = ftpClient.retrieveFile(fileName, fos);
				
				if (!download) {
					throw new FtpException();
				}
				
				fos.flush();
				fos.close();
				
				// logout the user, returned true if logout successfully
				ftpClient.logout();
			}
		} catch (IOException e) {
			throw new FtpException(e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
