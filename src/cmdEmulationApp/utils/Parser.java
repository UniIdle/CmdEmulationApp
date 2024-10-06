package cmdEmulationApp.utils;

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
		CommandDataObject commandContainer = new CommandDataObject();

		commandContainer.setCommandType(commandAsList[0].toUpperCase());

		if (commandAsList.length < 2) {
			return commandContainer;
		}

		fillOptionsAndArgsLists(commandContainer, commandAsList);//Стоит ли выносить в отдельный метод? если да, то как лучше реализовать void или return commandContainer

		return commandContainer;
	}

	private static void fillOptionsAndArgsLists(CommandDataObject commandContainer, String[] commandAsList) {
		for (int i = 1; i < commandAsList.length; i++) {
			String element = commandAsList[i];
			if (element.startsWith("-")) {
				commandContainer.addCommandOptions(element);
			} else {
				commandContainer.addCommandArgs(element);
			}
		}
	}

}
