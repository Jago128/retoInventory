package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JSpinner.*;
import javax.swing.event.*;
import controller.LoginController;
import model.User;

/* CHECK OUT WINDOW 
 * Go to->(*close*)
 * Back to->(ProductWindow/ComponentWindow/BrandWindow)*/
public class CheckOutWindow extends JDialog implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblItemName, lblPrice;
	private JButton btnClose, btnSubmit;
	private JSpinner spinner;
	private User user;
	private String name;
	private double price;
	private boolean type; // true = Product | false = Component
	private JPanel panelLeft, panelRight;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public CheckOutWindow(JDialog parent, LoginController cont, User user, String name, double price, boolean type) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;
		this.user = user;
		this.name = name;
		this.price = price;
		this.type = type; // true = Product | false = Component

		// Window
		setTitle("MEDIAMARTA: Check-out");
		setBounds(100, 100, 500, 350);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Left Panel
		panelLeft = new JPanel();
		panelLeft.setBounds(0, 0, 215, 313);
		panelLeft.setBackground(Color.RED);
		getContentPane().add(panelLeft);
		panelLeft.setLayout(null);

		// Buttons
		btnClose = new JButton("CLOSE");
		btnClose.setBounds(10, 11, 80, 21);
		btnClose.setForeground(Color.WHITE);
		btnClose.setBackground(Color.BLACK);
		panelLeft.add(btnClose);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));

		// Titles
		JLabel item = new JLabel("Item Name: ");
		item.setForeground(Color.WHITE);
		item.setBounds(25, 103, 180, 25);
		item.setHorizontalAlignment(SwingConstants.LEFT);
		item.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelLeft.add(item);

		JLabel subtotal = new JLabel("Subtotal: ");
		subtotal.setForeground(Color.WHITE);
		subtotal.setHorizontalAlignment(SwingConstants.LEFT);
		subtotal.setFont(new Font("Times New Roman", Font.BOLD, 25));
		subtotal.setBounds(25, 211, 180, 25);
		panelLeft.add(subtotal);

		// Right Panel
		panelRight = new JPanel();
		panelRight.setBounds(212, 0, 274, 313);
		panelRight.setBackground(Color.WHITE);
		panelRight.setLayout(null);
		getContentPane().add(panelRight);
		
		// Spinner (Numeric value)
		// It needs to be created before because the labels need the value of it
		SpinnerModel sm = new SpinnerNumberModel(1, 1, cont.checkStock(name, type), 1); // Default, Min, Max, Increment
		spinner = new JSpinner(sm);
		spinner.setBounds(30, 152, 223, 34);
		panelRight.add(spinner);
		spinner.setValue(1);
		((DefaultEditor)spinner.getEditor()).getTextField().setEditable(false); // Prevents the spinner textfield from being editable with a keyboard, but doesn't prevent the values from being changed
		
		// Titles
		JLabel quantity = new JLabel("Quantity to Buy");
		quantity.setForeground(Color.BLACK);
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setFont(new Font("Times New Roman", Font.BOLD, 25));
		quantity.setBounds(10, 41, 254, 34);
		panelRight.add(quantity);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setForeground(Color.BLACK);
		lblCodUser.setBounds(183, 11, 81, 19);
		panelRight.add(lblCodUser);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
	
		lblItemName = new JLabel(name);
		lblItemName.setForeground(Color.BLACK);
		lblItemName.setBackground(new Color(255, 255, 255));
		lblItemName.setHorizontalAlignment(SwingConstants.LEFT);
		lblItemName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblItemName.setBounds(30, 101, 223, 34);
		panelRight.add(lblItemName);

		lblPrice = new JLabel(calcPrice()+"€");
		lblPrice.setForeground(Color.BLACK);
		lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrice.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPrice.setBounds(30, 208, 223, 34);
		panelRight.add(lblPrice);

		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBackground(Color.BLACK);
		btnSubmit.setForeground(Color.WHITE);
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

	// Calculate price
	public double calcPrice() { // Calculates the subtotal basing on the price of the product and the spinner's value
		return price*(int)spinner.getValue();
	}

	// Refresh parent window list
	public void refreshParentList() {
		JDialog parent = (JDialog)this.getParent(); // Obtains the parent window
		if (parent instanceof ProductWindow) { // Checks the parent window type
			ProductWindow productWindow = (ProductWindow) parent; // Cast it to its type to be able to use it's methods
			productWindow.loadProductsList(); // Calls the parent method to reload the list
		} else if (parent instanceof ComponentWindow) {
			ComponentWindow productWindow = (ComponentWindow) parent;
			productWindow.loadComponentList();
		} else if (parent instanceof BrandWindow) {
			BrandWindow productWindow = (BrandWindow) parent;
			productWindow.loadList();
		} else if (parent instanceof LowStockWindow) {
			LowStockWindow lowStockWindow = (LowStockWindow) parent;
			lowStockWindow.loadList();
		}
	}

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
			if((int)spinner.getValue()<=cont.checkStock(name, type)) {
				cont.sellAndSubstract(user.getCodU(), name, (int)spinner.getValue(), calcPrice(), type);
				ContinueWindow next = new ContinueWindow(this, user, type);
				next.setVisible(true);
				refreshParentList();
			}
			else {
				JOptionPane.showMessageDialog(null, "There is not enough stock. Try with less quantity.");
			}
		}
	}

	// Change listener
	@Override
	public void stateChanged(ChangeEvent e) {
		// Closes the window
		if (e.getSource() == spinner) {
			lblPrice.setText(calcPrice()+"€");
		}
	}
}