import java.util.Scanner;

public class App {
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		
		final String[] COMMAND_LIST = {"ls", "cat"};
		final String PREFIX = "~";

		System.out.println("Welcome to CmdEmulatorApp!");
		System.out.println("For information enter \"help\"");
		System.out.println("To finish, enter \"exit\"");

		while (true) {
			System.out.printf("user@DESKTOP-GU0OIRP:%s$ ", PREFIX);
			String inputCommand = scan.nextLine();
			System.out.println(inputCommand);

			if (inputCommand == "exit") {
				break;
			}

		}

	}
}
