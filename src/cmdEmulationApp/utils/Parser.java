package cmdEmulationApp.utils;

import java.util.HashMap;
import java.util.Arrays;
import java.util.regex.*;
import cmdEmulationApp.exceptions.UnvalidCommandException;

public class Parser {
	public static HashMap<String, String> commandQualifier(String inputCommand) throws UnvalidCommandException {
		String[] commandAsList = inputCommand.split(" +");
		HashMap<String, String> commandStructure = new HashMap<String, String>();

		commandStructure.put("commandType", commandAsList[0]);
		commandStructure.put("commandOption", "");
		commandStructure.put("commandProperties", "");

		if (commandAsList.length == 1) {
			return commandStructure;
		}

		String commandOption = commandAsList[1];

		Pattern pat = Pattern.compile("-[\\w]");
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

	
}
