package cmdEmulationApp.utils;

// import java.util.HashMap;
import java.util.ArrayList;
// import java.util.regex.Pattern;
// import java.util.regex.Matcher;
import cmdEmulationApp.models.CommandDataObject;

/**
 * Класс, содрежащий в себе методы для извлечения необходимой информации из получаемых строк
 */
public class Parser {
	/**
	 * Метод для получения полной информации из команды введеной пользователем
	 * @param inputCommand команда введеная пользователем
	 * @return связанный список с полями, маркерующими информацю из введеной команды
	 */
	public static CommandDataObject parseInputedCommand(String inputCommand) {
		String[] commandAsList = inputCommand.split(" +");
		CommandDataObject commandData = new CommandDataObject();

		commandData.setCommandType(commandAsList[0]);

		if (commandAsList.length < 2) {
			return commandData;
		}

		for (int i = 1; i < commandAsList.length; i++) {
			String element = commandAsList[i];
			if (element.startsWith("-")) {
				commandData.setCommandOptions(element);
			} else {
				commandData.setCommandArgs(element);
			}
		}

		return commandData;
	}

	/**
	 * Метод определяющий режим работы "cat" команды
	 * @param commandProperties дополнительные свойства команды
	 * @return булевы значения true - catinate режим, false - show режим
	 */
	public static boolean parseCatCommandMode(ArrayList<String> commandProperties) {
		// Pattern pat = Pattern.compile("([^ >]+ +)*>( +[^ >]+)*");
		// Matcher mat = pat.matcher(commandProperties);

		// if (mat.matches()) return true;

		return false;
	}
}
