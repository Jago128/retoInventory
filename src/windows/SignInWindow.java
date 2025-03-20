package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;
import model.User;


// CREATE NEW USER WINDOW 
// Go to->(MenuWindow)
// Back to->(MainWindow)
public class SignInWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitulo;
	private JButton btnClose,btnSubmit;
	private JTextField textUserCod, textName;
	private JPasswordField password;
	private JPasswordField passwordConfirmation;
	private JLabel lblMensaje;

	public SignInWindow(JFrame parent, LoginController cont) {
		super(parent,true); 
		this.cont=cont;

		setTitle("REGISTER NEW USER");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblTitulo = new JLabel("Register");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTitulo.setBounds(10, 10, 416, 43);
		getContentPane().add(lblTitulo);

		JLabel lblNewLabel = new JLabel("USERNAME");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lblNewLabel.setBounds(66, 63, 147, 19);
		getContentPane().add(lblNewLabel);

		JLabel Contarseña = new JLabel("NAME");
		Contarseña.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		Contarseña.setBounds(66, 93, 147, 19);
		getContentPane().add(Contarseña);

		JLabel lblNuevaContrasea = new JLabel("PASSWORD");
		lblNuevaContrasea.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lblNuevaContrasea.setBounds(66, 121, 147, 19);
		getContentPane().add(lblNuevaContrasea);

		JLabel lblConfirmarContrasea = new JLabel("CONFIRM PASSWORD");
		lblConfirmarContrasea.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		lblConfirmarContrasea.setBounds(66, 150, 147, 19);
		getContentPane().add(lblConfirmarContrasea);

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

		lblMensaje = new JLabel("");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblMensaje.setBounds(66, 179, 313, 34);
		getContentPane().add(lblMensaje);

		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnSubmit.setBounds(135, 219, 147, 34);
		getContentPane().add(btnSubmit);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(356, 242, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane ().add(btnClose);


		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);

	}

	public boolean verifyPassword (String password, String passwordConf) {
		boolean correcta;

		if(password.equals(passwordConf)) {
			correcta=true;
		}else {
			correcta=false;
		}	
		return correcta;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		if (e.getSource()==btnSubmit) {
			User user = new User (textUserCod.getText(), textName.getText(), new String(password.getPassword()));
			if (!cont.verifyUser(user)) { //Comprueba que el usuario exista
				textUserCod.setBackground(new Color(255, 255, 255));
				if(verifyPassword(new String(password.getPassword()), new String(passwordConfirmation.getPassword()))) { //Comprueba que la contraseña nueva sea igual en ambos campos
					passwordConfirmation.setBackground(new Color(255, 255, 255));
					user.setPassword(new String(password.getPassword())); //Cambia la contraseña del usuario
					cont.registerUser(user); //Lo pasa a la base de datos
					lblMensaje.setText("User "+user.getCodU()+" registered correctly");
				}else {
					lblMensaje.setText("The password must be equal in both parts");
					password.setBackground(new Color(250, 128, 114));
					passwordConfirmation.setBackground(new Color(250, 128, 114));
				}
			}else {
				lblMensaje.setText("User with that code already exists");
				textUserCod.setBackground(new Color(250, 128, 114));
			}
		}
	}
}
