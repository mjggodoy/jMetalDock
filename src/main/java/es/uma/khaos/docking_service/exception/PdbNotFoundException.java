package es.uma.khaos.docking_service.exception;

public class PdbNotFoundException extends Exception {
	
	
	private static final long serialVersionUID = 1997753363232807009L;
	
	public PdbNotFoundException() {	}


	public PdbNotFoundException(String message) {
		super(message);
	}
	
	public PdbNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public PdbNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PdbNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
