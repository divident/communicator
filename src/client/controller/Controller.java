package client.controller;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import client.model.AbstractModel;
import client.model.JListModel;
import client.model.SendDataModel;
import client.view.LoginBox;
import client.view.MessageBox;
import client.view.Messenger;
import client.view.TestTheme;
import server.Server;
import server.beans.Message;

public class Controller implements PropertyChangeListener {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	private Map<String, AbstractModel> modelsMap;
	private Map<String, MessageBox> messageBoxesMap;
	private Messenger messengerView;

	private LoginBox loginBoxView;
	private String userNick;

	public Controller() {
		modelsMap = new HashMap<String, AbstractModel>();
		messageBoxesMap = new HashMap<String, MessageBox>();
	}

	public void addMessageBoxView(String user, MessageBox viewMessageBox) {
		messageBoxesMap.put(user, viewMessageBox);
	}

	public void addModel(String name, AbstractModel model) {
		modelsMap.put(name, model);
		model.addPropertyChangeListener(this);
	}

	public void addTextToConversationPanel(String userNickRecipient, String messageText) {
		LOGGER.info("Sending message to " + userNickRecipient);
		Message message = new Message(userNickRecipient, userNick, messageText);
		MessageBox messageBox = messageBoxesMap.get(userNickRecipient);
		messageBox.eraseMessageJTextPane();

		try {
			messageBox.addNewMessage(messageText, message.getNickFrom());
		} catch (Exception e) {
			e.printStackTrace();
		}
		((SendDataModel) modelsMap.get("sendDataModel")).sendData(message);

	}

	public LoginBox getLoginBoxView() {
		return loginBoxView;
	}

	public Messenger getMessengerView() {
		return messengerView;
	}

	public String getUserNick() {
		return userNick;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

		if (propertyName.equals("newMessageFromReceiveDataModel")) {

			Message message = (Message) evt.getNewValue();
			String nickTo = message.getNickTo();
			String messageContent = message.getMessage();

			if (nickTo.equals("addNewUser")) {
				LOGGER.info("Adding new user");
				((JListModel) modelsMap.get("JListModel")).addNewUser(message);

			} else if (messageContent.equals("uniqueNick")) {
				LOGGER.info("User register successfully");
				String userName = message.getNickFrom();
				setUserNick(userName);
				getLoginBoxView().closeLoginBox();

			} else if (messageContent.equals("wrongNick")) {
				LOGGER.info("User nick is not unique");
				getLoginBoxView().wrongNick();

			} else if (messageContent.equals("removeUserFromJList")) {
				LOGGER.info("User removed from list model");
				((JListModel) modelsMap.get("JListModel")).removeUser(message);

			} else if (!nickTo.equals("empty") && !message.getNickFrom().equals("empty")) {
				LOGGER.info("Showing message from " + message.getNickFrom());
				if (messageBoxesMap.containsKey(message.getNickFrom())) {

					MessageBox messageBox = messageBoxesMap.get(message.getNickFrom());
					messageBox.setVisible(true);
					try {
						messageBox.addNewMessage(message.getMessage(), message.getNickFrom());
					} catch (Exception e) {
						LOGGER.warning("Error while sending message");
					}
				} else {
					final String text = message.getMessage();
					final String user = message.getNickFrom();

					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							MessageBox frame = new MessageBox(Controller.this, user, text);
							frame.setVisible(true);
						}
					};
					EventQueue.invokeLater(runnable);

				}
			}
		} else if (propertyName.equals("removeUserFromJList")) {
			String userName = (String) evt.getNewValue();
			LOGGER.info("User removed from list");
			getMessengerView().removeUserFromList(userName);

		} else if (propertyName.equals("addNewUserToJList")) {
			String userName = (String) evt.getNewValue();
			LOGGER.info("Adding new user to list " + userName);
			getMessengerView().addUserToTheList(userName);

		} else {

			Message message = (Message) evt.getNewValue();
			final String text = message.getMessage();
			final String user = message.getNickFrom();

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					MessageBox frame = new MessageBox(Controller.this, user, text);
					frame.setVisible(true);
				}
			};
			EventQueue.invokeLater(runnable);

		}

	}

	public void receivedLoginData(String userLoginNick, String password) {

		Message message = new Message(password, userLoginNick, "empty");
		((SendDataModel) modelsMap.get("sendDataModel")).sendData(message);
	}

	public void removeMessageBoxView(String userNick) {
		messageBoxesMap.get(userNick).turnOffWindow();
		messengerView.resetList();
		((JListModel) modelsMap.get("JListModel")).isUserAtTheList(userNick);
	}

	public void selectedUserFromJList(String username) {
		LOGGER.info("Opening chat window with " + username);
		if (!messageBoxesMap.containsKey(username)) {

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					MessageBox frame = new MessageBox(Controller.this, username);
					frame.setVisible(true);
				}
			};
			EventQueue.invokeLater(runnable);

		} else
			messageBoxesMap.get(username).setVisible(true);
	}

	public void setLoginBoxView(LoginBox loginBoxView) {
		this.loginBoxView = loginBoxView;
	}

	public void setMessengerView(Messenger messengerView) {
		this.messengerView = messengerView;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public void changeLanguage() {
		this.loginBoxView.changeText();
		for (Map.Entry<String, MessageBox> messageBox : messageBoxesMap.entrySet()) {
			messageBox.getValue().changeText();
		}
		this.messengerView.changeText();
	}

	public void changeSkin(String theme) {
		LOGGER.info("Change skin to " + theme);
		try {
			if (theme.equals("metal"))
				MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
			else if (theme.equals("ocean"))
				MetalLookAndFeel.setCurrentTheme(new OceanTheme());
			else
				MetalLookAndFeel.setCurrentTheme(new TestTheme());

			UIManager.setLookAndFeel(new MetalLookAndFeel());
			SwingUtilities.updateComponentTreeUI(this.messengerView);
			//this.messengerView.pack();
		} catch (Exception ex) {
			LOGGER.warning("Changing skin failed");
		}
	}
}
