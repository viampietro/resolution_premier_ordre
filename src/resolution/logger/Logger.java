package resolution.logger;

import java.util.ArrayList;

/**
 * 
 * @author Vincent
 *
 * Classe servant à enregistrer les différentes étapes par lesquelles
 * va passer l'algortihme de résolution. Ceci afin de présenter à l'utilisateur
 * une trace de l'éxecution.
 */
public class Logger {
	
	private ArrayList<LogMessage> logMessages;

	public Logger() {
		logMessages = new ArrayList<>();
	}
	
	public void addLogMessage(LogMessage message) {
		logMessages.add(message);
	}
	
	public ArrayList<LogMessage> getLogMessages() {
		return logMessages;
	}

	public void setLogMessages(ArrayList<LogMessage> logMessages) {
		this.logMessages = logMessages;
	}

	
	
}
