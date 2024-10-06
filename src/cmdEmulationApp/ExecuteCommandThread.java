package cmdEmulationApp;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import cmdEmulationApp.abstracts.Command;
import cmdEmulationApp.models.CommandDataObject;

public class ExecuteCommandThread extends Thread {
	private Command objectOfCommand;
	private CommandDataObject commandContainer;
	public BufferedWriter resultOfCommandExecution;


	ExecuteCommandThread(Command objectOfCommand, CommandDataObject commandContainer) {
		super("Command execution flow.");
		this.commandContainer = commandContainer;
		this.objectOfCommand = objectOfCommand;
		resultOfCommandExecution = new BufferedWriter(new OutputStreamWriter(System.out));
	}

	public BufferedWriter getResultOfCommandExecution() {
		return resultOfCommandExecution;
	}


	public void run() {
		try {
			resultOfCommandExecution.write(objectOfCommand.executeCommand(commandContainer).toString());
		} catch (Exception e) {
			
		}
	}
	
}
