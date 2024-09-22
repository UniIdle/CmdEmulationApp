package cmdEmulationApp.abstracts;

/**
 * Интерфес, описывающий общее поведение командной строки.
 */
public interface CommandLine {

	/**
	 * Метод запускающий командную строку.
	 */
	void launchCommandLine();

	/**
	 * Метод завершающий работу командной строки.
	 */
	void stopCommandLine();

	/**
	 * Метод, реализующий принятие введеной последовательности символов в строке.
	 */
	void recieveInputedCommand();

	/**
	 * Метод по обработке принятой строки.
	 * @param commandType тип введеной команды
	 * @param commandOption необязательная опция расширяющая возможности команды
	 * @param commandProperties дополнительные свойства индивидуальные для каждого типа команды
	 */
	void processInputedCommand(String commandType, String commandOption, String commandProperties);
}
