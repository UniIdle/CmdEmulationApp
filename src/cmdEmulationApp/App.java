package cmdEmulationApp;

import cmdEmulationApp.abstracts.CommandLine;

/**
Класс - точкf входа в приложение
 */
public class App {
	public static void main(String[] args) {
		CommandLine core = new LinuxCommandLine();
		core.launchCommandLine();
	}
}
