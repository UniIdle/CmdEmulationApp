package cmdEmulationApp.commands;

import java.util.Map;
import cmdEmulationApp.abstracts.Command;
import cmdEmulationApp.constants.Commands;
import cmdEmulationApp.constants.Options;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.models.CommandDataObject;

/**
 * Класс реализующий функционал команды "help"
 */
public class CommandHelp extends AbstractCommand {
	private String allInformation = "GNU bash, version 5.1.16(1)-release (x86_64-pc-linux-gnu)\nThese shell commands are defined internally.  Type `help' to see this list.\nType `help name' to find out more about the function `name'.\n\n";
	private Map<Commands, Command> mapOfCommandObjects;

	public CommandHelp(Map<Commands, Command> mapOfCommandObjects) {
		supportedOptions = Options.getHelpCommandSupportOptions();
		this.mapOfCommandObjects = mapOfCommandObjects;
	}

	@Override
	public StringBuilder executeCommand(CommandDataObject commandContainer) {
		StringBuilder resultOfCommand = new StringBuilder();
		super.executeCommand(commandContainer);

		if (commandContainer.getCommandArgs().isEmpty()) {
			resultOfCommand.append(formAllHelpInformation());
		} else {
			for (String arg : commandContainer.getCommandArgs()) {
				try {
					Commands commandInfo = Commands.valueOf(arg.toUpperCase());

					resultOfCommand.append(mapOfCommandObjects.get(commandInfo).formHelpInformation(commandInfo));
				} catch (NullPointerException error) {
					System.out.printf("bash: help: no help topic match `%s'.%n", arg);
				}
			}
		}

		return resultOfCommand;

	}

	private StringBuilder formAllHelpInformation() {
		StringBuilder allInformation = new StringBuilder();
		allInformation.append(this.allInformation);
		allInformation.append(formAccessCommands());

		return allInformation;
	}

	private StringBuilder formAccessCommands() {
		StringBuilder accessCommand = new StringBuilder();
		accessCommand.append("\tAll access commands:\n\n");//System.out.print("\tAll access commands:\n\n");
		for (Commands command : Commands.values()) {
			accessCommand.append("\t");//System.out.print("\t" + command.schema);
			accessCommand.append(command.schema);//System.out.print("\t" + command.schema);
		}

		return accessCommand;
	}

}
