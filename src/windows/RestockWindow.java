package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.*;
import controller.LoginController;
import model.*;

/* SHOW LOW STOCK WINDOW
 * Go to->(*close*)
 * Back to->(LowStockWindow)*/
public class RestockWindow extends JDialog implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblItemName, lblStockCalc, lblStock, lblActualStock, lblNewStock;
	private JButton btnClose, btnSubmit;
	private JSpinner spinner;
	private String name;
	private int code;
	private boolean type; // true = Product | false = Component
	private JPanel panelLeft, panelRight;
	private JLabel logo;

	/** [WINDOW CREATION] **/

	public RestockWindow(JDialog parent, LoginController cont, User user, String name, int code, double price, boolean type) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;
		this.name = name;
		this.code = code;
		this.type = type; // true = Product | false = Component

		// Window
		setTitle("MEDIAMARTA: Restock Item");
		setBounds(100, 100, 500, 350);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size
		getContentPane().setLayout(null);

		// Left Panel
		panelLeft = new JPanel();
		panelLeft.setBounds(0, 0, 215, 313);
		panelLeft.setBackground(Color.RED);
		getContentPane().add(panelLeft);
		panelLeft.setLayout(null);

		// Buttons
		btnClose = new JButton("CLOSE");
		btnClose.setBounds(10, 11, 80, 21);
		btnClose.setForeground(Color.BLACK);
		btnClose.setBackground(Color.WHITE);
		panelLeft.add(btnClose);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));

		// Titles
		JLabel item = new JLabel("Item Name: ");
		item.setForeground(Color.BLACK);
		item.setBounds(25, 95, 180, 25);
		item.setHorizontalAlignment(SwingConstants.LEFT);
		item.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelLeft.add(item);

		// Labels
		lblActualStock = new JLabel("Actual Stock: ");
		lblActualStock.setForeground(Color.BLACK);
		lblActualStock.setBounds(25, 136, 180, 25);
		lblActualStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblActualStock.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelLeft.add(lblActualStock);

		lblNewStock = new JLabel("New Stock: ");
		lblNewStock.setForeground(Color.BLACK);
		lblNewStock.setBounds(25, 226, 180, 25);
		lblNewStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewStock.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelLeft.add(lblNewStock);

		// Right Panel
		panelRight = new JPanel();
		panelRight.setBounds(212, 0, 274, 313);
		panelRight.setBackground(Color.BLACK);
		panelRight.setLayout(null);
		getContentPane().add(panelRight);

		// Spinner (Numeric value)
		// It needs to be created before because the labels need the value of it
		SpinnerModel sm = new SpinnerNumberModel(5, 5, 5000, 1); // Default, Min, Max, Increment
		spinner = new JSpinner(sm);
		spinner.setBounds(20, 179, 231, 34);
		panelRight.add(spinner);
		spinner.setValue(5);
		((DefaultEditor)spinner.getEditor()).getTextField().setEditable(false); // Prevents the spinner textfield from being editable with a keyboard

		// Titles
		JLabel quantity = new JLabel("Quantity to Restock");
		quantity.setForeground(Color.WHITE);
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setFont(new Font("Times New Roman", Font.BOLD, 25));
		quantity.setBounds(10, 41, 254, 34);
		panelRight.add(quantity);

		// Labels
		JLabel lblCodUser = new JLabel(user.getCodU());
		lblCodUser.setForeground(Color.RED);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(183, 11, 81, 19);
		panelRight.add(lblCodUser);

		lblItemName = new JLabel(name);
		lblItemName.setForeground(Color.WHITE);
		lblItemName.setBounds(20, 89, 231, 34);
		lblItemName.setBackground(new Color(255, 255, 255));
		lblItemName.setHorizontalAlignment(SwingConstants.LEFT);
		lblItemName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		panelRight.add(lblItemName);

		lblStock = new JLabel(cont.checkStock(name, type) + " units");
		lblStock.setForeground(Color.WHITE);
		lblStock.setHorizontalAlignment(SwingConstants.LEFT);
		lblStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblStock.setBounds(20, 134, 231, 34);
		panelRight.add(lblStock);

		lblStockCalc = new JLabel(calcNewStock() + " units");
		lblStockCalc.setForeground(Color.WHITE);
		lblStockCalc.setHorizontalAlignment(SwingConstants.LEFT);
		lblStockCalc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblStockCalc.setBounds(20, 222, 231, 34);
		panelRight.add(lblStockCalc);

		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBackground(Color.WHITE);
		btnSubmit.setForeground(Color.BLACK);
		btnSubmit.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnSubmit.setBounds(70, 267, 147, 34);
		panelRight.add(btnSubmit);

		// Logo
		logo = new JLabel("");
		logo.setBounds(80, 121, 217, 217);
		logo.setIcon(new ImageIcon(SignInWindow.class.getResource("/img/MediaMartaLogoR.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 100));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		panelRight.add(logo);

		// Adding action listener
		spinner.addChangeListener(this);
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Calculates the new stock
	public int calcNewStock() { // Calculates the subtotal basing on the price of the product and the spinner's value
		return cont.checkStock(name, type) + (int) spinner.getValue();
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

	/**[ACTION PERFORMER & CHANGE LISTENER]**/

	// Action Performer
	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// 
		if (e.getSource() == btnSubmit) {
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