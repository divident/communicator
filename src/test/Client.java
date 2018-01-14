package test;

import java.awt.EventQueue;
import java.util.logging.Logger;

import client.controller.Controller;
import client.model.EstablishConnectionSupport;
import client.model.JListModel;
import client.model.ReceiveDataModel;
import client.model.SendDataModel;
import client.view.Messenger;

public class Client {
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
	
	public static void main(String[] args) {
		
		final Controller controller = new Controller();

		EstablishConnectionSupport establishConnectionModel = new EstablishConnectionSupport("localhost", 8389);

		SendDataModel sendDataModel = new SendDataModel(establishConnectionModel);
		controller.addModel("sendDataModel", sendDataModel);

		ReceiveDataModel receiveDataModel = new ReceiveDataModel(establishConnectionModel);
		controller.addModel("receiveDataModel", receiveDataModel);
		Thread thread = new Thread(receiveDataModel); 
		thread.start();

		JListModel userListModel = new JListModel();
		controller.addModel("JListModel", userListModel);
		
		LOGGER.info("Starting client program");
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Messenger frame = new Messenger(controller);
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(runnable);
	}

}