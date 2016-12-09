package es.uma.khaos.docking_service.exception;

public class DlgNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DlgNotFoundException(String message) {
		super(message);
	}
	
	public DlgNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public DlgNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DlgNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
