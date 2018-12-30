package exception;

public class EcheancierException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public EcheancierException() {
		super();
	}
	
	public EcheancierException(String message) {
		super(message);
	}
}
