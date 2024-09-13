package cmdEmulationApp.commands;

import java.util.regex.*;
import java.io.File;
import cmdEmulationApp.exceptions.*;
import cmdEmulationApp.utils.PathValidator;
import java.util.Arrays;

public class CommandCat extends Command {
	final private String[] LS_COMMAND_OPTIONS_LIST = {"b", "E", "n"};
	private String commandPath = System.getProperty("user.dir");
	private String commandOption = "";

	public CommandCat(String inputCommand, String commandType) {
		super(inputCommand, commandType);
	}

	public void commandExecutor() {
		try {
			this.commandValidator();
			this.commandParser();
			PathValidator.pathValidator(this.commandPath);
			try {
				this.commandHandler();
			} catch (NullPointerException error) {
				System.out.println(this.getCommandType() + ": cannot access '" + this.commandPath + "': No such file or directory");
			}
		} catch (UnvalidCommandException error) {
			System.out.println(error);
		} catch (UnvalidPathException error) {
			System.out.println(error);
		}
	}

	private void commandParser() {
		String[] commandAsList = this.getCommand().trim().split(" +");
		Pattern pat = Pattern.compile("-[" + String.join("", LS_COMMAND_OPTIONS_LIST) + "]");
		Matcher mat = pat.matcher(commandAsList[1]);

		if (Arrays.asList(commandAsList).contains(">")) {
			System.out.println("Have \">\"");
		} else {
			if (commandAsList.length == 2) {
				if (mat.matches()) {
					this.commandOption = commandAsList[1];
				} 
			} else if (commandAsList.length > 2) {

	
		
			}
		}
	}

	private void commandValidator() throws UnvalidCommandException {
		Pattern pat = Pattern.compile("[Cc][Aa][Tt] *(-[" + String.join("", LS_COMMAND_OPTIONS_LIST) + "])? *(- *|> *|[^ ]* *)*");
		Matcher mat = pat.matcher(getCommand());

		if (!mat.matches()) {
			throw new UnvalidCommandException(this.getCommand(), this.getCommandType());
		};
	}

	private void commandHandler() {
		File folder = new File(this.commandPath);
		File[] files = folder.listFiles();

		switch (this.commandOption) {
			case "-a":
				for (File file : files) {
					if (file.isFile()) {
						System.out.print("\u001B[37;1m" + file.getName() + "\u001B[0m" + " ");
					} else {
						System.out.print("\u001B[34;1m" + file.getName() + "\u001B[0m" + " ");
					}
				}
				System.out.print("\n");
				break;
			case "-Q":
				for (File file : files) {
					String element = file.getName();
					if (!(element.substring(0, 1).equals("."))) {
						if (file.isFile()) {
							System.out.print("\u001B[37;1m" + "\"" + element + "\u001B[0m" + "\"" + " ");
						} else {
							System.out.print("\u001B[34;1m" + "\"" + element + "\"" + "\u001B[0m" + " ");
						}
					}
				}
				System.out.print("\n");
				break;
			case "-r":
				for (int i = files.length - 1; i >= 0 ; i--) {
					String element = files[i].getName();
					if (!(element.substring(0, 1).equals("."))) {
						if (files[i].isFile()) {
							System.out.print("\u001B[37;1m" + element + "\u001B[0m" + " ");
						} else {
							System.out.print("\u001B[34;1m" + element + "\u001B[0m" + " ");
						}
					}
				}
				System.out.print("\n");
				break;
			default:
				for (File file : files) {
					String element = file.getName();
					if (!(element.substring(0, 1).equals("."))) {
						if (file.isFile()) {
							System.out.print("\u001B[37;1m" + element + "\u001B[0m" + " ");
						} else {
							System.out.print("\u001B[34;1m" + element + "\u001B[0m" + " ");
						}
					}
				}
				System.out.print("\n");
				break;
		}
	}

}
