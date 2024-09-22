package cmdEmulationApp.abstracts;

/**
 * Интерфейс, определяющий функционал команды
 */
public interface Command {

	/**
	 * Метод осуществляющий выполнение конкретной команды
	 */
	void executeCommand();

	/**
	 * Метод реализующий выполнение функционала дополнительной опции
	 */
	void processCommandOption();

	/**
	 * Метод показывающий подробную информацию о команде
	 */
	void showHelpInformation();
}
