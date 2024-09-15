package cmdEmulationApp.exceptions;

public class UnexpectedTokenException extends Exception{
	private String unexpectedToken;

	public UnexpectedTokenException(String unexpectedToken) {
		this.unexpectedToken = unexpectedToken;
	}

	public String toString() {
		return "bash: syntax error near unexpected token `" + this.unexpectedToken + "'";
	}
}
