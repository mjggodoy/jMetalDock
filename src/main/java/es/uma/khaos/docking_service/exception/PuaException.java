package es.uma.khaos.docking_service.exception;

/**
 * Created by esteban on 08/01/14.
 */
public class PuaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PuaException() { super(); }
    public PuaException(String message) { super(message); }
    public PuaException(String message, Throwable cause) { super(message, cause); }
    public PuaException(Throwable cause) { super(cause); }

}
