package cmdEmulationApp.utils;

import java.util.regex.Pattern;
// import java.util.ArrayList;
import java.util.regex.Matcher;
// import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.exceptions.UnvalidCommandException;

/**
 * Класс содержащий в себе методы для валидации
 */
public class Validator {

	/**
	 * Метод для проверки опций, применяемых для введенной команды
	 * @param commandType тип введеной команды
	 * @param commandOption необязательная опция расширяющая возможности команды
	 * @param validOptions список опций поддерживаемых в команде
	 * @throws InvalidOptionException
	 */
	// public static void validateCommandOptions(String commandType, ArrayList<String> commandOptions, String[] validOptions) throws InvalidOptionException {

	// 	if (commandOptions.equals("")) {
	// 		return;
	// 	}

	// 	String commandOptionRegEx = "(-[" + String.join("", validOptions) + "]?)";

	// 	Pattern pat = Pattern.compile(commandOptionRegEx);
	// 	Matcher mat = pat.matcher(commandOptions);

	// 	if (!mat.matches()) {
	// 		throw new InvalidOptionException(commandType, commandOption);
	// 	}
	// }

	/**
	 * Метод для валидации catinate мода команды "cat"
	 * @param commandType тип введеной команды
	 * @param commandProperties дополнительные свойства индивидуальные для каждого типа команды
	 * @throws UnvalidCommandException исключение "выбрасываемое" при некорректно введеной последовательности инструкции данного мода
	 */
	public static void validateCatCommandCatinateMode(String commandType, String commandProperties) throws UnvalidCommandException{
		Pattern pat = Pattern.compile("([^ ]+ +)*>( +[^ ]+)+");
		Matcher mat = pat.matcher(commandProperties);

		if (!mat.matches()) {
			throw new UnvalidCommandException(commandType);
		}
	}

	/**
	 * Метод для проверки является ли введенная строка указателем на директорию
	 * @param commandPath строка указывающая путь к директории или файлу
	 * @return булевы значения true - директория, false - файл
	 */
	static public boolean validatePath(String commandPath) {
		String osFileSeparator = System.getProperty("os.name").equals("Linux") ? "/" : "\\\\";
		String osPrefix = System.getProperty("os.name").equals("Linux") ? "" : "[\\w]\\:";

		Pattern pat = Pattern.compile(osPrefix + "(" + osFileSeparator + "[^\":*?\\/<>|]+)*");
		Matcher mat = pat.matcher(commandPath);

		return mat.matches();
	}
	
}
