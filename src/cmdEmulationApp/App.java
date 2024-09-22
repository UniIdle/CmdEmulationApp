package cmdEmulationApp;

/**
Класс - точкf входа в приложение
 */
public class App {
	public static void main(String[] args) {
		LinuxCommandLine core = new LinuxCommandLine();
		core.launchCommandLine();
	}
}
