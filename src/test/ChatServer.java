package test;

import java.util.logging.Logger;

import server.Server;


public class ChatServer {
	private final static Logger LOGGER = Logger.getLogger(ChatServer.class.getName());
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		try {
			Server server = Server.getInstance();
			server.runServer();
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage() != null) {
				LOGGER.warning(e.getMessage());
			}
		}
	}
}
