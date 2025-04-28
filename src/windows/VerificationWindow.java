package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.LoginController;
import java.util.*;

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
	private int cod;
	private String name;
	private boolean type; // true = Product | false = Component
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public VerificationWindow(JDialog parent, LoginController cont, String name, boolean type) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;
		this.name = name;
		this.type = type;

		// Window
		setTitle("Verify action");
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.BLACK);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		lblTitle = new JLabel("Insert the code "+generateCode()+" to verify the action");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(15, 22, 416, 37);
		getContentPane().add(lblTitle);

		// Messages
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMensaje.setBounds(85, 52, 259, 29);
		getContentPane().add(lblMensaje);

		// Text fields
		textVerification = new JTextField();
		textVerification.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		textVerification.setBounds(85, 80, 259, 61);
		getContentPane().add(textVerification);
		textVerification.setColumns(10);

		// Buttons
		btnClose = new JButton("CLOSE");
		btnClose.setForeground(Color.BLACK);
		btnClose.setBackground(Color.RED);
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);
		
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setForeground(Color.BLACK);
		btnSubmit.setBackground(Color.WHITE);
		btnSubmit.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnSubmit.setBounds(157, 152, 117, 37);
		getContentPane().add(btnSubmit);

		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(SignInWindow.class.getResource("/img/MediaMartaLogoR.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 100));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(110, 105, 217, 217);
		getContentPane().add(logo);

		// Adding action listener
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Generates a random code from 1 to 4 numbers
	public int generateCode() {
		Random random = new Random();
		cod = random.nextInt(9999-0+1)+0;
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

	// Refresh parent window list
	public void refreshParentList() {
		JDialog parent = (JDialog)this.getParent(); // Obtains the parent window
		if (parent instanceof ProductWindow) { // Checks the parent window type
			ProductWindow productWindow = (ProductWindow)parent; // Cast it to its type to be able to use it's methods
			productWindow.loadProductsList(); // Calls the parent method to reload the list
		} else if (parent instanceof ComponentWindow) {
			ComponentWindow productWindow = (ComponentWindow)parent;
			productWindow.loadComponentList();
		} else if (parent instanceof BrandWindow) {
			BrandWindow productWindow = (BrandWindow)parent;
			productWindow.loadList();
		} else if (parent instanceof LowStockWindow) {
			LowStockWindow lowStockWindow = (LowStockWindow)parent;
			lowStockWindow.loadList();
		}
	}

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Verifies the code
		if (e.getSource() == btnSubmit && verifyCode(cod, textVerification.getText())) {
			deletion(cont, name, type);
			refreshParentList();
			this.dispose();
		} else {
			lblMensaje.setText("Incorrect code");
			lblTitle.setText("Insert the code "+generateCode()+" to verify the action");
		}
	}
}
