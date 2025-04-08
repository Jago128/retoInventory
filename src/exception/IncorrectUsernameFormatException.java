package exception;

public class IncorrectUsernameFormatException  extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectUsernameFormatException() {
		super("Incorrect username format.");
	}
}