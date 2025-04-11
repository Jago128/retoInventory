package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import javax.swing.*;
import controller.LoginController;
import exception.IncorrectNameFormatException;
import exception.IncorrectPasswordFormatException;
import exception.IncorrectUsernameFormatException;
import model.*;

/* CREATE NEW USER WINDOW 
 * Go to->(MenuWindow)
 * Back to->(MainWindow)*/
public class SignInWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitulo, lblMessage;
	private JTextField textUserCod, textName;
	private JPasswordField password, passwordConfirmation;
	private JButton btnClose, btnSubmit;

	/**[WINDOW CREATION]**/

	public SignInWindow(LoginController cont) {
		this.cont = cont;

		// Window
		setTitle("REGISTER NEW USER");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

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

		lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblMessage.setBounds(66, 179, 313, 34);
		getContentPane().add(lblMessage);

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

	/**[METHODS]**/

	// Sets the color of the labels true = BLACK | false = RED
	public void setLabelColor(JLabel label, boolean correct) {
		if (correct) {
			label.setForeground(Color.BLACK);
		} else {
			label.setForeground(Color.RED);
		}
	}

	// Sets the color of the text fields true = WHITE | false = RED
	public void setTextColor(JTextField field, boolean correct) {
		if (correct) {
			field.setBackground(Color.WHITE);
		} else {
			field.setBackground(new Color(250, 128, 114));
		}
	}

	// Verifies the username has the format
	public boolean usernameFormat(String userCod) {
		Pattern pattern = Pattern.compile("^(?=.*\\w).{4,20}$");
		Matcher matcher = pattern.matcher(userCod);
		return matcher.matches();
	}

	// Verifying the password has the correct format
	public boolean verifyUsernameFormat(String userCod) throws IncorrectUsernameFormatException { // EXCEPTION 1
		boolean check = usernameFormat(userCod);
		if (!check) {
			throw new IncorrectUsernameFormatException();
		}
		return check;
	}	

	// Verifies the name has the format
	public boolean nameFormat(String name) {
		Pattern pattern = Pattern.compile("^(?=.*[A-Za-z]).{2,30}$");
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

	// Verifying the password has the correct format
	public boolean verifyNameFormat(String userCod) throws IncorrectNameFormatException { // EXCEPTION 1
		boolean check = usernameFormat(userCod);
		if (!check) {
			throw new IncorrectNameFormatException();
		}
		return check;
	}

	// Verifies the password has the format
	public boolean passwordFormat(String password) {
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z]).{8,}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	// Verifying the password has the correct format
	public boolean verifyPasswordFormat(String password) throws IncorrectPasswordFormatException { // EXCEPTION 2
		boolean check = passwordFormat(password);
		if (!check) {
			throw new IncorrectPasswordFormatException();
		}
		return check;
	}

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

	// Sets the correct data of the user
	public User setUser(User user) {
		user.setCodU(textUserCod.getText());
		user.setUsername(textName.getText());
		user.setPassword(new String(password.getPassword()));
		user.setTypeU(TypeU.CLIENT);
		return user;
	}

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			MainWindow frame = new MainWindow(cont);
			frame.setVisible(true);
			this.dispose();
		}
		// Verifies the text fields and creates the user
		if (e.getSource() == btnSubmit) {
			User user = new User(textUserCod.getText(), textName.getText()); // Creates a user
			if (!cont.verifyUser(user)) { // Verifies if a user with the same name exists				
				try {
					if (verifyUsernameFormat(textUserCod.getText())) { // EXCEPTION 1 User Code Pattern						
						setTextColor(textUserCod, true);
						try {
							if (verifyNameFormat(textName.getText())) { // EXCEPTION 2 Name Pattern								
								setTextColor(textName, true);								
								try {
									if (verifyPasswordFormat(new String(password.getPassword()))) { // EXCEPTION 3 Password Pattern
										if (verifyPassword(new String(password.getPassword()), new String(passwordConfirmation.getPassword()))) { // Verifies if the password is equal in both text fields
											setTextColor(password, true);
											setTextColor(passwordConfirmation, true);
											setUser(user);
											cont.registerUser(user); // Registers the user
											lblMessage.setText("User registered correctly.");
											setLabelColor(lblMessage, true);
											JOptionPane.showMessageDialog(null, "User registered correctly.");
											MenuWindow menu = new MenuWindow(cont, user);
											menu.setVisible(true);
											this.dispose();
										} else {
											lblMessage.setText("The password must be equal in both parts.");
											setLabelColor(lblMessage, false);
											setTextColor(password, false);
											setTextColor(passwordConfirmation, false);
										}
									}
								} catch (IncorrectPasswordFormatException paswordError) { // EXCEPTION 3 Password Pattern
									setLabelColor(lblMessage, false);
									setTextColor(password, false);
									lblMessage.setText(paswordError.getMessage());
									JOptionPane.showMessageDialog(null, "Password must have at least 8 characters and contain one letter and one number.");
								}
							}
						} catch (IncorrectNameFormatException nameError) { // EXCEPTION 2 Name Pattern
							setLabelColor(lblMessage, false);
							setTextColor(textName, false);
							lblMessage.setText(nameError.getMessage());
							JOptionPane.showMessageDialog(null, "Name must have at least 2 characters and no numbers.");
						}
					}
				} catch (IncorrectUsernameFormatException userCodError) { // EXCEPTION 1 User Code Pattern
					setLabelColor(lblMessage, false);
					setTextColor(textUserCod, false);
					lblMessage.setText(userCodError.getMessage());
					JOptionPane.showMessageDialog(null, "Username must have at least 4 characters (Numbers and Letters).");
				}
			} else {
				lblMessage.setText("User with that code already exists.");
				setLabelColor(lblMessage, false);
				setTextColor(textUserCod, false);
			}
		}
	}
}

