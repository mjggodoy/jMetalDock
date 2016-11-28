package es.uma.khaos.docking_service.exception;

public class LaunchingTaskException extends Exception {
	
	private static final long serialVersionUID = 1997753363232807009L;
	
	public LaunchingTaskException() {	}
	
	public LaunchingTaskException(String message) {
		super(message);
	}
	
	public LaunchingTaskException(Throwable cause) {
		super(cause);
	}
	
	public LaunchingTaskException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LaunchingTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
