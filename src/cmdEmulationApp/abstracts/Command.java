package cmdEmulationApp.abstracts;

import java.util.List;
import cmdEmulationApp.exceptions.ExitCommandException;

/**
 * Интерфейс, определяющий функционал команды
 */
public interface Command {

	/**
	 * Метод осуществляющий выполнение конкретной команды
	 */
	void executeCommand(String commandType, List<String> commandOption, List<String> commandProperties) throws ExitCommandException;

	/**
	 * Метод показывающий подробную информацию о команде
	 */
	void showHelpInformation();
}
