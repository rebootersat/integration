package com.siemens.becs.objects;

public class Log {

	private String messages;

	public Log(String messages) {
		super();
		this.messages = messages;
	}

	public Log() {
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "Log [messages=" + messages + "]";
	}
	
}
