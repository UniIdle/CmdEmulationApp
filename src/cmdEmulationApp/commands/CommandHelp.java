package cmdEmulationApp.commands;

import java.util.HashMap;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.utils.Validator;

/**
 * Класс реализующий функционал команды "help"
 */
public class CommandHelp extends AbstractCommand {
	private HashMap<String, AbstractCommand> MapOfCommandObjects;
	private final String[] HELP_COMMAND_OPTIONS_LIST = {" "};

	public CommandHelp(HashMap<String, AbstractCommand> MapOfObjects) {
		this.MapOfCommandObjects = MapOfObjects;
	}

	public void executeCommand() {
		try {
			Validator.validateCommandOptions(
				super.getCommandType(),
				super.getCommandOption(),
				HELP_COMMAND_OPTIONS_LIST
			);

			if (super.getCommandProperties().equals("")) {
				this.showHelpInformation();
			} else {
				this.MapOfCommandObjects.get(super.getCommandProperties()).showHelpInformation();
			}
			
		} catch (NullPointerException error) {
			System.out.printf("bash: help: no help topic match `%s'.%n", super.getCommandType());
		} catch (InvalidOptionException error) {
			System.out.println(error);
		}
	}

	public void processCommandOption() {

	}

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
