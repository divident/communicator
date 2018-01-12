package client.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import client.controller.Controller;

@SuppressWarnings("serial")
public class LoginBox extends JDialog {

	// Attributes
	private final Controller controller;
	// Attributes : UI
	private JTextField nickTextField;
	private JTextField passwordTextField;
	private JLabel lblNewLabel;
	private JLabel passwordLabel;

	public LoginBox(Controller controller) {
		//TODO Daæ jakiegoœ FloatLayouta zrobiæ pole na has³o
		this.controller = controller;
		this.controller.setLoginBoxView(this);

		setTitle("Login");
		setBounds(100, 100, 293, 240);
		getContentPane().setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Login : ", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(16, 16, 231, 169);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 16, 219, 147);
		panel_1.add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("Nick : ");
		lblNewLabel.setBounds(10, 24, 35, 14);
		panel.add(lblNewLabel);

		nickTextField = new JTextField();
		nickTextField.setBounds(55, 21, 143, 20);
		panel.add(nickTextField);
		nickTextField.setColumns(10);
		
		passwordLabel = new JLabel("Password : ");
		passwordLabel.setBounds(10, 54, 35, 14);
		panel.add(passwordLabel);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(55, 51, 143, 20);
		panel.add(passwordTextField);
		passwordTextField.setColumns(10);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginBox.this.controller.receivedLoginData(nickTextField.getText(), passwordTextField.getText());
			}
		});
		okButton.setBounds(69, 110, 89, 23);
		panel.add(okButton);

		setModal(true); // Blokuj pozostale okienka!
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void closeLoginBox() {
		setVisible(false);
		dispose();
	}

	/*
	 * Opis: Je¿eli podane dane by³y juz wczesniej zarejestrowane to : - wyzeruj
	 * pole tekstowe na ponowne wprowadzenie danych - ustaw etykiete nicku na kolor
	 * czerowny - sygnalizacja problemu
	 */
	public void wrongNick() {
		nickTextField.setText("");
		lblNewLabel.setForeground(Color.RED);
	}

}
