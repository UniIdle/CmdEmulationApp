package cmdEmulationApp.abstracts;

/**
 * Абстрактный класс команды, определяющий основные поля объекта команды и методы доступа к ним
 */
public abstract class AbstractCommand implements Command {

	private String commandType;
	private String commandOption;
	private String commandProperties;

	public String getCommandType() {
		return this.commandType;
	}

	public void setCommandType(String commandOption) {
		this.commandOption = commandOption;
	}

	public String getCommandOption() {
		return this.commandOption;
	}

	public void setCommandOption(String commandOption) {
		this.commandOption = commandOption;
	}
	
	public String getCommandProperties() {
		return this.commandProperties;
	}

	public void setCommandProperties(String commandProperties) {
		this.commandProperties = commandProperties;
	}
}
