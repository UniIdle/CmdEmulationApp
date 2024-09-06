package cmdEmulationApp;

import java.util.Scanner;
import cmdEmulationApp.commands.Command;


public class Core {
	private boolean coreTrigger;
	private Scanner scan = new Scanner(System.in);
	

	void launchCore() {
		coreTrigger = true;

		helloInfo();

		while(coreTrigger) {
			System.out.print("\u001B[32;1m" + "user@DESKTOP-GU0OIRP" + "\u001B[0m" + ":" + "\u001B[34;1m" + "~" + "\u001B[0m" + "$ ");
			Command inputCommand = new Command(scan.nextLine().toLowerCase());
			commandHandler(inputCommand.getCommandType());
		}
	}

	private void helloInfo() {
		System.out.println("Welcome to CmdEmulatorApp!");
		System.out.println("To finish, enter \"exit\"");
	}

	private void commandHandler(String commandType) {
		switch (commandType) {
			case "help":
				System.out.println("help");
				helpInformation();
				break;
			case "exit":
				stopCore();
				break;
			case "ls":
				System.out.println("ls");
				break;
			case "cat":
				System.out.println("cat");
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
		scan.close();

		return coreTrigger;
	}
}
