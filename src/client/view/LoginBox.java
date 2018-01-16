package client.view;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import client.ClientLocale;
import client.controller.Controller;
 
@SuppressWarnings("serial")
public class LoginBox extends JDialog {
	private Controller controller;
	
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    public LoginBox(Controller controller) {
        this.controller = controller;
        this.controller.setLoginBoxView(this);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        

        lbUsername = new JLabel();
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbPassword = new JLabel();
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnLogin = new JButton();
 
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
              controller.receivedLoginData(tfUsername.getText(), new String(pfPassword.getPassword()));
            }
        });
        btnCancel = new JButton();
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
        changeText();
        setLocationRelativeTo(null);
        pack();
    	setModal(true);                                     
		setVisible(true);
        setResizable(false);
    }
    
    public void changeText() {
    	btnCancel.setText(ClientLocale.getMessage("btCancel"));
    	btnLogin.setText(ClientLocale.getMessage("btLogin"));
    	lbUsername.setText(ClientLocale.getMessage("lbUsername"));
    	lbPassword.setText(ClientLocale.getMessage("lbPassword"));
    }
    public String getUsername() {
        return tfUsername.getText().trim();
    }
 
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
 
    public void wrongNick() {
    	   JOptionPane.showMessageDialog(LoginBox.this,
                   ClientLocale.getMessage("loginError"),
                   "Login",
                   JOptionPane.ERROR_MESSAGE);
    }
    public void closeLoginBox() {
    	this.setVisible(false);
    	this.dispose();
    }
}
