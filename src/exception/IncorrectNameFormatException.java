package exception;

public class IncorrectNameFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectNameFormatException() {
		super("Incorrect Name format.");
	}
}
