package cmdEmulationApp;

import java.util.Map;
import java.util.HashMap;
import cmdEmulationApp.abstracts.AbstractCommandLineCore;
import cmdEmulationApp.abstracts.Command;
import cmdEmulationApp.commands.CommandCat;
import cmdEmulationApp.commands.CommandLs;
import cmdEmulationApp.constants.Commands;
import cmdEmulationApp.commands.CommandHelp;
import cmdEmulationApp.commands.CommandExit;
import cmdEmulationApp.models.CommandDataObject;

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
		printStartInformation();
		super.launchCommandLine();
	}

	@Override
	public void processInputedCommand(CommandDataObject commandContainer) {
		try {
			Command objectOfCommand = this.mapOfCommandObjects.get(Commands.valueOf(commandContainer.getCommandType()));

			ExecuteCommandThread flowOfExecuteCommand = new ExecuteCommandThread(objectOfCommand, commandContainer);
			flowOfExecuteCommand.start();

			try {
				flowOfExecuteCommand.join();
				flowOfExecuteCommand.getResultOfCommandExecution().flush();
			} catch (Exception e) {
				
			}

		} catch (IllegalArgumentException error) {
			System.out.printf("%s: command not found\n", commandContainer.getCommandType());
		}
	}

	@Override
	public void showPrefix() {
		System.out.print("\u001B[32;1m" + "user@DESKTOP-GU0OIRP" + "\u001B[0m" + ":" + "\u001B[34;1m" + "~" + "\u001B[0m" + "$ ");
	}

		/**
	 * Метод отображающий стартовую информацию при запуске командной строки
	 */
	private void printStartInformation() {
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
