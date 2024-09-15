package cmdEmulationApp.commands;

import cmdEmulationApp.exceptions.CommandNotFoundException;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.utils.Validator;

public class CommandHelp extends Command {
	String commandProperty;
	private final String[] ACCESS_COMMANDS_FOR_INFORMATION_LIST = {"cat", "ls"};

	public CommandHelp(String inputCommand, String commandType, String commandOption, String commandProperties) {
		super(inputCommand, commandType, commandOption);
		this.commandProperty = commandProperty == null ? "" : commandProperty;
	}

	public void commandExecutor() {
		try {
			Validator.helpCommandValidator(
				this.commandProperty,
				ACCESS_COMMANDS_FOR_INFORMATION_LIST
			);

			this.commandHandler();
			
		}  catch (CommandNotFoundException error) {
			System.out.println(error);
		}
	}

	private void commandHandler() {
		switch (this.commandProperty) {
			case "cat":
				
				break;
			case "ls":
			
			break;
			default:
				this.showAllInformation();
				break;
		}
	}

	private void showAllInformation() {
		System.out.println("GNU bash, version 5.1.16(1)-release (x86_64-pc-linux-gnu)");
		System.out.println("These shell commands are defined internally.  Type `help' to see this list.");
		System.out.println("Type `help name' to find out more about the function `name'.");
		System.out.println();
		System.out.println("A star (*) next to a name means that the command is disabled.");
		System.out.println();
		System.out.println();

	}

}
