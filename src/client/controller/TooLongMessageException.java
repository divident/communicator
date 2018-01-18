package client.controller;

@SuppressWarnings("serial")
public class TooLongMessageException extends Exception{
	
	public TooLongMessageException(String message) {
		super(message);
	}
	
	public TooLongMessageException() {
		super();
	}
}
