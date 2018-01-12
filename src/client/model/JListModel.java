package client.model;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import server.beans.Message;

public class JListModel extends AbstractModel{
	private Set<String> usersList;
	private final static Logger LOGGER = Logger.getLogger(JListModel.class.getName());
	public JListModel() {
		usersList = new HashSet<String>();
	}
	
	public void addNewUser(Message message) {
		String user = message.getNickFrom();
		usersList.add(user);
		LOGGER.info("Add new user to list " +  user);
		this.firePropertyChange("addNewUserToJList", null, user);
	}
	
	public void removeUser(Message message) {
		LOGGER.info("Removing user from list");
		String user = message.getNickFrom();
		usersList.remove(user);
		this.firePropertyChange("removeUserFromJList", null, user);
	}
	
	public void isUserAtTheList(String userName){                                   
		if (!usersList.contains(userName))
			this.firePropertyChange("", userName, null);
	}
}
