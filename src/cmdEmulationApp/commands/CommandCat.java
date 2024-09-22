package cmdEmulationApp.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import cmdEmulationApp.abstracts.AbstractCommand;
import cmdEmulationApp.exceptions.InvalidOptionException;
import cmdEmulationApp.exceptions.UnvalidCommandException;
import cmdEmulationApp.utils.Parser;
import cmdEmulationApp.utils.Validator;

/**
 * Класс реализующий функционал команды "cat"
 */
public class CommandCat extends AbstractCommand {
	final private String[] CAT_COMMAND_OPTIONS_LIST = {"b", "E", "n"};
	private boolean catinateMode = false;
	private int counterOfLine = 1;
	private StringBuilder catinateModeBuffer = new StringBuilder("");
	private String currentDirectory = System.getProperty("user.dir");
	private File[] currentDirectoryFiles = this.getCurrentDirectoryFiles();

	public void setCommandProperties(String commandProperties) {
		if (commandProperties.equals("")) {
			super.setCommandProperties("-");
		} else {
			super.setCommandProperties(commandProperties);
		}
	}

	private File[] getCurrentDirectoryFiles() {
		File folder = new File(this.currentDirectory);
		File[] files = folder.listFiles();

		return files;
	}

	public void executeCommand() {
		this.catinateMode = Parser.parseCatCommandMode(super.getCommandProperties());

		try {
			if (this.catinateMode) {
				Validator.validateCatCommandCatinateMode(
					super.getCommandType(),
					super.getCommandProperties()
				);
				this.processCommandCatinateMode();

			} else {
				Validator.validateCommandOptions(
					super.getCommandType(),
					super.getCommandOption(),
					CAT_COMMAND_OPTIONS_LIST
				);

				this.processCommandShowMode();
			}

		} catch (InvalidOptionException error) {
			System.out.println(error);
		} catch (UnvalidCommandException error) {
			System.out.println(error);
		}

		this.catinateMode = false;
		this.counterOfLine = 1;
	}

	public void processCommandOption() {
	}

	private void processCommandOption(String outputString) {
		switch (super.getCommandOption()) {
			case "-b":
				if (!outputString.equals("")) {
					System.out.println("\t" + this.counterOfLine++ + "  " + outputString);
				} else {
					System.out.println();
				}
				break;
			case "-E":
				System.out.println(outputString + "$");
				break;
			case "-n":
				System.out.println("\t" + this.counterOfLine++ + "  " + outputString);
				break;
			default:
				System.out.println(outputString);
				break;
		}
	}

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
	 * Метод реализующий обработку в режиме отображения
	 */
	private void processCommandShowMode() {
		String[] ListOfCommandProperties = super.getCommandProperties().split(" +");

		for (String property : ListOfCommandProperties) {
			if (property.equals("-")) {
				this.launchInputOutputMode();
				continue;
			}

			try {
				Scanner scanner = new Scanner(new File(property));

				while (scanner.hasNextLine()) {
					this.processCommandOption(scanner.nextLine());
				}

				scanner.close();

			} catch (FileNotFoundException error) {
				System.out.println(this.getCommandType() + ": " + property + ": No such file or directory");
			}
		}
	}

	/**
	 * Метод реализующий обработку в режиме катенации
	 */
	private void processCommandCatinateMode() {
		String[] ListOfCommandProperties = super.getCommandProperties().split(" +");
		boolean isRecordMode = false;

		for (int i = 0; i < ListOfCommandProperties.length; i++) {
			String element = ListOfCommandProperties[i];

			if (isRecordMode) {
				this.recordFile(element);

			} else {
				if (element.equals(">")) {
					isRecordMode = true;

					if (i == 0) {
						this.launchInputOutputMode();
					}

					continue;
				}

				if (element.equals("-")) {
					this.launchInputOutputMode();
					continue;
				}

				this.readFile(element);
			}
		}

	}

	/**
	 * Метод для запуска интерактивного режима ввода-вывода последовательностей от пользователя
	 */
	private void launchInputOutputMode() {
		Scanner scanner = new Scanner(System.in);

		try {
			while (true) {
				String inputString = scanner.nextLine();
				this.processCommandOption(inputString);

				if (this.catinateMode) {
					this.catinateModeBuffer.append(inputString + "\n");
				}
			}
		} catch (NoSuchElementException error) {}
	}

	private void readFile(String filePath) {
		filePath = this.processFileName(filePath);

		try {
			Scanner scanner = new Scanner(new File(filePath));

			while (scanner.hasNextLine()) {
				this.catinateModeBuffer.append(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {

		}
	}

	private void recordFile(String filePath) {
		this.createNewFile(filePath);

		if (!Validator.validatePath(filePath)) {
			filePath = this.currentDirectory + System.getProperty("file.separator") + filePath;
		}

		try {
			FileWriter writer = new FileWriter(filePath, true);
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			writer.write(this.catinateModeBuffer.toString());
			writer.close();
			bufferWriter.close();
		} catch (IOException e) {
			System.out.println(super.getCommandType() + ": error writing to file :" + filePath);
		}
	}

	private void createNewFile(String fileName) {
		try {
			if (Validator.validatePath(fileName)) {
				File file = new File(fileName);
				file.createNewFile();
			} else {
				File file = new File(this.currentDirectory + System.getProperty("file.separator") + fileName);
				file.createNewFile();
			}
		} catch (IOException error) {
			System.out.println(super.getCommandType() + ": error creating file by name: " + fileName);
		}
	}

	/**
	 * Метод проверяющий наличие файла в текущей директории
	 * @param fileName имя файла
	 * @return полученную строку или полный путь к файлу, если указанный файл существует в директории
	 */
	private String processFileName(String fileName) {
		for (File file : this.currentDirectoryFiles) {
			if (file.getName().equals(fileName)) {
				return fileName = this.currentDirectory + System.getProperty("file.separator") + fileName;
			}
		}

		return fileName;
	}

}
