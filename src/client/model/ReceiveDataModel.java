package client.model;

import java.io.ObjectInputStream;
import java.util.logging.Logger;

import server.Server;
import server.beans.Message;


public class ReceiveDataModel extends AbstractModel implements Runnable {

	// Attributes 
	private EstablishConnectionSupport connectionModel;
	private ObjectInputStream in;
	private final static Logger LOGGER = Logger.getLogger(ReceiveDataModel.class.getName());
	
	
	// Constructor 
	public ReceiveDataModel(EstablishConnectionSupport connectionModel) {
		this.connectionModel = connectionModel;
		this.in = connectionModel.getIn();
	}
	
	
	/*
	 *  Opis: w�tek ten b�dzie zajmowa� si� obs�ug� wiadomo�ci przychodz�cych 
	 *  do klienta. Klient mo�e otrzyma� r�ne rodzaje wiadomo�ci i na tej podstawie nale�y podj�� odpowiednie 
	 *  kroki - tym zajmie si� controller. W�tek ten ma tylko odebra� wiadomo�c i przes�a� j� do kontrolera 
	 *  gdzie nast�pi odpowiednia obr�bka danych. 
	 */
	@Override
	public void run() {
		
		do{
			try {
				Message message = (Message) in.readObject();                               // Blokowanie na czas nadejscia wiadomosci
				LOGGER.info("Recived new message " + message.getMessage() + " from " + message.getNickFrom() + " to " + message.getNickTo());
				this.firePropertyChange("newMessageFromReceiveDataModel", null, message);  // Notify controller about new message 
				
			} catch (Exception e){ 
				e.printStackTrace();
				// In case of error close connections
				connectionModel.closeConnection();
				break;                                                                     // Po bledzie opusc petle 
			}
		}while (true);
	}
}
