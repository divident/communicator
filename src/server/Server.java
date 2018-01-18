package server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import server.util.MaxUsersException;
import server.util.ReadXMLFile;

public class Server {

	private static int portNumber;
	static int threadsNumber;
	private ExecutorService clientsThreads;
	private static Server server;
	private SSLServerSocket sslServerSocket;
	private SSLServerSocketFactory sslServerSocketfactory;

	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	private Server() throws IOException {
		ReadXMLFile rxf = new ReadXMLFile();
		portNumber = Integer.parseInt(rxf.getServerConf("port"));
		threadsNumber = Integer.parseInt(rxf.getServerConf("clients"));
		clientsThreads = Executors.newFixedThreadPool(threadsNumber);
		sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		sslServerSocket  = (SSLServerSocket) sslServerSocketfactory.createServerSocket(portNumber);
		sslServerSocket.setEnabledCipherSuites(sslServerSocketfactory.getSupportedCipherSuites()); 
		
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
			SSLSocket incommingConnection = (SSLSocket) sslServerSocket.accept();
			try {
				clientsThreads.submit(new ConnectionHandler(incommingConnection));
			} catch (MaxUsersException ex) {
				ex.printStackTrace();
				LOGGER.info(ex.getMessage());
			}
			LOGGER.info("Current users number " + ConnectionHandler.getUsersCount().get());
		}
	}
}
