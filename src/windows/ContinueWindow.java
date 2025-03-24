package windows;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import controller.LoginController;

public class ContinueWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitle;
	private JButton btnYes, btnNo;
	private String name;
	
	public ContinueWindow(JDialog parent, LoginController cont, String name) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.name = name;

		// Window
		setTitle("Verify action");
		setBounds(100, 100, 450, 194);
		getContentPane().setLayout(null);

		// Titles
		lblTitle = new JLabel("Do you want to keep shoping?");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 32, 416, 37);
		getContentPane().add(lblTitle);

		// Labels
		JLabel lblCodUser = new JLabel(name);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(345, 5, 81, 19);
		getContentPane().add(lblCodUser);
		
		// Buttons
		btnYes = new JButton("YES");
		btnYes.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnYes.setBounds(20, 79, 170, 57);
		getContentPane().add(btnYes);
		
		btnNo = new JButton("NO");
		btnNo.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNo.setBounds(256, 79, 170, 57);
		getContentPane().add(btnNo);

		// Adding action listener
		btnYes.addActionListener(this);
		btnNo.addActionListener(this);
	}

	// Verifies the code is correct
	public boolean verifyCode(int cod, String ver) {
		boolean correct;
		String codString = Integer.toString(cod);

		if (ver.equals(codString)) {
			correct = true;
		} else {
			correct = false;
		}
		return correct;
	}

	// Based on the type deletes a Product or a Component
	public void deletion(LoginController cont, String name, boolean type) {
		if (type) { // true = Product | false = Component
			cont.deleteProd(name);
		} else {
			cont.deleteComp(name);
		}
	}

	// Action performer
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnYes) {
			JFrame parent = (JFrame)this.getParent(); // Obtains the parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		} 
		if (e.getSource() == btnNo) {
			JFrame parent = (JFrame)this.getParent(); // Obtains the parent window			
			parent.dispose(); // Closes the parent window
			this.dispose();
		} 
	}
}
