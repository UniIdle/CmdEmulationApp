package cmdEmulationApp.commands;

import java.io.File;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.Commands;

/**
 * Класс реализующий функционал команды "ls"
 */
public class CommandLs extends AbstractCommand {

	public CommandLs() {
		super.supportedOptions = Commands.LS.supportedOptions;
	}


	@Override
	public void executeCommand(String commandType, List<String> commandOptions, List<String> commandArgs) {
		try {
			super.validateCommandOptions(commandType, commandOptions);

			if (commandArgs.isEmpty()) {
				commandArgs.add(System.getProperty("user.dir"));
			}

			processCommandArgs(commandType, commandOptions, commandArgs);
			
		}  catch (InvalidOptionException error) {
			System.out.println(error);
		}
	}

	@Override
	public void showHelpInformation() {
		System.out.println("ls: ls [-aQr] [args...]");
		System.out.println("\tThe ls command lists the files in your current directory (ls is short for \"list\"). Try it now by typing ls, then hitting <enter>.");
		System.out.println();
		System.out.println("\tOptions:");
		System.out.println("\t\t-a\t\tshow hidden files");
		System.out.println("\t\t-Q\t\tputs file and directories names in quotes");
		System.out.println("\t\t-r\t\tshow files in reverse order");
	}

	/**
	 * Метод выполняющий обработку аргументов переданных из команды
	 */
	private void processCommandArgs(String commandType, List<String> commandOptions, List<String> commandArgs) {
		for (String path : commandArgs) {
			try {
				File folder = new File(path);
				File[] files = folder.listFiles();

				if (folder.isFile()) {
					System.out.println(path);
					continue;
				}

				printResultOfExecuteCommand(commandType, commandOptions, commandArgs, files, path);
	
			} catch (NullPointerException error) {
				System.out.println(commandType + ": cannot access '" + path + "': No such file or directory\n");
			}
		}
	}

	/**
	 * Метод для отображения результатов выполнения команды ls
	 * @param commandType тип команды
	 * @param commandOptions опции команды
	 * @param commandArgs аргументы команды
	 * @param currentDirectoryFiles массив файлов директории переданной в качестве аргумента
	 * @param currentDirectoryPath путь к директории
	 */
	private void printResultOfExecuteCommand (
		String commandType,
		List<String> commandOptions,
		List<String> commandArgs,
		File[] currentDirectoryFiles,
		String currentDirectoryPath) {

		if (commandArgs.size() > 1 && currentDirectoryFiles != null) {
			System.out.println(currentDirectoryPath + ":");
		} 
				processCommandOption(commandOptions, currentDirectoryFiles).stream().forEach(element -> System.out.print(element + " "));
				System.out.println();
				if (commandArgs.size() > 1 && currentDirectoryFiles != null) System.out.println();
	}

	/**
	 * Метод выполняющий обработку дополнительных свойств команды
	 */
	private List<StringBuilder> processCommandOption(List<String> commandOptions, File[] currentDirectoryFiles) {
		List<StringBuilder> listOfCurrentDirectoryFiles = new ArrayList<>();

		if (commandOptions.contains("-a")) {
			listOfCurrentDirectoryFiles = Arrays.asList(currentDirectoryFiles)
				.stream()
				.map(CommandLs::processFileOrDirectoryName)
				.collect(Collectors.toList());
		} else {
			listOfCurrentDirectoryFiles = Arrays.asList(currentDirectoryFiles)
				.stream()
				.filter(file -> !file.isHidden()).map(CommandLs::processFileOrDirectoryName)
				.collect(Collectors.toList());
		}

		for (String option : commandOptions) {
			switch (option) {
				case "-Q":
					listOfCurrentDirectoryFiles = listOfCurrentDirectoryFiles.stream().map(element -> element.insert(7, "\"").insert((element.length() - 4), "\"")).collect(Collectors.toList());
					break;
				case "-r":
					Collections.reverse(listOfCurrentDirectoryFiles);
					break;
				default:
					break;
			}
		}

		return listOfCurrentDirectoryFiles;
	}

	/**
	 * Метод для обработки результата в зависимости, это строка или директория
	 * @param fileName путь к файлу или директории
	 * @return имя директории или файла в обертке ANSII
	 */
	private static StringBuilder processFileOrDirectoryName(File fileName) {
		if (fileName.isFile()) {
			return new StringBuilder("\u001B[37;1m" + fileName.getName() + "\u001B[0m");
		} else {
			return new StringBuilder("\u001B[34;1m" + fileName.getName() + "\u001B[0m");
		}
	}

}
