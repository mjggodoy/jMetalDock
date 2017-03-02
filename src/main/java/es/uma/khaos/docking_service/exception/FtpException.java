package es.uma.khaos.docking_service.exception;

public class FtpException extends Exception {
	
	private static final long serialVersionUID = 1264384105984368498L;

	public FtpException() {	}
	
	public FtpException(String message) {
		super(message);
	}
	
	public FtpException(Throwable cause) {
		super(cause);
	}
	
	public FtpException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FtpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
