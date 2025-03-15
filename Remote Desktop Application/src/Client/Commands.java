package Client;

// Enum to define various commands for remote desktop control
public enum Commands {
	// Command to press the mouse button
	PRESS_MOUSE(-1),
	// Command to release the mouse button
	RELEASE_MOUSE(-2),
	// Command to press a key on the keyboard
	PRESS_KEY(-3),
	// Command to release a key on the keyboard
	RELEASE_KEY(-4),
	// Command to move the mouse cursor
	MOVE_MOUSE(-5);

	// Variable to hold the abbreviation of the command
	private int abbreviation;

	// Constructor to initialize the command with its abbreviation
	Commands(int abbreviation) {
		this.abbreviation = abbreviation;
	}

	// Method to get the abbreviation of the command
	public int getAbbrev() {
		return abbreviation;
	}
}
