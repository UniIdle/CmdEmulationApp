package cmdEmulationApp.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import cmdEmulationApp.exceptions.*;
import cmdEmulationApp.utils.Parser;
import cmdEmulationApp.utils.Validator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class CommandCat extends Command {
	final private String[] CAT_COMMAND_OPTIONS_LIST = {"b", "E", "n"};
	private String commandProperties;
	private boolean catinateMode = false;
	private int counterOfLine = 1;
	private StringBuilder catinateModeBuffer = new StringBuilder("");
	private String currentDirectory = System.getProperty("user.dir");
	private File[] currentDirectoryFiles = this.getCurrentDirectoryFiles();


	public CommandCat(String inputCommand, String commandType, String commandOption, String commandProperties) {
		super(inputCommand, commandType, commandOption);
		this.commandProperties = commandProperties.equals("") ? "-" : commandProperties;
	}

	private File[] getCurrentDirectoryFiles() {
		File folder = new File(this.currentDirectory);
		File[] files = folder.listFiles();

		return files;
	}

	public void commandExecutor() {
		this.catinateMode = Parser.catCommandModeParser(commandProperties);

		try {
			if (this.catinateMode) {
				Validator.catinateModeValidator(
					super.getCommandType(),
					this.commandProperties
				);
				this.commandCatinateModeHandler();

			} else {
				Validator.optionValidator(
					super.getCommandType(),
					super.getCommandOption(),
					CAT_COMMAND_OPTIONS_LIST
				);

				this.commandShowModeHandler();
			}

		}  catch (InvalidOptionException error) {
			System.out.println(error);
		} catch (UnvalidCommandException error) {
			System.out.println(error);
		}
	}

	private void commandShowModeHandler() {
		String[] ListOfCommandProperties = this.commandProperties.split(" +");

		for (String property : ListOfCommandProperties) {
			if (property.equals("-")) {
				this.inputOutputMode();
				continue;
			}

			try {
				Scanner scanner = new Scanner(new File(property));

				while (scanner.hasNextLine()) {
					this.optionHandler(scanner.nextLine());
				}

				scanner.close();

			} catch (FileNotFoundException error) {
				System.out.println(this.getCommandType() + ": " + property + ": No such file or directory");
			}
		}

	}

	private void commandCatinateModeHandler() {
		String[] ListOfCommandProperties = this.commandProperties.split(" +");
		boolean isRecordMode = false;

		for (int i = 0; i < ListOfCommandProperties.length; i++) {
			String element = ListOfCommandProperties[i];

			if (isRecordMode) {
				this.fileRecorder(element);

			} else {
				if (element.equals(">")) {
					isRecordMode = true;

					if (i == 0) {
						this.inputOutputMode();
					}

					continue;
				}

				if (element.equals("-")) {
					this.inputOutputMode();
					continue;
				}

				this.fileReader(element);
			}
		}

	}

	private void optionHandler(String outputString) {
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

	private void inputOutputMode() {
		Scanner scanner = new Scanner(System.in);

		try {
			while (true) {
				String inputString = scanner.nextLine();
				this.optionHandler(inputString);

				if (this.catinateMode) {
					this.catinateModeBuffer.append(inputString + "\n");
				}
			}
		} catch (NoSuchElementException error) {}
	}

	private void fileReader(String filePath) {
		filePath = this.fileNameHandler(filePath);

		try {
			Scanner scanner = new Scanner(new File(filePath));

			while (scanner.hasNextLine()) {
				this.catinateModeBuffer.append(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {

		}
		
	}

	private void fileRecorder(String filePath) {
		this.fileCreator(filePath);

		if (!Validator.pathValidator(filePath)) {
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

	private String fileNameHandler(String fileName) {
		for (File file : this.currentDirectoryFiles) {
			if (file.getName().equals(fileName)) {
				return fileName = this.currentDirectory + System.getProperty("file.separator") + fileName;
			}
		}

		return fileName;
	}

	private void fileCreator(String fileName) {
		try {
			if (Validator.pathValidator(fileName)) {
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

}
