package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
	
	private final ServerSocket serverSocket;
	private final ExecutorService clientsThreads;
	
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
	
	
	/**
	 * Create server on given port with 
	 * fixed number of concurrent connection
	 * 
	 * @param portNumber
	 * @param threadsAmount 
	 * @throws IOException
	 */
	public Server(int portNumber, int threadsAmount) throws IOException{
		serverSocket = new ServerSocket(portNumber);
		clientsThreads = Executors.newFixedThreadPool(threadsAmount);
	}
	
    public void runServer() throws IOException{
    	while (true){
    		LOGGER.info("Waiting for user connection...");
    		
		    Socket incommingConnection = serverSocket.accept();               // Waiting for new connection : blocked
		 	clientsThreads.submit(new ConnectionHandler(incommingConnection));   // Execute in separate thread : client thread
		 	
		 	LOGGER.info("Current users number " + ConnectionHandler.getUsersCount().get());
    	}
    }
    
	
}
