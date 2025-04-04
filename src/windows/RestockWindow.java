package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import controller.LoginController;
import model.*;

// SHOW LOW STOCK WINDOW
// Go to->(*close*)
// Back to->(LowStockWindow)
public class RestockWindow extends JDialog implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblItemName, lblStockCalc, lblStock, lblActualStock, lblNewStock;
	private JButton btnClose, btnSubmit;
	private JSpinner spinner;
	private String name;
	private int code;
	private boolean type; // true = Product | false = Component

	/**[WINDOW CREATION]**/

	public RestockWindow(JDialog parent, LoginController cont, User user, String name, int code, double price, boolean type) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.name = name;
		this.code = code;
		this.type = type; // true = Product | false = Component

		// Window
		setTitle("MEDIAMARTA: Restock Item");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

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

		lblActualStock = new JLabel("Actual Stock: ");
		lblActualStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblActualStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblActualStock.setBounds(25, 156, 187, 25);
		getContentPane().add(lblActualStock);

		lblNewStock = new JLabel("New Stock: ");
		lblNewStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewStock.setBounds(25, 184, 187, 25);
		getContentPane().add(lblNewStock);

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

		lblStock = new JLabel(cont.checkStock(name, type)+" units");
		lblStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblStock.setBounds(214, 156, 187, 25);
		getContentPane().add(lblStock);

		lblStockCalc = new JLabel(calcNewStock()+" units");
		lblStockCalc.setHorizontalAlignment(SwingConstants.LEFT);
		lblStockCalc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblStockCalc.setBounds(214, 184, 187, 25);
		getContentPane().add(lblStockCalc);

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

	// Calculates the new stock
	public int calcNewStock() {	// Calculates the subtotal basing on the price of the product and the spinner's value
		return cont.checkStock(name, type) + (int) spinner.getValue();
	}

	// Refresh parent window list
	public void refreshParentList() {
		JDialog parent = (JDialog)this.getParent(); // Obtains the parent window
		if(parent instanceof ProductWindow){ // Checks the parent window type
			ProductWindow productWindow = (ProductWindow)parent; // Cast it to its type to be able to use it's methods
			productWindow.loadProductsList(); // Calls the parent method to reload the list
		} else if (parent instanceof ComponentWindow){ 
			ComponentWindow productWindow = (ComponentWindow)parent;
			productWindow.loadComponentList(); 
		} else if (parent instanceof BrandWindow){ 
			BrandWindow productWindow = (BrandWindow)parent;
			productWindow.loadList(); 
		}
		else if (parent instanceof LowStockWindow) {
			LowStockWindow lowStockWindow = (LowStockWindow)parent;
			lowStockWindow.loadList();
		}
	}

	/**[ACTION PERFORMER & CHANGE LISTENER]**/

	// Action Performer
	@Override
	public void actionPerformed(ActionEvent e) {				
		// Closes the window
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		// 
		if (e.getSource()==btnSubmit) {			
			cont.restock(code, calcNewStock(), type);
			JOptionPane.showMessageDialog(null, "Item "+name+" got added "+(int)spinner.getValue()+" units succesfully");
			refreshParentList();
			this.dispose();
		}
	}

	// Change listener
	@Override
	public void stateChanged(ChangeEvent e) {
		// Closes the window
		if (e.getSource() == spinner) {
			lblStockCalc.setText(calcNewStock()+" units");
		}
	}
}