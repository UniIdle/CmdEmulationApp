package cmdEmulationApp;

public enum Commands {
	CAT(new String[] {"b", "E", "n"}),
	LS(new String[] {"a", "Q", "r"}),
	EXIT(new String[] {" "}),
	HELP(new String[] {" "});

	public String[] supportedOptions;

	Commands(String[] supportedOptions) {
		this.supportedOptions = supportedOptions;
	}
}
