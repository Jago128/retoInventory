package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

import controller.LoginController;

// VERIFICATION WINDOW  
// Go to->(*close*)
// Back to->(CheckOutWindow, NewItemWindow, VerificationWindow)
public class VerificationWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitle, lblMensaje;
	private JButton btnClose, btnSubmit;
	private JTextField textVerification;
	private int cod;
	private String name;
	private boolean type; // true = Product | false = Component

	public VerificationWindow(JDialog parent, LoginController cont, String name, boolean type) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.name = name;
		this.type = type;

		// Window
		setTitle("Verify action");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		// Titles
		lblTitle = new JLabel("Insert the code " + generateCode() + " to verify the action");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 32, 416, 37);
		getContentPane().add(lblTitle);

		// Messages
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(new Color(0, 0, 0));
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMensaje.setBounds(10, 200, 416, 37);
		getContentPane().add(lblMensaje);

		// Text fields
		textVerification = new JTextField();
		textVerification.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		textVerification.setBounds(85, 80, 259, 61);
		getContentPane().add(textVerification);
		textVerification.setColumns(10);

		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnSubmit.setBounds(157, 152, 117, 37);
		getContentPane().add(btnSubmit);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);

		// Adding action listener
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}

	// Generates a random code from 1 to 4 numbers
	public int generateCode() {
		Random random = new Random();
		cod = random.nextInt(9999 - 0 + 1) + 0;
		return cod;
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
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		String ver;
		ver = textVerification.getText();
		if (e.getSource() == btnSubmit && verifyCode(cod, ver)) {
			deletion(cont, name, type);
			this.dispose();
		} else {
			lblMensaje.setText("Incorrect code");
			lblTitle.setText("Insert the code " + generateCode() + " to verify the action");
		}
	}
}
