package cmdEmulationApp.commands;

import java.io.File;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.utils.Validator;
import cmdEmulationApp.abstracts.AbstractCommand;

/**
 * Класс реализующий функционал команды "ls"
 */
public class CommandLs extends AbstractCommand {
	final private String[] LS_COMMAND_OPTIONS_LIST = {"a", "Q", "r"};

	public void setCommandProperties(String commandProperties) {
		if (commandProperties.equals("")) {
			super.setCommandProperties(System.getProperty("user.dir"));
		} else {
			super.setCommandProperties(commandProperties);
		}
	}

	public void executeCommand() {
		try {
			Validator.validateCommandOptions(
				super.getCommandType(),
				super.getCommandOption(),
				LS_COMMAND_OPTIONS_LIST
			);

			this.processCommandProperties();
			
		}  catch (InvalidOptionException error) {
			System.out.println(error);
		}
	}

	public void processCommandOption() {
	}

	private void processCommandOption(File[] currentDirectoryFiles) {

		switch (super.getCommandOption()) {
			case "-a":
				for (File file : currentDirectoryFiles) {
					if (file.isFile()) {
						System.out.print("\u001B[37;1m" + file.getName() + "\u001B[0m" + " ");
					} else {
						System.out.print("\u001B[34;1m" + file.getName() + "\u001B[0m" + " ");
					}
				}
				System.out.print("\n");
				break;
			case "-Q":
				for (File file : currentDirectoryFiles) {
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
				for (int i = currentDirectoryFiles.length - 1; i >= 0 ; i--) {
					String element = currentDirectoryFiles[i].getName();
					if (!(element.substring(0, 1).equals("."))) {
						if (currentDirectoryFiles[i].isFile()) {
							System.out.print("\u001B[37;1m" + element + "\u001B[0m" + " ");
						} else {
							System.out.print("\u001B[34;1m" + element + "\u001B[0m" + " ");
						}
					}
				}
				System.out.print("\n");
				break;
			default:
				for (File file : currentDirectoryFiles) {
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

	public void showHelpInformation() {
		System.out.println("ls: ls [-aQr] [args...]");
		System.out.println("\tThe ls command lists the files in your current directory (ls is short for \"list\"). Try it now by typing ls, then hitting <enter>.");
		System.out.println();
		System.out.println("\tOptions:");
		System.out.println("\t\t-a\t\tshow hidden files");
		System.out.println("\t\t-Q\t\tputs file and directories names in quotes");
		System.out.println("\t\t-r\t\tshow files in reverse order");
	}

	/**
	 * Метод выполняющий обработку дополнительных свойств команды
	 */
	private void processCommandProperties() {
		String[] pathesList = super.getCommandProperties().split(" +");

		for (String path : pathesList) {
			try {
				File folder = new File(path);
				File[] files = folder.listFiles();
	
				if (pathesList.length > 1 && files != null) System.out.println(path + ":");
				this.processCommandOption(files);
				if (pathesList.length > 1 && files != null) System.out.println();

			} catch (NullPointerException error) {
				System.out.println(this.getCommandType() + ": cannot access '" + path + "': No such file or directory");
			}
		}
	}

}
