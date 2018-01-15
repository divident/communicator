package test;

import java.io.IOException;

import server.Server;


public class DeployServer {

	public static void main(String[] args) {

		try {
			Server server = new Server(8389, 20);
			server.runServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
