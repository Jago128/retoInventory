package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.LoginController;
import model.User;

// CREATE NEW USER WINDOW 
// Go to->(MenuWindow)
// Back to->(MainWindow)
public class SignInWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitulo, lblMensaje;
	private JTextField textUserCod, textName;
	private JPasswordField password, passwordConfirmation;	
	private JButton btnClose, btnSubmit;

	/**[WINDOW CREATION]*/
	
	public SignInWindow(LoginController cont) {
		this.cont = cont;

		// Window
		setTitle("REGISTER NEW USER");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		// Titles
		lblTitulo = new JLabel("Register");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTitulo.setBounds(10, 10, 416, 43);
		getContentPane().add(lblTitulo);

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lblUsername.setBounds(66, 63, 147, 19);
		getContentPane().add(lblUsername);

		JLabel lblName = new JLabel("NAME");
		lblName.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lblName.setBounds(66, 93, 147, 19);
		getContentPane().add(lblName);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lblPassword.setBounds(66, 121, 147, 19);
		getContentPane().add(lblPassword);

		JLabel lblPasswordConf = new JLabel("CONFIRM PASSWORD");
		lblPasswordConf.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lblPasswordConf.setBounds(66, 150, 147, 19);
		getContentPane().add(lblPasswordConf);

		lblMensaje = new JLabel("");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblMensaje.setBounds(66, 179, 313, 34);
		getContentPane().add(lblMensaje);

		// Text fields
		textUserCod = new JTextField();
		textUserCod.setColumns(10);
		textUserCod.setBounds(213, 62, 166, 19);
		getContentPane().add(textUserCod);

		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(213, 92, 166, 19);
		getContentPane().add(textName);

		password = new JPasswordField();
		password.setBounds(213, 121, 166, 19);
		getContentPane().add(password);

		passwordConfirmation = new JPasswordField();
		passwordConfirmation.setBounds(213, 150, 166, 19);
		getContentPane().add(passwordConfirmation);

		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnSubmit.setBounds(135, 219, 147, 34);
		getContentPane().add(btnSubmit);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);

		// Adding action listener
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]*/	
	
	// Verifying the password is equal in both text fields
	public boolean verifyPassword(String password, String passwordConf) {
		boolean correct;

		if (password.equals(passwordConf)) {
			correct = true;
		} else {
			correct = false;
		}
		return correct;
	}

	/**[ACTION PERFORMER]*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Verifies the text fields and creates the user
		if (e.getSource() == btnSubmit) {
			User user = new User(textUserCod.getText(), textName.getText()); // Creates a user
			if (!cont.verifyUser(user)) { // Verifies if a user with the same name exists
				textUserCod.setBackground(new Color(255, 255, 255));
				if (verifyPassword(new String(password.getPassword()), new String(passwordConfirmation.getPassword()))) { // Verifies if the password is equal in both text fields
					passwordConfirmation.setBackground(new Color(255, 255, 255));
					user.setPassword(new String(password.getPassword())); // Modifies the password					 
					cont.registerUser(user); // Registers the user
					JOptionPane.showMessageDialog(null, "User registered correctly");
					MenuWindow menu = new MenuWindow(user, cont);
					menu.setVisible(true);
					this.dispose();
				} else {
					lblMensaje.setText("The password must be equal in both parts");
					password.setBackground(new Color(250, 128, 114));
					passwordConfirmation.setBackground(new Color(250, 128, 114));
				}
			} else {
				lblMensaje.setText("User with that code already exists");
				textUserCod.setBackground(new Color(250, 128, 114));
			}
		}
	}
}
