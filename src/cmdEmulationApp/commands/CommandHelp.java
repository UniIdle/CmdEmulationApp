package cmdEmulationApp.commands;

import cmdEmulationApp.exceptions.CommandNotFoundException;
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
				this.showCatHelpInformation();
				break;
			case "ls":
				this.showLsHelpInformation();
			break;
			default:
				this.showAllHelpInformation();
				break;
		}
	}

	private void showAllHelpInformation() {
		System.out.println("GNU bash, version 5.1.16(1)-release (x86_64-pc-linux-gnu)");
		System.out.println("These shell commands are defined internally.  Type `help' to see this list.");
		System.out.println("Type `help name' to find out more about the function `name'.");
		System.out.println();
		System.out.println("All access commands:");
		System.out.println();
		System.out.println("ls [-aQr] [args...]");
		System.out.println("cat [-bEn] [args...] [-] [>] [args...]");
	}

	private void showCatHelpInformation() {
		System.out.println("cat: cat [-bEn] [args...] [-] [>] [args...]");
		System.out.println("\tCat, which is short for concatenate, is one of the most commonly used commands in Linux and other Unix-like operating systems.");
		System.out.println("\tThe cat command allows us to create single or multiple files, view file inclusions, concatenate files and redirect output in a terminal or file.");
		System.out.println("\tIn this article, we will show you some handy uses of the cat command and examples of it in Linux.");
		System.out.println();
		System.out.println("\tOptions:");
		System.out.println("\t\t-b\t\tnumbers non-empty lines");
		System.out.println("\t\t-E\t\tadds $ sign to the end of the line");
		System.out.println("\t\t-n\t\tnumbers all lines");
	}

	private void showLsHelpInformation() {
		System.out.println("ls: ls [-aQr] [args...]");
		System.out.println("\tThe ls command lists the files in your current directory (ls is short for \"list\"). Try it now by typing ls, then hitting <enter>.");
		System.out.println();
		System.out.println("\tOptions:");
		System.out.println("\t\t-a\t\tshow hidden files");
		System.out.println("\t\t-Q\t\tputs file and directories names in quotes");
		System.out.println("\t\t-r\t\tshow files in reverse order");
	}

}
