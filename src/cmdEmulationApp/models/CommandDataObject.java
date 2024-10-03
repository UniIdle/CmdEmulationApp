package cmdEmulationApp.models;

import java.util.ArrayList;

public class CommandDataObject {
	private String commandType = "";
	private ArrayList<String> commandOptions = new ArrayList<String>();
	private ArrayList<String> commandArgs = new ArrayList<String>();

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public ArrayList<String> getCommandOptions() {
		return commandOptions;
	}

	public void setCommandOptions(String commandOptions) {
		this.commandOptions.add(commandOptions);
	}

	public ArrayList<String> getCommandArgs() {
		return commandArgs;
	}

	public void setCommandArgs(String commandArgs) {
		this.commandArgs.add(commandArgs);
	}
}
