package es.uma.khaos.docking_service.exception;

public class DpfNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1997753363232807009L;
	
	public DpfNotFoundException() {	}
	
	public DpfNotFoundException(String message) {
		super(message);
	}

	public DpfNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public DpfNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DpfNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
