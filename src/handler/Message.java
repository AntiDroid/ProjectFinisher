package handler;

public class Message {

	private String messageType;
	private Object message;
	
	public Message(String mT, Object m){
		this.messageType = mT;
		this.message = m;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
