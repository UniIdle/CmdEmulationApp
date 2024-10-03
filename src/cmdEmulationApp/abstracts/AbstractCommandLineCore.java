package cmdEmulationApp.abstracts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import cmdEmulationApp.models.CommandDataObject;
import cmdEmulationApp.utils.Parser;

/**
 * Абстрактный класс, частично реализующий базовый функционал интерфейса CommandLine.
 */
public abstract class AbstractCommandLineCore implements CommandLine {
	private BufferedReader commandLineBufferReader = new BufferedReader(new InputStreamReader(System.in));
	private String inputedCommand;
	private boolean isDisabledCore = false;

	@Override
	public void launchCommandLine() {
		while(!this.isDisabledCore) {
			showPrefix();
			
			try {
				this.recieveInputedCommand();

				if (this.inputedCommand.equals("")) {
					continue;
				}

				CommandDataObject commandAsMap = Parser.parseInputedCommand(this.inputedCommand);

				processInputedCommand(
					commandAsMap.getCommandType(),
					commandAsMap.getCommandOptions(),
					commandAsMap.getCommandArgs()
				);
			} catch (NullPointerException error) {//Обработка исключения выбрасываемого при нажатии комбинации клавиш Ctrl + D для прерывания
				this.stopCommandLine();
			}
		}
	}

	@Override
	public void stopCommandLine() {
		this.isDisabledCore = true;
		System.out.print("\n");
	}

	@Override
	public void recieveInputedCommand() {
		try {
			this.inputedCommand = this.commandLineBufferReader.readLine().trim();
		} catch (IOException error) {

		}
	}

	/**
	 * Метод отображающий разделитель строки, ожидающей команду от пользователя
	 */
	public abstract void showPrefix();

}
