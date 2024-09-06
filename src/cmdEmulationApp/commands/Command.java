package cmdEmulationApp.commands;

import java.util.regex.*;

public class Command {
	private String command;
	private String commandType;

	public Command(String command) {
		this.command = command;
		this.commandType = setCommandType(command);
	}

	private String setCommandType(String command) {	
		Pattern pat = Pattern.compile("[^ ]+");
		Matcher mat = pat.matcher(command);
		mat.find();

		return mat.group();
	}

	String getCommand() {
		return this.command;
	}

	public String getCommandType() {
		return this.commandType;
	}
}
