package cmdEmulationApp.exceptions;

public class UnvalidCommandException extends Exception {
	private String command;

	public UnvalidCommandException(String command) {
		this.command = command;
	}

	public String toString() {
		return command + " : unvalid format of command";
	}
}
