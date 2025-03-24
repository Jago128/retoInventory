package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeListener;

import controller.LoginController;
import model.User;

// CHECK OUT WINDOW 
// Go to->(*close*)
// Back to->(ProductWindow, ComponentWindow, BrandWindow)
public class CheckOutWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblItemName;
	private JButton btnClose, btnSubmit;
	private JSpinner spinner;
	private User user;
	private String name;
	private int price;
	private boolean type; // true = Product | false = Component
		
	public CheckOutWindow(JDialog parent, LoginController cont, User user, String name, double price, boolean type) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.user = user;
		this.type = type; // true = Product | false = Component

		// Window
		setTitle("MEDIAMARTA: Check-out");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		// Spinner (Numeric value)
		// It needs to be created before because the labels need the value of it
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 10, 1); // Default, Min, Max, Increment
		spinner = new JSpinner(sm);
		spinner.setBounds(214, 111, 187, 34);
		getContentPane().add(spinner);
		spinner.setValue(1);	
		// NEEDS TO BE ADDED AN LISTENER FOR WHEN THE VALUES CHANGE
		
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
		JLabel lblCodUser = new JLabel(user.getCodU());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(345, 5, 81, 19);
		getContentPane().add(lblCodUser);

		lblItemName = new JLabel(name);
		lblItemName.setBackground(new Color(255, 255, 255));
		lblItemName.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblItemName.setBounds(25, 111, 187, 34);
		getContentPane().add(lblItemName);

		JLabel lblPrice = new JLabel(calcPrice(price) + "€");
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
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}
	
	// Calculate price
	public double calcPrice(double price) {
		double total;
		total=price*(int)spinner.getValue();
		
		return total;
	}
	
	// Action performer
	@Override
	public void actionPerformed(ActionEvent e) {				
		// Closes the window
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		// 
		if (e.getSource()==btnSubmit) {			
			cont.sellAndSubstract(user.getCodU(), name, (int)spinner.getValue(), calcPrice(price), type);
			ContinueWindow next = new ContinueWindow(this, cont, user.getCodU());
			next.setVisible(true);
		}
	}
}