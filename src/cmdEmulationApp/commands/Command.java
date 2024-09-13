package cmdEmulationApp.commands;

public class Command {
	private String inputCommand;
	private String commandType;
	private String commandOption;

	public Command(String inputCommand, String commandType, String commandOption) {
		this.inputCommand = inputCommand;
		this.commandType = commandType;
		this.commandOption = commandOption;
	}

	public String getCommand() {
		return this.inputCommand;
	}

	public String getCommandType() {
		return this.commandType;
	}

	public String getCommandOption() {
		return this.commandOption ;
	}
}
