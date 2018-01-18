package client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import test.Client;

public class EstablishConnectionSupport {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private SSLSocket clientSocket;
	private SSLSocketFactory sslsocketfactory;
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

	public EstablishConnectionSupport(String host, int port) throws IOException {
		connectToServer(host, port);
		getStreams();
	}

	private void connectToServer(String host, int port) throws UnknownHostException, IOException {
		sslsocketfactory  = (SSLSocketFactory) SSLSocketFactory.getDefault();
		clientSocket = (SSLSocket) sslsocketfactory.createSocket(host, port);
		clientSocket.setEnabledCipherSuites(sslsocketfactory.getSupportedCipherSuites()); 
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
