package resolution.logger;

public class LogMessage {
	
	private LogMessageType messageType;
	private String messageContent;
	
	public LogMessage(LogMessageType messageType, String messageContent) {
		super();
		this.messageType = messageType;
		this.messageContent = messageContent;
	}
	
	public LogMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(LogMessageType messageType) {
		this.messageType = messageType;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	@Override
	public String toString() {
		return messageType.toString().toUpperCase() + " : " + messageContent;
	}
	
}
