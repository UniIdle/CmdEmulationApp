package cmdEmulationApp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmdEmulationApp.exceptions.UnvalidPathException;

public class PathValidator {
	static public void pathValidator(String commandPath) throws UnvalidPathException {
		String osFileSeparator = System.getProperty("os.name").equals("Linux") ? "/" : "\\\\";
		String osPrefix = System.getProperty("os.name").equals("Linux") ? "" : "[\\w]\\:";

		Pattern pat = Pattern.compile(osPrefix + "(" + osFileSeparator + "[^+=\\[\\]:*?;«,.\\/<>|]+)*" + osFileSeparator + "?");
		Matcher mat = pat.matcher(commandPath);

		if (!mat.matches()) {
			throw new UnvalidPathException(commandPath);
		}
	}
}
