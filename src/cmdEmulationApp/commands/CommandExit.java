package cmdEmulationApp.commands;

import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.constants.Options;
import cmdEmulationApp.models.CommandDataObject;

public class CommandExit extends AbstractCommand {
	public CommandExit() {
		super.supportedOptions = Options.getExitCommandSupportOptions();
	}

	@Override
	public StringBuilder executeCommand(CommandDataObject commandContainer) {
		StringBuilder resultOfCommand;
		resultOfCommand = super.executeCommand(commandContainer);
		return resultOfCommand.append("Shutting down...");//System.out.println("Shutting down...");
	}
}
