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
	private JButton btnLogIn, btnSignIn;
	private JLabel lblMesageUp, lblMessageDown;
	private LoginController cont;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public MainWindow(LoginController cont) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;

		// Window
		setTitle("MEDIAMARTA: Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 400);
		getContentPane().setBackground(Color.WHITE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false); // Blocks the window so it can't be modified the size
		contentPane.setLayout(null);

		// Left Panel
		JPanel panelLeft = new JPanel();
		panelLeft.setBounds(0, 0, 242, 363);
		panelLeft.setBackground(Color.RED);
		panelLeft.setLayout(null);
		contentPane.add(panelLeft);
		
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
		JPanel panelRight = new JPanel();
		panelRight.setBounds(241, 0, 405, 363);
		panelRight.setBackground(Color.WHITE);
		panelRight.setLayout(null);
		contentPane.add(panelRight);
		
		// Titles
		JLabel lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setBounds(0, 21, 405, 46);
		panelRight.add(lblMediaMarta);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.BOLD, 40));
		
		JLabel lblWelcomeTo = new JLabel("Login");
		lblWelcomeTo.setBackground(Color.BLACK);
		lblWelcomeTo.setBounds(0, 56, 405, 30);
		panelRight.add(lblWelcomeTo);
		lblWelcomeTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeTo.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		
		JLabel lblUser = new JLabel("USER");
		lblUser.setBounds(21, 160, 177, 33);
		panelRight.add(lblUser);
		lblUser.setFont(new Font("Times New Roman", Font.BOLD, 20));

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(21, 204, 177, 33);
		panelRight.add(lblPassword);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));

		// Text fields
		textCodU = new JTextField();
		textCodU.setBounds(205, 160, 168, 33);
		panelRight.add(textCodU);
		textCodU.setColumns(10);

		passwordPsw = new JPasswordField();
		passwordPsw.setBounds(205, 206, 168, 33);
		panelRight.add(passwordPsw);

		// Messages
		lblMesageUp = new JLabel("");
		lblMesageUp.setBounds(0, 94, 393, 33);
		panelRight.add(lblMesageUp);
		lblMesageUp.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMesageUp.setHorizontalAlignment(SwingConstants.CENTER);

		// Buttons
		btnLogIn = new JButton("LOG-IN");
		btnLogIn.setForeground(Color.WHITE);
		btnLogIn.setBackground(Color.BLACK);
		btnLogIn.setBounds(75, 314, 111, 33);
		panelRight.add(btnLogIn);
		btnLogIn.setFont(new Font("Times New Roman", Font.BOLD, 15));

		btnSignIn = new JButton("SIGN-IN");
		btnSignIn.setForeground(Color.WHITE);
		btnSignIn.setBackground(Color.BLACK);
		btnSignIn.setBounds(222, 314, 111, 33);
		panelRight.add(btnSignIn);
		btnSignIn.setFont(new Font("Times New Roman", Font.BOLD, 15));

		lblMessageDown = new JLabel("");
		lblMessageDown.setBounds(0, 264, 393, 33);
		panelRight.add(lblMessageDown);
		lblMessageDown.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessageDown.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		// Adding action listener
		btnLogIn.addActionListener(this);
		btnSignIn.addActionListener(this);
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
