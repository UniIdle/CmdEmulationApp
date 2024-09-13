package cmdEmulationApp;

import java.util.Scanner;
import cmdEmulationApp.commands.*;

import java.util.HashMap;
import java.util.NoSuchElementException;
import cmdEmulationApp.exceptions.UnvalidCommandException;
import cmdEmulationApp.utils.Parser;
import cmdEmulationApp.utils.Validator;


public class Core {
	private boolean coreTrigger;
	private Scanner scan = new Scanner(System.in);
	

	void launchCore() {
		coreTrigger = true;

		helloInfo();

		while(coreTrigger) {
			System.out.print("\u001B[32;1m" + "user@DESKTOP-GU0OIRP" + "\u001B[0m" + ":" + "\u001B[34;1m" + "~" + "\u001B[0m" + "$ ");
			
			try {
				String inputCommand = scan.nextLine().trim();

				if (inputCommand.equals("")) {
					continue;
				}

				Validator.commandValidator(inputCommand);
				HashMap<String, String> commandStructure = Parser.commandQualifier(inputCommand);
				commandHandler(
					inputCommand,
					commandStructure.get("commandType"),
					commandStructure.get("commandOption"),
					commandStructure.get("commandProperties")
				);
			} catch (NoSuchElementException error) {
				this.stopCore();
			} catch (UnvalidCommandException error) {
				System.out.println(error);
			}
			
		}
	}

	private void helloInfo() {
		System.out.println("Welcome to CmdEmulatorApp!");
		System.out.println("To finish, enter \"exit\"");
	}

	private void commandHandler(String inputCommand, String commandType, String commandOption, String commandProperties) {
		switch (commandType) {
			case "help":
				System.out.println("help");
				helpInformation();
				break;
			case "exit":
				stopCore();
				break;
			case "ls":
				CommandLs ls = new CommandLs(inputCommand, commandType, commandOption, commandProperties);
				ls.commandExecutor();
				break;
			case "cat":
				CommandCat cat = new CommandCat(commandType, inputCommand);
				cat.commandExecutor();
				break;
			default:
				System.out.printf("%s: command not found\n", commandType);
				break;
		}
	}

	private void helpInformation() {

		System.out.println("Access comands:");
		System.out.println("ls - this command show all files and directories");
	}

	private boolean stopCore() {
		coreTrigger = false;
		System.out.print("\n");
		scan.close();

		return coreTrigger;
	}
}
