package es.uma.khaos.docking_service.exception;

/**
 * Created by estebanpua on 08/01/14.
 */
public class DlgParseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DlgParseException() { super(); }
    public DlgParseException(String message) { super(message); }
    public DlgParseException(String message, Throwable cause) { super(message, cause); }
    public DlgParseException(Throwable cause) { super(cause); }

}
