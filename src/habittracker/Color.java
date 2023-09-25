package habittracker;

public enum Color {

	RED("\u001B[31m"), GREEN("\u001B[32m"), CYAN("\u001B[36m");

	private String colorCode;

	private Color(String colorCode) {
		this.colorCode = colorCode;
	}

	// Logic
	
	/**
	 * @return ANSI color code (as String) for the corresponding color
	 */
	public String colorCode() {
		return colorCode;
	}

	/**
	 * Resets the color of the text in the console.
	 * 
	 * @return ANSI color code (as String) for resetting the color of the text in
	 *         the console.
	 */
	public static String reset() {
		return "\u001B[0m";
	}

}
