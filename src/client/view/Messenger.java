package client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.ClientLocale;
import client.controller.Controller;
import server.util.WebTimeModel;
import test.Client;

public class Messenger extends JFrame implements ListSelectionListener {
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

	private Controller controller;

	private JList<String> list;
	private DefaultListModel<String> listModel;

	private JButton chatButton;
	private JMenuBar menuBar;
	private JMenu langMenu, skinMenu;
	private ButtonGroup group, skinGroup;
	private JRadioButtonMenuItem btPl, btEn;
	private JRadioButtonMenuItem btMetal, btBlue, btOcean;
	
	public Messenger(Controller controller, String locale) {
		ClientLocale.setLocale(locale.toLowerCase(), locale.toUpperCase());
		setController(controller);
		getController().setMessengerView(this);
		listModel = new DefaultListModel<>();
		setSize(240, 360);
		
		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);

		this.setJMenuBar(createMenuBar(locale));
		setTitle(WebTimeModel.getCurrentDate());
		chatButton = new JButton();
		chatButton.addActionListener(new ChatListener());

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
		buttonPane.add(chatButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(listScrollPane, BorderLayout.CENTER);
		add(chatButton, BorderLayout.PAGE_END);
		chatButton.setEnabled(false);
		changeText();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		skinMenu.setText(ClientLocale.getMessage("skinMenu"));
		btMetal.setText(ClientLocale.getMessage("btMetal"));
		btBlue.setText(ClientLocale.getMessage("btBlue"));
		btOcean.setText(ClientLocale.getMessage("btOcean"));
	}

	public JMenuBar createMenuBar(String locale) {
		ActionListener al = new LanguageChange();
		ActionListener alSkin = new SkinChange();
		menuBar = new JMenuBar();
		langMenu = new JMenu();
		menuBar.add(langMenu);
		group = new ButtonGroup();
		btEn = new JRadioButtonMenuItem();
		btEn.addActionListener(al);
		group.add(btEn);
		langMenu.add(btEn);

		btPl = new JRadioButtonMenuItem();
		btPl.addActionListener(al);
		group.add(btPl);
		langMenu.add(btPl);
		
		if(locale.equalsIgnoreCase("en")) {
			btEn.setSelected(true);
		} else {
			btPl.setSelected(true);
		}

		skinMenu = new JMenu();
		menuBar.add(skinMenu);
		skinGroup = new ButtonGroup();
		btMetal = new JRadioButtonMenuItem();
		btBlue = new JRadioButtonMenuItem();
		btOcean = new JRadioButtonMenuItem();
		skinGroup.add(btMetal);
		skinGroup.add(btBlue);
		skinGroup.add(btOcean);
		btOcean.setSelected(true);
		skinMenu.add(btMetal);
		skinMenu.add(btOcean);
		skinMenu.add(btBlue);
		btMetal.addActionListener(alSkin);
		btOcean.addActionListener(alSkin);
		btBlue.addActionListener(alSkin);

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

	private class LanguageChange implements ActionListener {
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

	private class SkinChange implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ex) {
			String theme;
			if (ex.getSource().equals(btMetal)) {
				theme =  "metal";
			} else if(ex.getSource().equals(btOcean)) {
				theme = "ocean";
			} else {
				theme = "test";
			}
			getController().changeSkin(theme);
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