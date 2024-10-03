package cmdEmulationApp.commands;

import java.util.List;
import java.util.Map;
import cmdEmulationApp.abstracts.Command;
import cmdEmulationApp.Commands;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.exceptions.InvalidOptionException;

/**
 * Класс реализующий функционал команды "help"
 */
public class CommandHelp extends AbstractCommand {
	private Map<Commands, Command> mapOfCommandObjects;

	public CommandHelp(Map<Commands, Command> mapOfCommandObjects) {
		super.supportedOptions = Commands.HELP.supportedOptions;
		this.mapOfCommandObjects = mapOfCommandObjects;
	}

	@Override
	public void executeCommand(String commandType, List<String> commandOptions, List<String> commandArgs) {
		try {
			validateCommandOptions(commandType, commandOptions);

			if (commandArgs.isEmpty()) {
				this.showHelpInformation();
			} else {
				for (String arg : commandArgs) {
					try {
						mapOfCommandObjects.get(Commands.valueOf(arg.toUpperCase())).showHelpInformation();
					} catch (NullPointerException error) {
						System.out.printf("bash: help: no help topic match `%s'.%n", arg);
					}
				}
			}
		} catch (InvalidOptionException error) {
			System.out.println(error);
		}
	}

	@Override
	public void showHelpInformation() {
		System.out.println("GNU bash, version 5.1.16(1)-release (x86_64-pc-linux-gnu)");
		System.out.println("These shell commands are defined internally.  Type `help' to see this list.");
		System.out.println("Type `help name' to find out more about the function `name'.");
		System.out.println();
		System.out.println("All access commands:");
		System.out.println();
		System.out.println("ls [-aQr] [args...]");
		System.out.println("cat [-bEn] [args...] [-] [>] [args...]");
	}

}
