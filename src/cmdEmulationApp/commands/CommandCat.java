package cmdEmulationApp.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.constants.Commands;
import cmdEmulationApp.constants.Options;
import cmdEmulationApp.exceptions.UnvalidCommandException;
import cmdEmulationApp.models.CommandDataObject;

/**
 * Класс реализующий функционал команды "cat"
 */
public class CommandCat extends AbstractCommand {
	private int lineNumber;

	public CommandCat() {
		supportedOptions = Options.getCatCommandSupportOptions();
		this.lineNumber = 1;//Стоит ли инициализировать в конструкторе??
	}

	@Override
	public StringBuilder executeCommand(CommandDataObject commandContainer) {
		StringBuilder resultOfCommand;
		resultOfCommand = super.executeCommand(commandContainer);

		try {
			Map<String, List<String>> catCommandArgsContainer = parseCatCommandArgs(commandContainer);

			resultOfCommand.append(processCommandArgs(catCommandArgsContainer, commandContainer.getCommandOptions()));

		} catch (UnvalidCommandException error) {
			System.out.println(error);
		}

		return resultOfCommand;
	}

	/**
	 * Метод валидирующий аргументы переданные с командой
	 * @param commandType тип команды
	 * @param commandArgs аргументы команды
	 * @throws UnvalidCommandException исключение некорректно введеной команды
	 */
	private Map<String, List<String>> parseCatCommandArgs(CommandDataObject commandContainer) throws UnvalidCommandException {//Что лучше передавать данному методу???
			
		if (
			(commandContainer.getCommandArgs().stream()
			.filter(element -> element.equals(">"))
			.collect(Collectors.toList()).size() > 1)
			|| (commandContainer.getCommandArgs().isEmpty())) {
			throw new UnvalidCommandException(commandContainer.getCommandType());
		}

			boolean isReadArgs = true;
			Map<String, List<String>> commandArgsContainer = new HashMap<>();
			commandArgsContainer.put("readArguments", new ArrayList<>());
			commandArgsContainer.put("recordArguments", new ArrayList<>());

			for (String argument : commandContainer.getCommandArgs()) {
				if (argument.equals(">")) {
					isReadArgs = false;
					continue;
				}

				if (isReadArgs) {
					commandArgsContainer.get("readArguments").add(argument);
				} else {
					commandArgsContainer.get("recordArguments").add(argument);
				}
			}

			if (!isReadArgs && commandArgsContainer.get("recordArguments").size() != 1) {
				throw new UnvalidCommandException(commandContainer.getCommandType());
			}

			return commandArgsContainer;
	}

	private StringBuilder processCommandArgs(
		Map<String, List<String>> commandArgsContainer,
		List<String> commandOptions) {
	
		List<String> readArgs = commandArgsContainer.get("readArguments");
		List<String> recordArgs = commandArgsContainer.get("recordArguments");
		
		StringBuilder readBuffer = new StringBuilder();

		for (String filePath : readArgs) {
			readBuffer.append(readFile(filePath, commandOptions));
		}

		this.lineNumber = 1;

		if (!recordArgs.isEmpty()) {
			recordFile(recordArgs.get(0), readBuffer);
			return new StringBuilder();
		}

		// readBuffer.append(formCatCommandResult());

		return readBuffer;
	}

	// private void formCatCommandResult(StringBuilder readBuffer) {
	// 	System.out.println(readBuffer);
	// }

	/**
	 * Метод для обработки опции команды
	 * @param commandOptions опции команды
	 * @param readLine строка считанная из файла
	 * @return преобразованная строка readLine согласно полученным опциям
	 */
	private String processCommandOptions(List<String> commandOptions, String readLine) {

		if (commandOptions.contains("-b") && commandOptions.contains("-n")) {
			Iterator<String> optionsIterator = commandOptions.iterator();
			while(optionsIterator.hasNext()) {
				String nextOption = optionsIterator.next();
				if (nextOption.equals("-n")) {
					optionsIterator.remove();
				}
			}
		}

		for (String option : commandOptions) {
			switch (option) {
				case "-b":
					if (!readLine.equals("")) {
						readLine = "\t" + this.lineNumber++ + "  " + readLine;
					}
					break;
				case "-E":
						readLine = readLine + "$";
					break;
				case "-n":
					readLine = "\t" + this.lineNumber++ + "  " + readLine;
					break;
				default:
					break;
			}
		}

		return readLine + "\n";

	}

	private StringBuilder readFile(String filePath, List<String> commandOptions) {
		StringBuilder fileContents = new StringBuilder("");

		try {
			Scanner scanner = new Scanner(new File(filePath));

			while (scanner.hasNextLine()) {
				fileContents.append(processCommandOptions(commandOptions, scanner.nextLine()));
			}

			scanner.close();
		} catch (FileNotFoundException e) {

		}
		return fileContents;
	}

	private void recordFile(String filePath, StringBuilder readBuffer) {

		try {
			FileWriter writer = new FileWriter(new File(filePath), true);
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			writer.write(readBuffer.toString());
			writer.close();
			bufferWriter.close();
		} catch (IOException e) {
			System.out.println(Commands.CAT + ": error writing to file :" + filePath);
		}

	}

}
