package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.LoginController;
import model.User;

// SHOW LOW STOCK WINDOW  
// Go to->(*close*)
// Back to->(LowStockWindow)
public class RestockWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblItemName, lblActualStock;
	private JButton btnClose, btnSubmit;
	private JSpinner spinner;
	private User user;
	private String name;
	private int price;
	private boolean type; // true = Product | false = Component
	private JLabel lblStock;
	private JLabel lblNewStock;
	private JLabel lblStockCalc;
	
	/**[WINDOW CREATION]*/

	public RestockWindow(JDialog parent, LoginController cont, User user, String name, double price, boolean type) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.type = type; // true = Product | false = Component

		// Window
		setTitle("MEDIAMARTA: Restock Item");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		/* Spinner (Numeric value)
		 * It needs to be created before because the labels need the value of it */
		SpinnerModel sm = new SpinnerNumberModel(5, 5, 5000, 5); // Default, Min, Max, Increment
		spinner = new JSpinner(sm);
		spinner.setBounds(214, 111, 187, 34);
		getContentPane().add(spinner);
		spinner.setValue(5);	
		
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
		
		lblActualStock = new JLabel("Actual Stock: ");
		lblActualStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblActualStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblActualStock.setBounds(25, 156, 187, 25);
		getContentPane().add(lblActualStock);
		
		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnSubmit.setBounds(135, 219, 147, 34);
		getContentPane().add(btnSubmit);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);
		
		lblStock = new JLabel("");
		lblStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblStock.setBounds(214, 156, 187, 25);
		getContentPane().add(lblStock);
		
		lblNewStock = new JLabel("New Stock: ");
		lblNewStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewStock.setBounds(25, 184, 187, 25);
		getContentPane().add(lblNewStock);
		
		lblStockCalc = new JLabel("");
		lblStockCalc.setHorizontalAlignment(SwingConstants.LEFT);
		lblStockCalc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblStockCalc.setBounds(214, 184, 187, 25);
		getContentPane().add(lblStockCalc);

		// Adding action listener
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}
	
	/**[METHODS]*/

	
	
	/**[ACTION PERFORMER]*/

	@Override
	public void actionPerformed(ActionEvent e) {				
		// Closes the window
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		// 
		if (e.getSource()==btnSubmit) {
			// Call restock method
			
		}
	}
}