package cmdEmulationApp.exceptions;

public class UnvalidPathException extends Exception {
	private String commandPath;

	public UnvalidPathException(String commandPath) {
		this.commandPath = commandPath;
	}

	public String toString() {
		return commandPath + " : unsupported format of path";
	}
}
