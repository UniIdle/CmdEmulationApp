package cmdEmulationApp.commands;

import java.io.File;
import cmdEmulationApp.exceptions.*;
import cmdEmulationApp.utils.Validator;

public class CommandLs extends Command {
	final private String[] LS_COMMAND_OPTIONS_LIST = {"a", "Q", "r"};
	private String commandPath;

	public CommandLs(String inputCommand, String commandType, String commandOption, String commandProperties) {
		super(inputCommand, commandType, commandOption);
		this.commandPath = commandProperties.equals("") ? System.getProperty("user.dir") : commandProperties;
	}

	public void commandExecutor() {
		try {
			Validator.optionValidator(super.getCommandType(), super.getCommandOption(), LS_COMMAND_OPTIONS_LIST);
			this.commandPathHandler();
		}  catch (InvalidOptionException error) {
			System.out.println(error);
		}
	}

	private void commandPathHandler() {
		String[] pathesList = this.commandPath.split(" +");

		for (String path : pathesList) {
			try {
				File folder = new File(path);
				File[] files = folder.listFiles();
	
				if (pathesList.length > 1 && files != null) System.out.println(path + ":");
				this.optionHandler(files);
				if (pathesList.length > 1 && files != null) System.out.println();

			} catch (NullPointerException error) {
				System.out.println(this.getCommandType() + ": cannot access '" + path + "': No such file or directory");
			}
		}

	}

	private void optionHandler(File[] currentDirectoryFiles) {

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

}
