package cmdEmulationApp.commands;

import java.util.List;
import cmdEmulationApp.Commands;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.exceptions.ExitCommandException;

public class CommandExit extends AbstractCommand {

	public CommandExit() {
		super.supportedOptions = Commands.EXIT.supportedOptions;
	}

	@Override
	public void executeCommand(String commandType, List<String> commandOptions, List<String> commandArgs) throws ExitCommandException {
		System.out.println("Shutting down...");
		throw new ExitCommandException();
	}

	@Override
	public void showHelpInformation() {
		System.out.println("exit: exit [n]");
		System.out.println("Exit the shell.");
		System.out.println();
		System.out.println("Exits the shell with a status of N. If N is omitted, the exit status");
		System.out.println("is that of the last command executed.");
	}
}
