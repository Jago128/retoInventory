package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import model.*;
import controller.LoginController;

/* MAIN MENU WINDOW 
 * Go to->(MenuWindow, SignInWindow)
 * Back to->(*close*)*/
public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCodU;
	private JPasswordField passwordPsw;
	private JButton btnLogIn, btnSignIn, btnClose;
	private JLabel lblMesageUp, lblMessageDown;
	private LoginController cont;

	/**[WINDOW CREATION]**/

	public MainWindow(LoginController cont) {
		this.cont = cont;

		// Window
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setBackground(Color.WHITE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		JLabel lblUser = new JLabel("USER");
		lblUser.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUser.setBounds(40, 83, 177, 33);
		contentPane.add(lblUser);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPassword.setBounds(40, 134, 177, 33);
		contentPane.add(lblPassword);

		// Text fields
		textCodU = new JTextField();
		textCodU.setBounds(227, 85, 168, 33);
		contentPane.add(textCodU);
		textCodU.setColumns(10);

		passwordPsw = new JPasswordField();
		passwordPsw.setBounds(227, 136, 168, 33);
		contentPane.add(passwordPsw);

		// Messages
		lblMesageUp = new JLabel("");
		lblMesageUp.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMesageUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblMesageUp.setBounds(10, 10, 416, 33);
		contentPane.add(lblMesageUp);

		lblMessageDown = new JLabel("");
		lblMessageDown.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessageDown.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMessageDown.setBounds(10, 40, 416, 33);
		contentPane.add(lblMessageDown);

		// Buttons
		btnLogIn = new JButton("LOG-IN");
		btnLogIn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnLogIn.setBounds(40, 203, 111, 33);
		contentPane.add(btnLogIn);

		btnSignIn = new JButton("SIGN-IN");
		btnSignIn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnSignIn.setBounds(284, 203, 111, 33);
		contentPane.add(btnSignIn);

		btnClose = new JButton("CLOSE");
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnClose.setBounds(5, 5, 80, 21);
		contentPane.add(btnClose);

		// Adding action listener
		btnLogIn.addActionListener(this);
		btnSignIn.addActionListener(this);
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

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Verifies if the user exist to log in
		if (e.getSource() == btnLogIn) {
			User user = new User(textCodU.getText(), new String(passwordPsw.getPassword()));
			if (cont.verifyUser(user)) { // Verifies the user exists
				setTextColor(textCodU, true);
				if (cont.verifyUserPassword(user)) { // Verifies the password matches
					user = cont.getUser(user); // Obtains all the data of the user
					setLabelColor(lblMesageUp, true);
					setTextColor(passwordPsw, true);
					lblMesageUp.setText("Welcome "+textCodU.getText());
					lblMessageDown.setText("");
					JOptionPane.showMessageDialog(null, "Welcome "+textCodU.getText()); // Pop-Up Message
					MenuWindow menu = new MenuWindow(cont, user);
					menu.setVisible(true);
					this.dispose();
				} else { // If the password doesn't match warns the user with a red message
					setLabelColor(lblMesageUp, false);
					setTextColor(passwordPsw, false);
					lblMesageUp.setText("Incorrect password.");
				}
			} else { // If the user doesn't exist warns the user with a red message
				setLabelColor(lblMesageUp, false);
				setTextColor(textCodU, false);
				lblMesageUp.setText("The user does not exist.");
				lblMessageDown.setText("To register go to Log-In.");
			}
		}
		// Opens a new window to log-in for the new user to register
		if (e.getSource() == btnSignIn) {
			SignInWindow signIn = new SignInWindow(cont);
			signIn.setVisible(true);
			this.dispose();
		}
	}
}
