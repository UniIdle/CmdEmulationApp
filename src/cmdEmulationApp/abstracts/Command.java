package cmdEmulationApp.abstracts;

import cmdEmulationApp.constants.Commands;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.models.CommandDataObject;

/**
 * Интерфейс, определяющий функционал команды
 */
public interface Command {

	/**
	 * Метод осуществляющий выполнение конкретной команды
	 */
	StringBuilder executeCommand(CommandDataObject commandContainer);

	/**
	 * Метод для валидации полученных опций
	 * @param commandContainer объект с данными команды
	 */
	void validateCommandOptions(CommandDataObject commandContainer) throws InvalidOptionException;

	/**
	 * Метод показывающий подробную информацию о команде
	 */
	StringBuilder formHelpInformation(Commands commandInfo);
}
