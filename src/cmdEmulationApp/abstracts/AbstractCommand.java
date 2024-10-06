package cmdEmulationApp.abstracts;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.models.CommandDataObject;
import cmdEmulationApp.constants.Commands;

/**
 * Абстрактный класс команды, определяющий основные поля объекта команды и методы доступа к ним
 */
public abstract class AbstractCommand implements Command {
	protected Map<String, String> supportedOptions;

	@Override
	public StringBuilder executeCommand(CommandDataObject commandContainer) {//???????
		StringBuilder resultOfCommand = new StringBuilder();

		try {
			validateCommandOptions(commandContainer);
		} catch (InvalidOptionException error) {
			System.out.println(error);
		}

		return resultOfCommand;
	}

	@Override
	public void validateCommandOptions(CommandDataObject commandContainer) throws InvalidOptionException{
		
		if (commandContainer.getCommandOptions().isEmpty()) {
			return;
		}

		compareInputedAndSupportedOptions(commandContainer);
	}

	@Override
	public StringBuilder formHelpInformation(Commands commandInfo) {
		StringBuilder helpInfo = new StringBuilder();
		helpInfo.append(commandInfo.schema).append(commandInfo.description);

		if (!supportedOptions.isEmpty()) {
			helpInfo.append("\n").append("\tOptions:\n");
			supportedOptions
				.entrySet()
				.stream()
				.forEach(option -> helpInfo.append("\t").append(option.getKey()).append("\t").append(option.getValue()));
		}

		helpInfo.append("\n");

		return helpInfo;
	}

	private void compareInputedAndSupportedOptions(CommandDataObject commandContainer) throws InvalidOptionException {
		String commandOptionRegEx = "(-[" + String.join("", supportedOptions.keySet()) + "])";
		Pattern pat = Pattern.compile(commandOptionRegEx);

		for (String option : commandContainer.getCommandOptions()) {
			Matcher mat = pat.matcher(option);
			if(!mat.matches()) {
				throw new InvalidOptionException(commandContainer.getCommandType(), option);
			} 
		}
	}

}
