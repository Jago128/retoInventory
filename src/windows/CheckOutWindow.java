package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import controller.LoginController;
import model.User;

/* CHECK OUT WINDOW 
 * Go to->(*close*)
 * Back to->(ProductWindow/ComponentWindow/BrandWindow) */
public class CheckOutWindow extends JDialog implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblItemName, lblPrice;
	private JButton btnClose, btnSubmit;
	private JSpinner spinner;
	private User user;
	private String name;
	private boolean type; // true = Product | false = Component

	/**[WINDOW CREATION]**/

	public CheckOutWindow(JDialog parent, LoginController cont) {
		super(parent, true); // Blocks the father window
		this.cont = cont;

		// Window
		setTitle("MEDIAMARTA: Check-out");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		/* Spinner (Numeric value)
		 * It needs to be created before because the labels need the value of it */
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 9999, 1); // Default, Min, Max, Increment
		spinner = new JSpinner(sm);
		spinner.setBounds(214, 111, 187, 34);
		getContentPane().add(spinner);

		// Titles
		JLabel item = new JLabel("Item");
		item.setHorizontalAlignment(SwingConstants.CENTER);
		item.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		item.setBounds(25, 66, 187, 34);
		getContentPane().add(item);

		JLabel quantity = new JLabel("Quantity");
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		quantity.setBounds(214, 66, 187, 34);
		getContentPane().add(quantity);

		JLabel subtotal = new JLabel("Subtotal: ");
		subtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		subtotal.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		subtotal.setBounds(25, 156, 187, 34);
		getContentPane().add(subtotal);

		// Labels
		JLabel lblCodUser = new JLabel("Username");
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		lblItemName = new JLabel(name);
		lblItemName.setBackground(new Color(255, 255, 255));
		lblItemName.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblItemName.setBounds(25, 111, 187, 34);
		getContentPane().add(lblItemName);

		lblPrice = new JLabel("Price" + "€");
		lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrice.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPrice.setBounds(214, 156, 187, 34);
		getContentPane().add(lblPrice);

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
		spinner.addChangeListener(this);
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	

	/**[ACTION PERFORMER & CHANGE LISTENER]**/

	// Action performer
	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		
		// Calls the method that ejecutes the action on the DataBase
		if (e.getSource() == btnSubmit) {			
			ContinueWindow next = new ContinueWindow(this);
			next.setVisible(true);
		}
	}

	// Change listener
	@Override
	public void stateChanged(ChangeEvent e) {
		// Closes the window
		if (e.getSource() == spinner) {
			lblPrice.setText("Price"+"€");
		}
	}
}