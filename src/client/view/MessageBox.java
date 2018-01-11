package client.view;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import client.controller.Controller;
import server.Server;

@SuppressWarnings("serial")
public class MessageBox extends JFrame {
	private final static Logger LOGGER = Logger.getLogger(MessageBox.class.getName());
	// Attributes: set in the constructor 
	Controller controller;
	
	// Nazwa uzytkownika do ktorego to okno wysyla wiadomosc, pojawia sie tez w tytule // TODO : zmodyfikuj tytul
	String userWindowOwner;
	/*
	 * info: Wykorzystywane przy obs³udze przychodz¹cych wiadomosci. 
	 * Numer kazdego okna, to numer uzytkownika z ktorym prowadzony jest chat.
	 * Na podstawie tego numeru bêd¹ odnajdowane okna konwersacji, a je¿eli ich nie bêdzie, 
	 * to bêd¹ tworzone. 
	 */
	int windowNumber;  
	
	
	// Attributes: UI elements
	JTextPane conversationJTextPane;    
	JTextPane messageJTextPane;
	JButton redoButton;
	JButton settingsButton;
	JButton sendButton;
	JButton undoButton;
	
	JPanel panel;
	JPanel panel_1;
	JScrollPane scrollPane_1;
	JScrollPane scrollPane;
	
	// Used by the conversation JTextPane
	HTMLEditorKit kit = new HTMLEditorKit();
	HTMLDocument conversationContent = new HTMLDocument();
	

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
		
		// Dodanie nowej wiadomosci do okna wiadomosci 
		try{
			this.addNewMessage(receivedMessage);
		}catch(Exception ex){
			/* handle exception*/
			ex.printStackTrace();
		}
	} 
	
	
	private void setWindowsComponent(){
		
		// Set Window details 
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 11));
		setTitle("MessageBox | Rozmowa z : " + userWindowOwner);
		setBounds(100, 100, 459, 409);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(controller.getMessengerView());  
		
		// --------------------- Components not modified by the controller ---------- // 
	    panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conversation", TitledBorder.CENTER, TitledBorder.TOP, null, null));
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
		
	
		// ------------------------- Components modified by the controller ---------- |
	    // --- Conversation window
		conversationJTextPane = new JTextPane();
		conversationJTextPane.setEditable(false);
		conversationJTextPane.setEditorKit(kit);
		conversationJTextPane.setDocument(conversationContent);
		scrollPane_1.setViewportView(conversationJTextPane);
		
		// --- Message window 
		messageJTextPane = new JTextPane();
		scrollPane.setViewportView(messageJTextPane);
		messageJTextPane.setMargin(new Insets(15, 15, 5, 5));
		
		settingsButton = new JButton("");
		settingsButton.setToolTipText("Text Settings");
		settingsButton.setBounds(207, 337, 50, 23);
		getContentPane().add(settingsButton);
		
		sendButton = new JButton(" Send");
		sendButton.setBounds(328, 337, 93, 23);
		getContentPane().add(sendButton);
		
	}
	
	
	public void setActionLIsteners(){
		
		sendButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Sending message to" + userWindowOwner);

				controller.addTextToConversationPanel(userWindowOwner, messageJTextPane.getText()); 
				

			}
		});

		
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 *  TODO : dorobic okno z wyborem czcionki + ustawien
				 */
			}
		});
		
		
		// TODO :  Akcje podczas zamykania okna 
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				controller.removeMessageBoxView(userWindowOwner);
			}
		});
	}
	
	//------------------------------------------------------------------------ | 
	// Methods invoked by the controller : change views property because model has changed
	
	
	// Add a new message to the conversation JTextPane
	public void addNewMessage(String message) throws BadLocationException, IOException{
		kit.insertHTML(conversationContent, conversationContent.getLength(), message, 0, 0, null);
	}
	
	// Erase message JTextPane 
	public void eraseMessageJTextPane(){
		messageJTextPane.setText("");
	}
	
	// Set message JTextPane
	public void setMessageJTextPane(String message){
		messageJTextPane.setText(message);
	}
	
	// Unlock redo button
	public void unlockRedoButton(){
		redoButton.setEnabled(true);
	}
	
	// Lock redo button
	public void lockRedoButton(){
		redoButton.setEnabled(false);
	}
	
	// Unlock undo button
	public void unlockUndoButton(){
		undoButton.setEnabled(true);
	}
	
    // Lock undo button 
	public void lockUndoButton(){
		undoButton.setEnabled(false);
	}
	
	// Set a new font for message JTextArea
	public void setNewFont(Font newFont){
		messageJTextPane.setFont(newFont);
	}
	
	// Przywracanie okna : widocznosc 100 
	public void setVisi(){
		setVisible(true);
	}

	// Zamykanie okna : widocznosc 0 
	public void turnOffWindow() {
		setVisible(false);
		
	}
}


