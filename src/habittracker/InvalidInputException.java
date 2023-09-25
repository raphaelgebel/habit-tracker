package habittracker;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidInputException() {
		super(Color.RED.colorCode() + "[Error] " + Color.reset() + "Invalid input. Try again.\n");
	}

}
