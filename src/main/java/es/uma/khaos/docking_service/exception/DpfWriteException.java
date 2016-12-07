package es.uma.khaos.docking_service.exception;

public class DpfWriteException extends Exception {
	
	private static final long serialVersionUID = 1997753363232807009L;
	
	public DpfWriteException() {	}
	
	public DpfWriteException(String message) {
		super(message);
	}
	
	public DpfWriteException(Throwable cause) {
		super(cause);
	}
	
	public DpfWriteException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DpfWriteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
