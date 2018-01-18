package client.controller;

@SuppressWarnings("serial")
public class EmptyMessageException extends Exception {
	public EmptyMessageException(String message) {
		super(message);
	}

	public EmptyMessageException() {
		super();
	}
}
