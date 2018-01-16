package test;

import java.util.logging.Logger;

import server.Server;


public class ChatServer {
	private final static Logger LOGGER = Logger.getLogger(ChatServer.class.getName());
	public static void main(String[] args) {

		try {
			Server server = Server.getInstance();
			server.runServer();
		} catch (Exception e) {
			if(e.getMessage() != null) {
				LOGGER.warning(e.getMessage());
			}
		}
	}
}
