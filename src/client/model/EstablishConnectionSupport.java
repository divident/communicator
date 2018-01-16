package client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import server.Server;

public class EstablishConnectionSupport {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket clientSocket;
	private final static Logger LOGGER = Logger.getLogger(EstablishConnectionSupport.class.getName());

	public EstablishConnectionSupport(String host, int port) throws IOException {
		connectToServer(host, port);
		getStreams();
	}

	private void connectToServer(String host, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(host, port);
	}

	private void getStreams() throws IOException {
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
	}

	public void closeConnection() {
		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException ioException) {
			LOGGER.warning("Error while closing connection");
		}
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}
}
