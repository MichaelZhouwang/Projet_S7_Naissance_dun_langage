package exception;

/**
 * Exception levee lors de la lecture de la configuration du systeme
 * 
 * @author Charles MECHERIKI & Yongda LIN
 *
 */
public class ConfigurationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ConfigurationException() {
		super();
	}
	
	public ConfigurationException(Throwable cause) {
		super(cause);
	}
	
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
