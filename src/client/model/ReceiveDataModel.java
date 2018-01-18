package client.model;

import java.io.ObjectInputStream;
import java.util.logging.Logger;

import server.beans.Message;
import test.Client;


public class ReceiveDataModel extends AbstractModel implements Runnable {

	private EstablishConnectionSupport connectionModel;
	private ObjectInputStream in;
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
	
	public ReceiveDataModel(EstablishConnectionSupport connectionModel) {
		this.connectionModel = connectionModel;
		this.in = connectionModel.getIn();
	}
	

	@Override
	public void run() {
		
		do{
			try {
				Message message = (Message) in.readObject();                             
				LOGGER.info("Recived new message " + message.getMessage() + " from " + message.getNickFrom() + " to " + message.getNickTo());
				this.firePropertyChange("newMessageFromReceiveDataModel", null, message);  
				
			} catch (Exception e){ 
				//e.printStackTrace();
				connectionModel.closeConnection();
				break;                                                                   
			}
		}while (true);
	}
}
