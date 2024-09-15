package cmdEmulationApp.utils;

import java.util.regex.*;
import java.util.Arrays;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.exceptions.UnvalidCommandException;
import cmdEmulationApp.exceptions.CommandNotFoundException;

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

	public static void catinateModeValidator(String commandType, String commandProperties) throws UnvalidCommandException{
		Pattern pat = Pattern.compile("([^ ]+ +)*>( +[^ ]+)+");
		Matcher mat = pat.matcher(commandProperties);

		if (!mat.matches()) {
			throw new UnvalidCommandException(commandType);
		}
	}

	static public boolean pathValidator(String commandPath) {
		String osFileSeparator = System.getProperty("os.name").equals("Linux") ? "/" : "\\\\";
		String osPrefix = System.getProperty("os.name").equals("Linux") ? "" : "[\\w]\\:";

		Pattern pat = Pattern.compile(osPrefix + "(" + osFileSeparator + "[^\":*?\\/<>|]+)*");
		Matcher mat = pat.matcher(commandPath);

		return mat.matches();
	}

	static public void helpCommandValidator(String commandProperty, String[] accessCommandsList) throws CommandNotFoundException {
		if (!Arrays.asList(accessCommandsList).contains(commandProperty)) {
			throw new CommandNotFoundException(commandProperty);
		}
	}
	
}
