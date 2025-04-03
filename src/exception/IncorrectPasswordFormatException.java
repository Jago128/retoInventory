package exception;

public class IncorrectPasswordFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectPasswordFormatException() {
		super("Your password must contain at minimum one letter and one number, and it mist be at least 8 chacraters long.");
	}
}
