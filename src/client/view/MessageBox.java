package client.view;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import client.ClientLocale;
import client.controller.Controller;

@SuppressWarnings("serial")
public class MessageBox extends JFrame {
	private final static Logger LOGGER = Logger.getLogger(MessageBox.class.getName());
	Controller controller;
	
	String userWindowOwner;
	int windowNumber;  
	
	JTextPane conversationJTextPane;    
	JTextPane messageJTextPane;
	JButton sendButton;
	JPanel panel;
	JPanel panel_1;
	JScrollPane scrollPane_1;
	JScrollPane scrollPane;
	

	public MessageBox(Controller controller, String userConversation) {

		this.userWindowOwner = userConversation;
		this.controller = controller;
		controller.addMessageBoxView(userConversation, this);
		 
		
		setWindowsComponent();
		setActionLIsteners();
	} 
	
	public MessageBox(Controller controller, String userConversation, String receivedMessage) {
		
		this.userWindowOwner = userConversation;
		this.controller = controller;
		controller.addMessageBoxView(userConversation, this);   
		 
		
		setWindowsComponent();
		setActionLIsteners();
		
		try{
			this.addNewMessage(receivedMessage, userConversation);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	} 
	
	
	private void setWindowsComponent(){
		
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 11));
		setBounds(100, 100, 459, 409);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(controller.getMessengerView());  
		
	    panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(16, 11, 405, 217);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 22, 385, 184);
		panel.add(scrollPane_1);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(16, 233, 405, 83);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 385, 61);
		panel_1.add(scrollPane);
		
	
		conversationJTextPane = new JTextPane();
		conversationJTextPane.setEditable(false);
		scrollPane_1.setViewportView(conversationJTextPane);
		
		messageJTextPane = new JTextPane();
		scrollPane.setViewportView(messageJTextPane);
		messageJTextPane.setMargin(new Insets(15, 15, 5, 5));
		
		sendButton = new JButton();
		sendButton.setBounds(328, 337, 93, 23);
		getContentPane().add(sendButton);
		
		changeText();
		
	}
	
	
	public void setActionLIsteners(){
		
		sendButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Sending message to " + userWindowOwner);
				controller.addTextToConversationPanel(userWindowOwner, messageJTextPane.getText()); 
			}
		});
		
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				controller.removeMessageBoxView(userWindowOwner);
			}
		});
	}
	
	public void changeText() {
		sendButton.setText(ClientLocale.getMessage("btSend"));
		setTitle(ClientLocale.getMessage("titMessageBox") + " " + userWindowOwner);
	}
	public void addNewMessage(String messageText, String userFrom) {
		conversationJTextPane.setText(conversationJTextPane.getText() + '\n' + "<" + userFrom + "> " + messageText );
	}
	public void eraseMessageJTextPane(){
		messageJTextPane.setText("");
	}
	
	public void setMessageJTextPane(String message){
		messageJTextPane.setText(message);
	}

	public void setNewFont(Font newFont){
		messageJTextPane.setFont(newFont);
	}
	
	public void setVisi(){
		setVisible(true);
	}

	public void turnOffWindow() {
		setVisible(false);
		
	}
}


