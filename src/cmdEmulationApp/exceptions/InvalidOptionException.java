package cmdEmulationApp.exceptions;

public class InvalidOptionException extends Exception {
	private String commandType;
	private String commandOption;

	public InvalidOptionException(String commandType, String commandOption) {
		this.commandType = commandType;
		this.commandOption = commandOption;
	}

	public String toString() {
		return this.commandType + ": invalid option  '" + this.commandOption + "'";
	}
}
