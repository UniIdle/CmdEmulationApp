package cmdEmulationApp;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import cmdEmulationApp.abstracts.AbstractCommandLineCore;
import cmdEmulationApp.abstracts.Command;
import cmdEmulationApp.commands.CommandCat;
import cmdEmulationApp.commands.CommandLs;
import cmdEmulationApp.commands.CommandHelp;
import cmdEmulationApp.commands.CommandExit;
import cmdEmulationApp.exceptions.ExitCommandException;

/**
 * Класс-приложение, реализующий эмулятор командной строки Linux
 */
public class LinuxCommandLine extends AbstractCommandLineCore {
	private Map<Commands, Command> mapOfCommandObjects = new HashMap<>();

	LinuxCommandLine() {
		initializeMapOfCommandObjects();
	}

	@Override
	public void launchCommandLine() {
		showStartInformation();
		super.launchCommandLine();
	}

	@Override
	public void processInputedCommand(String commandType, List<String> commandOption, List<String> commandProperties) {
		try {
			Command commandObject = this.mapOfCommandObjects.get(Commands.valueOf(commandType.toUpperCase()));
			
			commandObject.executeCommand(commandType, commandOption, commandProperties);
		} catch (IllegalArgumentException error) {
			System.out.printf("%s: command not found\n", commandType);
		} catch (ExitCommandException error) {
			super.stopCommandLine();
		}
	}

	@Override
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
	private void  initializeMapOfCommandObjects() {
		this.mapOfCommandObjects.put(Commands.CAT, new CommandCat());
		this.mapOfCommandObjects.put(Commands.LS, new CommandLs());
		this.mapOfCommandObjects.put(Commands.EXIT, new CommandExit());
		this.mapOfCommandObjects.put(Commands.HELP, new CommandHelp(mapOfCommandObjects));
	}

}
