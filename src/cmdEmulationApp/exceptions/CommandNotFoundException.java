package cmdEmulationApp.exceptions;

public class CommandNotFoundException extends Exception {
	private String commandType;

	public CommandNotFoundException(String commandType) {
		this.commandType = commandType;
	}

	public String toString() {
		return this.commandType + ": command not found";
	}
}
