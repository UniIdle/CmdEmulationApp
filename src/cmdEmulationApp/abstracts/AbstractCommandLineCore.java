package cmdEmulationApp.abstracts;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import cmdEmulationApp.utils.Parser;

/**
 * Абстрактный класс, частично реализующий базовый функционал интерфейса CommandLine.
 */
public abstract class AbstractCommandLineCore implements CommandLine {
	private Scanner commandLineScanner = new Scanner(System.in);
	private String inputedCommand;
	private boolean coreTrigger = true;

	public void launchCommandLine() {
		while(this.coreTrigger) {
			showPrefix();
			
			try {
				this.recieveInputedCommand();

				if (this.inputedCommand.equals("")) {
					continue;
				}

				HashMap<String, String> commandAsMap = Parser.parseInputedCommand(this.inputedCommand);

				if (commandAsMap.get("commandType").toLowerCase().equals("exit")) {
					this.stopCommandLine();
					continue;
				}

				processInputedCommand(
					commandAsMap.get("commandType"),
					commandAsMap.get("commandOption"),
					commandAsMap.get("commandProperties")
				);
			} catch (NoSuchElementException error) {//Обработка исключения выбрасываемого при нажатии комбинации клавиш Ctrl + D для прерывания
				this.stopCommandLine();
			}
		}
	}

	public void stopCommandLine() {
		this.coreTrigger = false;
		System.out.print("\n");
		this.commandLineScanner.close();
	}

	public void recieveInputedCommand() {
		this.inputedCommand = this.commandLineScanner.nextLine().trim();
	}

	/**
	 * Метод отображающий разделитель строки, ожидающей команду от пользователя
	 */
	public abstract void showPrefix();

}
