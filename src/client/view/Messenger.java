package client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.ClientLocale;
import client.controller.Controller;

public class Messenger extends JFrame implements ListSelectionListener {
	private final static Logger LOGGER = Logger.getLogger(Messenger.class.getName());

	private Controller controller;

	private JList<String> list;
	private DefaultListModel<String> listModel;

	private JButton chatButton;
	private JMenuBar menuBar;
	private JMenu langMenu;
	private ButtonGroup group;
	private JRadioButtonMenuItem btPl, btEn;

	public Messenger(Controller controller) {
		ClientLocale.setLocale("en", "EN");
		setController(controller);
		getController().setMessengerView(this);
		listModel = new DefaultListModel<>();
		setSize(240, 360);
		// Create the list and put it in a scroll pane.
		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);

		this.setJMenuBar(createMenuBar());

		chatButton = new JButton();
		chatButton.addActionListener(new ChatListener());

		// Create a panel that uses BoxLayout.
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.add(chatButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(listScrollPane, BorderLayout.CENTER);
		add(chatButton, BorderLayout.PAGE_END);
		chatButton.setEnabled(false);
		changeText();
		new LoginBox(controller);
	}

	class ChatListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.selectedUserFromJList(list.getSelectedValue());
		}

	}
	
	public void changeText() {
		chatButton.setText(ClientLocale.getMessage("btChat"));
		btPl.setText(ClientLocale.getMessage("plLang"));
		btEn.setText(ClientLocale.getMessage("enLang"));
		langMenu.setText(ClientLocale.getMessage("langMenu"));
	}

	public JMenuBar createMenuBar() {
		ActionListener al = new LanguageChange();
		menuBar = new JMenuBar();
		langMenu = new JMenu();
		menuBar.add(langMenu);
		group = new ButtonGroup();
		btEn = new JRadioButtonMenuItem();
		btEn.setSelected(true);
		btEn.addActionListener(al);
		group.add(btEn);
		langMenu.add(btEn);

		btPl = new JRadioButtonMenuItem();
		btPl.addActionListener(al);
		group.add(btPl);
		langMenu.add(btPl);
		return menuBar;
	}

	public void addUserToTheList(String userName) {
		LOGGER.info("Adding username " + userName);
		listModel.addElement(userName);
		if (listModel.size() > 0)
			chatButton.setEnabled(true);
	}

	public void removeUserFromList(String userName) {
		LOGGER.info("Removing user " + userName);
		listModel.removeElement(userName);
		list.repaint();
		if (listModel.size() < 0)
			chatButton.setEnabled(false);
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (list.getSelectedIndex() == -1) {
				chatButton.setEnabled(false);

			} else {
				chatButton.setEnabled(true);
			}
		}
	}

	class LanguageChange implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ex) {
			if (ex.getSource().equals(btPl)) {
				ClientLocale.setLocale("pl", "PL");
			} else {
				ClientLocale.setLocale("en", "EN");
			}
			getController().changeLanguage();
		}
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void resetList() {
		list.clearSelection();
	}
}