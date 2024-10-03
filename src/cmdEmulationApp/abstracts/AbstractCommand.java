package cmdEmulationApp.abstracts;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import cmdEmulationApp.exceptions.InvalidOptionException;

/**
 * Абстрактный класс команды, определяющий основные поля объекта команды и методы доступа к ним
 */
public abstract class AbstractCommand implements Command {
	public String[] supportedOptions;

	// @Override
	// public void executeCommand(String commandType, ArrayList<String> commandOptions, ArrayList<String> commandArgs) {
	// 	try {
	// 		validateCommandOptions(commandType, commandOptions);
	// 	} catch (InvalidOptionException error) {
	// 		System.out.println(error);
	// 	}
	// }

	public void validateCommandOptions(String commandType, List<String> commandOptions) throws InvalidOptionException {
		
		if (commandOptions.isEmpty()) {
			return;
		}

		String commandOptionRegEx = "(-[" + String.join("", supportedOptions) + "])";

		Pattern pat = Pattern.compile(commandOptionRegEx);

		for (String option : commandOptions) {
			Matcher mat = pat.matcher(option);
			if(!mat.matches()) {
				throw new InvalidOptionException(commandType, option);
			} 
		}
	}
}
