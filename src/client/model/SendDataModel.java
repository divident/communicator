package client.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import server.beans.Message;

public class SendDataModel extends AbstractModel{

	private EstablishConnectionSupport connectionModel;
	private ObjectOutputStream out;
	private final static Logger LOGGER = Logger.getLogger(SendDataModel.class.getName());

	public SendDataModel(EstablishConnectionSupport connectionModel) {
		this.connectionModel = connectionModel;
		out = connectionModel.getOut();
	}
	
	public void sendData(Message msg){
		try {
			LOGGER.info("Sending message " + msg.getMessage() + " from " + msg.getNickFrom() + 
					" to " + msg.getNickTo());
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			connectionModel.closeConnection();  
		}
	}
}
