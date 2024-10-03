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
import cmdEmulationApp.Commands;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.exceptions.UnvalidCommandException;

/**
 * Класс реализующий функционал команды "cat"
 */
public class CommandCat extends AbstractCommand {
	private int lineNumber;
	public CommandCat() {
		super.supportedOptions = Commands.CAT.supportedOptions;
		this.lineNumber = 1;
	}

	@Override
	public void executeCommand(
			String commandType,
			List<String> commandOptions,
			List<String> commandArgs
		) {

		try {
			super.validateCommandOptions(commandType, commandOptions);
			Map<String, List<String>> catCommandArgsContainer = parseCatCommandArgs(
				commandType, commandArgs);

			processCommandArgs(catCommandArgsContainer, commandOptions);

		} catch (InvalidOptionException error) {
			System.out.println(error);
		} catch (UnvalidCommandException error) {
			System.out.println(error);
		}

	}

	@Override
	public void showHelpInformation() {
		System.out.println("cat: cat [-bEn] [args...] [-] [>] [args...]");
		System.out.println("\tCat, which is short for concatenate, is one of the most commonly used commands in Linux and other Unix-like operating systems.");
		System.out.println("\tThe cat command allows us to create single or multiple files, view file inclusions, concatenate files and redirect output in a terminal or file.");
		System.out.println("\tIn this article, we will show you some handy uses of the cat command and examples of it in Linux.");
		System.out.println();
		System.out.println("\tOptions:");
		System.out.println("\t\t-b\t\tnumbers non-empty lines");
		System.out.println("\t\t-E\t\tadds $ sign to the end of the line");
		System.out.println("\t\t-n\t\tnumbers all lines");
	}

	/**
	 * Метод валидирующий аргументы переданные с командой
	 * @param commandType тип команды
	 * @param commandArgs аргументы команды
	 * @throws UnvalidCommandException исключение некорректно введеной команды
	 */
	private Map<String, List<String>> parseCatCommandArgs(
			String commandType,
			List<String> commandArgs
		) throws UnvalidCommandException {
			
		if (
			(commandArgs.stream()
			.filter(element -> element.equals(">"))
			.collect(Collectors.toList()).size() > 1)
			|| (commandArgs.isEmpty())) {
			throw new UnvalidCommandException(commandType);
		}

			boolean isReadArgs = true;
			Map<String, List<String>> commandArgsContainer = new HashMap<>();
			commandArgsContainer.put("readArguments", new ArrayList<>());
			commandArgsContainer.put("recordArguments", new ArrayList<>());

			for (String argument : commandArgs) {
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
				throw new UnvalidCommandException(commandType);
			}

			return commandArgsContainer;
	}

	private void processCommandArgs(
		Map<String, List<String>> commandArgsContainer,
		List<String> commandOptions) {
	
		List<String> readArgs = commandArgsContainer.get("readArguments");
		List<String> recordArgs = commandArgsContainer.get("recordArguments");
		
		StringBuilder readBuffer = new StringBuilder("");

		for (String filePath : readArgs) {
			readBuffer.append(readFile(filePath, commandOptions));
		}

		this.lineNumber = 1;

		if (!recordArgs.isEmpty()) {
			recordFile(recordArgs.get(0), readBuffer);
			return;
		}

		printCatCommandResult(readBuffer);
	}

	private void printCatCommandResult(StringBuilder readBuffer) {
		System.out.println(readBuffer);
	}

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
