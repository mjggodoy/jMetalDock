package es.uma.khaos.docking_service.exception;

public class CommandExecutionException extends Exception {
	
	private static final long serialVersionUID = 1997753363232807009L;
	
	public CommandExecutionException() {	}
	
	public CommandExecutionException(String message) {
		super(message);
	}
	
	public CommandExecutionException(Throwable cause) {
		super(cause);
	}
	
	public CommandExecutionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommandExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
