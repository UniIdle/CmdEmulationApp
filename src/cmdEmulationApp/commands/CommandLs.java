package cmdEmulationApp.commands;

import java.io.File;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import cmdEmulationApp.models.CommandDataObject;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.constants.Options;

/**
 * Класс реализующий функционал команды "ls"
 */
public class CommandLs extends AbstractCommand {

	public CommandLs() {
		super.supportedOptions = Options.getLsCommandSupportOptions();
	}


	@Override
	public StringBuilder executeCommand(CommandDataObject commandContainer) {

			super.executeCommand(commandContainer);

			if (commandContainer.getCommandArgs().isEmpty()) {
				commandContainer.getCommandArgs().add(System.getProperty("user.dir"));
			}

			return processCommandArgs(commandContainer);
	}

	/**
	 * Метод выполняющий обработку аргументов переданных из команды
	 */
	private StringBuilder processCommandArgs(CommandDataObject commandContainer) {
		StringBuilder resultOfCommand = new StringBuilder();

		for (String path : commandContainer.getCommandArgs()) {
			try {
				File folder = new File(path);
				File[] files = folder.listFiles();

				if (folder.isFile()) {
					System.out.println(path);
					continue;
				}

				resultOfCommand.append(formResultOfExecuteCommand(commandContainer, files, path));
	
			} catch (NullPointerException error) {
				System.out.println(commandContainer.getCommandType() + ": cannot access '" + path + "': No such file or directory\n");
			}
		}

		return resultOfCommand;
	}

	/**
	 * Метод для отображения результатов выполнения команды ls
	 * @param commandType тип команды
	 * @param commandOptions опции команды
	 * @param commandArgs аргументы команды
	 * @param currentDirectoryFiles массив файлов директории переданной в качестве аргумента
	 * @param currentDirectoryPath путь к директории
	 */
	private StringBuilder formResultOfExecuteCommand(
		CommandDataObject commandContainer,
		File[] currentDirectoryFiles,
		String currentDirectoryPath) {

			StringBuilder resultOfArg = new StringBuilder();

			if (commandContainer.getCommandArgs().size() > 1 && currentDirectoryFiles != null) {
				resultOfArg.append(currentDirectoryPath).append(":");//System.out.println(currentDirectoryPath + ":");
			} 

			processCommandOption(commandContainer.getCommandOptions(), currentDirectoryFiles).stream().forEach(element -> resultOfArg.append(element).append(" "));//System.out.print(element + " "));
			resultOfArg.append("\n");//System.out.println();
			if (commandContainer.getCommandArgs().size() > 1 && currentDirectoryFiles != null) {
				resultOfArg.append("\n");//System.out.println();
			} 

			return resultOfArg;
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
