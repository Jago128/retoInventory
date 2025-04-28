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
	private JLabel lblMessage;
	private JTextField textUserCod, textName;
	private JPasswordField password, passwordConfirmation;
	private JButton btnClose, btnSubmit;
	private JPanel panelLeft, panelRight;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public SignInWindow(LoginController cont) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;

		// Window
		setTitle("MEDIAMARTA: Register");
		setBounds(100, 100, 660, 400);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size
		getContentPane().setLayout(null);

		// Left Panel
		panelLeft = new JPanel();
		panelLeft.setBounds(0, 0, 242, 363);
		panelLeft.setBackground(Color.RED);
		panelLeft.setLayout(null);
		getContentPane().add(panelLeft);

		// Buttons
		btnClose = new JButton("CLOSE");
		btnClose.setForeground(Color.WHITE);
		btnClose.setFont(new Font("Times New Roman", Font.BOLD, 10));
		btnClose.setBackground(Color.BLACK);
		btnClose.setBounds(10, 11, 80, 21);
		panelLeft.add(btnClose);
		
		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(SignInWindow.class.getResource("/img/MediaMartaLogoW.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 100));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(216, 11, 50, 50);
		logo.setBounds(10, 69, 217, 217);
		panelLeft.add(logo);		

		// Right Panel
		panelRight = new JPanel();
		panelRight.setBounds(241, 0, 405, 363);
		panelRight.setBackground(Color.WHITE);
		panelRight.setLayout(null);		
		getContentPane().add(panelRight);

		// Titles
		JLabel lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setBounds(0, 21, 405, 46);
		panelRight.add(lblMediaMarta);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.BOLD, 40));

		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(0, 56, 393, 30);
		panelRight.add(lblRegister);
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Times New Roman", Font.PLAIN, 25));

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds(15, 106, 193, 30);
		panelRight.add(lblUsername);
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblName = new JLabel("NAME");
		lblName.setBounds(15, 147, 193, 30);
		panelRight.add(lblName);
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(15, 186, 193, 30);
		panelRight.add(lblPassword);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblPasswordConf = new JLabel("CONFIRM PASSWORD");
		lblPasswordConf.setBounds(15, 227, 193, 30);
		panelRight.add(lblPasswordConf);
		lblPasswordConf.setFont(new Font("Times New Roman", Font.BOLD, 15));

		lblMessage = new JLabel("");
		lblMessage.setBounds(15, 268, 374, 34);
		panelRight.add(lblMessage);
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		// Text fields
		textUserCod = new JTextField();
		textUserCod.setBounds(218, 105, 166, 30);
		panelRight.add(textUserCod);
		textUserCod.setColumns(10);

		textName = new JTextField();
		textName.setBounds(218, 146, 166, 30);
		panelRight.add(textName);
		textName.setColumns(10);

		password = new JPasswordField();
		password.setBounds(218, 186, 166, 30);
		panelRight.add(password);

		passwordConfirmation = new JPasswordField();
		passwordConfirmation.setBounds(218, 227, 166, 30);
		panelRight.add(passwordConfirmation);

		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBackground(Color.BLACK);
		btnSubmit.setBounds(121, 313, 147, 34);
		panelRight.add(btnSubmit);
		btnSubmit.setFont(new Font("Times New Roman", Font.BOLD, 15));

		// Adding action listener
		btnClose.addActionListener(this);
		btnSubmit.addActionListener(this);
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
			MainWindow main = new MainWindow(cont);
			main.setVisible(true);			
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

