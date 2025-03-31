package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import model.*;
import javax.swing.*;
import controller.LoginController;

// SHOW PRODUCT WINDOW  
// Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
// Back to->(MainWindow, MenuWindow)
public class ProductWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblProducts;
	private JButton btnLogOut, btnBuy, btnAddNew, btnRemove, btnClose;
	private JList<String> listName, listPrice;
	private Map<String, Product> products;	
	private User user;

	/**[WINDOW CREATION]**/

	public ProductWindow(JFrame parent, LoginController cont, User user) {
		super(parent,true); // Blocks the father window
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Products");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(10, 24, 446, 46);
		getContentPane().add(lblMediaMarta);

		lblProducts = new JLabel("PRODUCTS");
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblProducts.setBounds(10, 58, 446, 19);
		getContentPane().add(lblProducts);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		// List
		listName = new JList<String>();
		listName.setBounds(10, 104, 314, 406);
		getContentPane().add(listName);		

		listPrice = new JList<String>();
		listPrice.setBounds(327, 104, 129, 406);
		getContentPane().add(listPrice);

		loadProductsList();

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		getContentPane().add(btnLogOut);

		btnBuy = new JButton("BUY");
		btnBuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBuy.setBounds(10, 533, 196, 35);
		getContentPane().add(btnBuy);

		btnAddNew = new JButton("NEW PRODUCT");
		btnAddNew.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnAddNew.setBounds(10, 533, 196, 35);
		getContentPane().add(btnAddNew);

		btnRemove = new JButton("REMOVE");
		btnRemove.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRemove.setBounds(260, 533, 196, 35);
		getContentPane().add(btnRemove);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);				

		// Buttons visibility
		if (user.getTypeU()==TypeU.ADMIN) { // In case the user is an admin these buttons will be visible
			btnBuy.setVisible(false);
			btnAddNew.setVisible(true);
			btnRemove.setVisible(true);			
		} else { // In case the user is a client these buttons will be visible
			btnBuy.setVisible(true);
			btnAddNew.setVisible(false);
			btnRemove.setVisible(false);
		}

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnBuy.addActionListener(this);
		btnAddNew.addActionListener(this);
		btnRemove.addActionListener(this);
		btnClose.addActionListener(this);	
	}

	/**[METHODS]**/

	// Loads the products to the list
	public void loadProductsList() {
		listName.removeAll();
		listPrice.removeAll();

		DefaultListModel<String> modelName = new DefaultListModel<String>();
		DefaultListModel<String> modelPrice = new DefaultListModel<String>();

		products = cont.verifyProduct();		
		if(!products.isEmpty()) {
			for (Product p : products.values()){
				if(p.getStock()>0) {
					modelName.addElement(p.getNameP());
					modelPrice.addElement(p.getPrice()+" €");
				}	
			}
		}		
		listName.setModel(modelName);
		listPrice.setModel(modelPrice);
	}

	// Obtains the name and price of the selected product
	public Product obtainNamePrice() {
		Product product = new Product();
		product=cont.obtainProduct(listName.getSelectedValue());
		return product;
	}

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window	
		if (e.getSource()==btnLogOut) {
			MainWindow frame = new MainWindow(cont);
			frame.setVisible(true);
			JFrame parent = (JFrame)this.getParent(); // Obtains the parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		}		
		// Closes the window
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		// Opens the window for the Check out
		if (e.getSource() == btnBuy) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				boolean type = true;  // true = Product | false = Component
				CheckOutWindow checkOut = new CheckOutWindow(this, cont, user, obtainNamePrice().getNameP(), obtainNamePrice().getPrice(), type);
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to buy");
			}
		}
		// Opens the window to add a new product
		if (e.getSource()==btnAddNew) {			
			boolean type = true;  // true = Product | false = Component
			AddNewWindow addNew = new AddNewWindow(this, cont, user, obtainNamePrice().getNameP(), type);
			addNew.setVisible(true);
		}
		// Opens the window to delete
		if (e.getSource()==btnRemove) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				boolean type = true;  // true = Product | false = Component
				VerificationWindow checkOut = new VerificationWindow(this, cont, obtainNamePrice().getNameP(), type);
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to delete");
			}
		}
	}
}