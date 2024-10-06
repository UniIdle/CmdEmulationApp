package cmdEmulationApp.constants;

import java.util.Map;
import java.util.HashMap;

public class Options {
	private static Map<String, String> catCommandSupportOptions = new HashMap<>();
	private static Map<String, String> lsCommandSupportOptions = new HashMap<>();
	private static Map<String, String> helpCommandSupportOptions = new HashMap<>();
	private static Map<String, String> exitCommandSupportOptions = new HashMap<>();

	static {
		lsCommandSupportOptions.put("a", "show hidden files\n");
		lsCommandSupportOptions.put("Q", "puts file and directories names in quotes\n");
		lsCommandSupportOptions.put("r", "show files in reverse order\n");

		catCommandSupportOptions.put("b", "numbers non-empty lines\n");
		catCommandSupportOptions.put("E", "adds '$' sign to the end of the line\n");
		catCommandSupportOptions.put("n", "numbers all lines\n");
	}

	public static Map<String, String> getCatCommandSupportOptions() {
		return catCommandSupportOptions;
	}

	public static Map<String, String> getLsCommandSupportOptions() {
		return lsCommandSupportOptions;
	}

	public static Map<String, String> getHelpCommandSupportOptions() {
		return helpCommandSupportOptions;
	}

	public static Map<String, String> getExitCommandSupportOptions() {
		return exitCommandSupportOptions;
	}
	
}
