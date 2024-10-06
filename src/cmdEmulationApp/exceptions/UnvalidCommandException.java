package cmdEmulationApp.exceptions;

public class UnvalidCommandException extends Exception {
	private String commandType;

	public UnvalidCommandException(String commandType) {
		this.commandType = commandType;
	}

	public String toString() {
		return commandType + " : unvalid format of command";
	}
}
