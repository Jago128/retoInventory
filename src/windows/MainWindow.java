package windows;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.User;

import java.awt.event.ActionListener;

// MAIN MENU WINDOW 
// Go to->(MenuWindow, SignInWindow)
// Back to->(*close*)
public class MainWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCodU;
	private JPasswordField passwordPsw;
	private JButton btnLogIn, btnSignIn, btnClose;
	private JLabel lblMesageUp, lblMessageDown;
	private LoginController cont;
	private User user;

	public MainWindow(LoginController controlador) {
		setTitle("LOGIN");
		this.cont=controlador;		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUser = new JLabel("USER");
		lblUser.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUser.setBounds(40, 83, 177, 33);
		contentPane.add(lblUser);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPassword.setBounds(40, 134, 177, 33);
		contentPane.add(lblPassword);
		
		textCodU = new JTextField();
		textCodU.setBounds(227, 85, 168, 33);
		contentPane.add(textCodU);
		textCodU.setColumns(10);

		passwordPsw = new JPasswordField();
		passwordPsw.setBounds(227, 136, 168, 33);
		contentPane.add(passwordPsw);

		btnLogIn = new JButton("LOG-IN");
		btnLogIn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnLogIn.setBounds(40, 203, 111, 33);
		contentPane.add(btnLogIn);
		
		btnSignIn = new JButton("SIGN-IN");
		btnSignIn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnSignIn.setBounds(284, 203, 111, 33);
		contentPane.add(btnSignIn);
			
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
		
		btnClose = new JButton("CLOSE");
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnClose.setBounds(369, 246, 67, 21);
		contentPane.add(btnClose);
		
		btnLogIn.addActionListener(this);
		btnSignIn.addActionListener(this);
		btnClose.addActionListener(this);
	}

	// Verifies the type of the user
	public boolean verifyUserType(User user, boolean admin) {
		if(cont.verifyUserType(user)) { // If is admin it will be true
			admin=true;
		}else { // If its not it will be false
			admin=false;
		}
		return admin;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
		boolean admin=false;
		
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		if (e.getSource()==btnLogIn) {
			User user = new User (textCodU.getText(),new String(passwordPsw.getPassword()));
			if (cont.verifyUserPassword(user)) {
				lblMesageUp.setText("Welcome "+textCodU.getText());
				admin=verifyUserType(user, admin);
				MenuWindow menu=new  MenuWindow(admin, cont); // The admin variable is sent to show or not certain option in the next windows
				menu.setVisible(true);
				dispose();
			}else {
				lblMesageUp.setText("User not found.");
				lblMessageDown.setText("To register go to Log-In.");
			}
		}
		if (e.getSource()==btnSignIn) {
			SignInWindow signIn=new  SignInWindow(cont);
			signIn.setVisible(true);
			dispose();
		}
	}
}
