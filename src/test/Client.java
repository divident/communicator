package test;

import java.awt.EventQueue;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import client.controller.Controller;
import client.model.EstablishConnectionSupport;
import client.model.JListModel;
import client.model.ReceiveDataModel;
import client.model.SendDataModel;
import client.view.Messenger;
import server.util.ReadXMLFile;

public class Client {
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

	public static void main(String[] args) {

		final Controller controller = new Controller();
		ReadXMLFile fmx = new ReadXMLFile();
		try {
			EstablishConnectionSupport establishConnectionModel = new EstablishConnectionSupport(
					fmx.getClientConf("host"), Integer.parseInt(fmx.getServerConf("port")));

			SendDataModel sendDataModel = new SendDataModel(establishConnectionModel);
			controller.addModel("sendDataModel", sendDataModel);

			ReceiveDataModel receiveDataModel = new ReceiveDataModel(establishConnectionModel);
			controller.addModel("receiveDataModel", receiveDataModel);
			Thread thread = new Thread(receiveDataModel);
			thread.start();

			JListModel userListModel = new JListModel();
			controller.addModel("JListModel", userListModel);

			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			LOGGER.info("Starting client program");

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					Messenger frame = new Messenger(controller, fmx.getClientConf("lang"));
					frame.setVisible(true);
				}
			};
			EventQueue.invokeLater(runnable);
			
		} catch (Exception e) {
			LOGGER.warning("Failed to start client");
		}
	}

}