package cmdEmulationApp.constants;

public enum Commands {
	CAT(
		"CAT",
		"cat: cat [-bEn] [args...] [-] [>] [args...]\n",
		"\tat, which is short for concatenate, is one of the most commonly used commands in Linux and other Unix-like operating systems.\n\n\tThe cat command allows us to create single or multiple files, view file inclusions, concatenate files and redirect output in a terminal or file.\n\tIn this article, we will show you some handy uses of the cat command and examples of it in Linux.\n"),
	LS(
		"LS",
		"ls: ls [-aQr] [args...]\n",
		"\tThe ls command lists the files in your current directory (ls is short for \"list\").\n\n\tTry it now by typing ls, then hitting <enter>.\n"),
	EXIT(
		"EXIT",
		"exit: exit []\n",
		"\tExit the shell.\n\n\tExits the shell with a status of N. If N is omitted, the exit status\n\tis that of the last command executed.\n"),
	HELP(
		"HELP",
		"help: help [] [pattern ...]\n",
		"\tDisplay information about builtin commands.\n\n\tDisplays brief summaries of builtin commands.  If PATTERN is\n\tspecified, gives detailed help on all commands matching PATTERN,\n\totherwise the list of help topics is printed.\n");

	public String name;
	public String schema;
	public String description;

	Commands(String name, String schema, String description) {
		this.name = name;
		this.schema = schema;
		this.description = description;
	}
}
