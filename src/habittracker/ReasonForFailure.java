package habittracker;

import java.io.Serializable;
import java.util.InputMismatchException;

public enum ReasonForFailure implements Serializable {

	GOT_DISTRACTED("Got distracted"), FORGOT("Forgot"), LACK_OF_MOTIVATION("Lack of motivation"),
	LACK_OF_TIME("Lack of time"), PROCRASTINATED("Procrastinated"), TOO_TIRED("Too tired");

	private String name;

	private ReasonForFailure(String name) {
		this.name = name;
	}

	// Logic
	
	/**
	 * Lets the user choose from a selection of reasons why he did not perform the
	 * habit.
	 * 
	 * @return ReasonForFailure-Enum that represents the chosen reason.
	 */
	public static ReasonForFailure chooseReasonForFailure() {
		ReasonForFailure reasonForFailure = null;
		int choice = 0;
		boolean validInput = false;
		do {
			// Letting the user make a selection
			try {
				System.out.println(Color.CYAN.colorCode() + "[Choose a reason for failure]" + Color.reset());
				System.out.println(" - (1) Got distracted");
				System.out.println(" - (2) Forgot");
				System.out.println(" - (3) Lack of motivation");
				System.out.println(" - (4) Lack of time");
				System.out.println(" - (5) Procrastinated");
				System.out.println(" - (6) Too tired");
				choice = Main.scanner.nextInt();
				Main.scanner.nextLine();
				validInput = true;
			} catch (InputMismatchException e) {
				Main.scanner.nextLine();
				System.out.println(new InvalidInputException().getMessage());
				validInput = false;
				continue;
			}

			// Assignment of the user selection to the given reasons of failure
			switch (choice) {
			case 1:
				validInput = true;
				reasonForFailure = GOT_DISTRACTED;
				break;
			case 2:
				validInput = true;
				reasonForFailure = FORGOT;
				break;
			case 3:
				validInput = true;
				reasonForFailure = LACK_OF_MOTIVATION;
				break;
			case 4:
				validInput = true;
				reasonForFailure = LACK_OF_TIME;
				break;
			case 5:
				validInput = true;
				reasonForFailure = PROCRASTINATED;
				break;
			case 6:
				validInput = true;
				reasonForFailure = TOO_TIRED;
				break;
			default:
				validInput = false;
				System.out.println(new InvalidInputException().getMessage());
				break;
			}
		} while (!validInput);

		return reasonForFailure;
	}

	// Getters / Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
