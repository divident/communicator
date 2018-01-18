package test;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import server.Server;


public class ChatServer {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
	public static void main(String[] args) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("log/server%g.log", 5242880, 5, true);
			LOGGER.addHandler(fileHandler);
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
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
