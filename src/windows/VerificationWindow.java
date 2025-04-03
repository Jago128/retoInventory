package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.LoginController;

/* VERIFICATION WINDOW  
 * Go to->(*close*)
 * Back to->(ProductWindow/ComponentWindow/BrandWindow)*/
public class VerificationWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitle;
	private JLabel lblMensaje;
	private JButton btnClose;
	private JButton btnSubmit;
	private JTextField textVerification;

	/**[WINDOW CREATION]**/

	public VerificationWindow(JDialog parent, LoginController cont) {		
		super(parent, true); // Blocks the father window
		this.cont = cont;

		// Window		
		setTitle("Verify action");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		lblTitle = new JLabel("Insert the code " + "0000" + " to verify the action");
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

	/**[METHODS]**/

	

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Verifies the code 
		if (e.getSource() == btnSubmit) {
			this.dispose();
		} else {
			lblMensaje.setText("Incorrect code");
			lblTitle.setText("Insert the code " + "0000" + " to verify the action");
		}
	}
}
