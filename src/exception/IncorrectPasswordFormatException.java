package exception;

public class IncorrectPasswordFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectPasswordFormatException() {
		super("The format of the password is invalid.");
	}
}
