package cmdEmulationApp;

import java.util.HashMap;
import cmdEmulationApp.abstracts.AbstractCommandLineCore;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.commands.CommandCat;
import cmdEmulationApp.commands.CommandLs;
import cmdEmulationApp.commands.CommandHelp;

/**
 * Класс-приложение, реализующий эмулятор командной строки Linux
 */
public class LinuxCommandLine extends AbstractCommandLineCore {
	private HashMap<String, AbstractCommand> mapOfCommandObjects = this.initializeMapOfCommandObjects();

	public void launchCommandLine() {
		showStartInformation();
		super.launchCommandLine();
	}

	public void processInputedCommand(String commandType, String commandOption, String commandProperties) {
		AbstractCommand commandObject = this.mapOfCommandObjects.get(commandType.toLowerCase());
		
		try {
			commandObject.setCommandType(commandType);
			commandObject.setCommandOption(commandOption);
			commandObject.setCommandProperties(commandProperties);

			commandObject.executeCommand();
		} catch (NullPointerException error) {
			System.out.printf("%s: command not found\n", commandType);
		}
	}

	public void showPrefix() {
		System.out.print("\u001B[32;1m" + "user@DESKTOP-GU0OIRP" + "\u001B[0m" + ":" + "\u001B[34;1m" + "~" + "\u001B[0m" + "$ ");
	}

		/**
	 * Метод отображающий стартовую информацию при запуске командной строки
	 */
	private void showStartInformation() {
		System.out.println("Welcome to CmdEmulatorApp!");
		System.out.println("To finish, enter \"exit\"");
	}

	/**
	 * Метод для инициализации связанного списка, объектами всех реализованных команд
	 * @return связанный список с объектами команд
	 */
	private HashMap<String, AbstractCommand> initializeMapOfCommandObjects() {
		HashMap<String, AbstractCommand> MapOfObjects = new HashMap<String, AbstractCommand>();
		
		MapOfObjects.put("cat", new CommandCat());
		MapOfObjects.put("ls", new CommandLs());
		MapOfObjects.put("help", new CommandHelp(MapOfObjects));
		
		return MapOfObjects;
	}

}
