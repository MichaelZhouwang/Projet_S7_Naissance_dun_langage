package exception;

public class ConditionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ConditionException() {
		super();
	}
	
	public ConditionException(String message) {
		super(message);
	}
}
