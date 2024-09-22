package cmdEmulationApp.utils;

import java.util.HashMap;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Класс, содрежащий в себе методы для извлечения необходимой информации из получаемых строк
 */
public class Parser {
	/**
	 * Метод для получения полной информации из команды введеной пользователем
	 * @param inputCommand команда введеная пользователем
	 * @return связанный список с полями, маркерующими информацю из введеной команды
	 */
	public static HashMap<String, String> parseInputedCommand(String inputCommand) {
		String[] commandAsList = inputCommand.split(" +");
		HashMap<String, String> commandStructure = new HashMap<String, String>();

		commandStructure.put("commandType", commandAsList[0]);
		commandStructure.put("commandOption", "");
		commandStructure.put("commandProperties", "");

		if (commandAsList.length == 1) {
			return commandStructure;
		}

		String commandOption = commandAsList[1];

		Pattern pat = Pattern.compile("-[\\w]?");
		Matcher mat = pat.matcher(commandOption);

		if (mat.matches()) {
			commandStructure.put("commandOption", commandOption);
			commandStructure.put("commandProperties", String.join(" ", Arrays.copyOfRange(commandAsList, 2, commandAsList.length)));
		} else {
			commandStructure.put("commandOption", "");
			commandStructure.put("commandProperties", String.join(" ", Arrays.copyOfRange(commandAsList, 1, commandAsList.length)));
		}

		return commandStructure;
	}

	/**
	 * Метод определяющий режим работы "cat" команды
	 * @param commandProperties дополнительные свойства команды
	 * @return булевы значения true - catinate режим, false - show режим
	 */
	public static boolean parseCatCommandMode(String commandProperties) {
		Pattern pat = Pattern.compile("([^ >]+ +)*>( +[^ >]+)*");
		Matcher mat = pat.matcher(commandProperties);

		if (mat.matches()) return true;

		return false;
	}
}
