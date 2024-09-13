package cmdEmulationApp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.exceptions.UnvalidCommandException;

public class Validator {
	public static void commandValidator(String inputCommand) throws UnvalidCommandException {
		String commandTypeRegEx = "[\\w]+ *";
		String commandOptionRegEx = "(-[\\w])? *";

		Pattern pat = Pattern.compile(commandTypeRegEx + commandOptionRegEx + "([^ ]* *)*");
		Matcher mat = pat.matcher(inputCommand);

		if (!mat.matches()) {
			throw new UnvalidCommandException(inputCommand);
		}
	}

	public static void optionValidator(String commandType, String commandOption, String[] validOptions) throws InvalidOptionException {

		if (commandOption.equals("")) {
			return;
		}

		String commandOptionRegEx = "(-[" + String.join("", validOptions) + "])";

		Pattern pat = Pattern.compile(commandOptionRegEx);
		Matcher mat = pat.matcher(commandOption);

		if (!mat.matches()) {
			throw new InvalidOptionException(commandType, commandOption);
		}
	}
	
}
