package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import server.util.MaxUsersException;
import server.util.ReadXMLFile;

public class Server {

	private static int portNumber;
	static int threadsNumber;
	private ServerSocket serverSocket;
	private ExecutorService clientsThreads;
	private static Server server;

	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	private Server() throws IOException {
		ReadXMLFile rxf = new ReadXMLFile();
		portNumber = Integer.parseInt(rxf.getServerConf("port"));
		threadsNumber = Integer.parseInt(rxf.getServerConf("clients"));
		serverSocket = new ServerSocket(portNumber);
		clientsThreads = Executors.newFixedThreadPool(threadsNumber);
	}

	public static Server getInstance() throws IOException {
		if (server == null) {
			server = new Server();
		}
		return server;
	}

	public void runServer() throws Exception {
		while (true) {
			LOGGER.info("Waiting for user connection...");
			Socket incommingConnection = serverSocket.accept();
			try {
				clientsThreads.submit(new ConnectionHandler(incommingConnection));
			} catch (MaxUsersException ex) {
				LOGGER.info(ex.getMessage());
			}
			LOGGER.info("Current users number " + ConnectionHandler.getUsersCount().get());
		}
	}

}
