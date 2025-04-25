package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import model.*;
import javax.swing.*;
import controller.LoginController;

/* SHOW PRODUCT WINDOW  
 * Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
 * Back to->(MainWindow, MenuWindow)*/
public class ProductWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblProducts;
	private JButton btnLogOut, btnBuy, btnAddNew, btnRemove, btnClose;
	private JComboBox<String> comboxFilter;
	private JList<String> listName, listPrice;
	private User user;
	private JPanel panelHead, panelBody;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public ProductWindow(JFrame parent, LoginController cont, User user) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Products");
		setBounds(100, 100, 500, 650);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Head Panel
		panelHead = new JPanel();
		panelHead.setBackground(Color.RED);
		panelHead.setBounds(0, 0, 486, 113);
		getContentPane().add(panelHead);
		panelHead.setLayout(null);

		// Buttons
		btnClose = new JButton("CLOSE");
		btnClose.setForeground(Color.WHITE);
		btnClose.setBackground(Color.BLACK);
		btnClose.setBounds(10, 11, 80, 21);
		panelHead.add(btnClose);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));

		btnLogOut = new JButton("Log-Out");
		btnLogOut.setForeground(Color.WHITE);
		btnLogOut.setBounds(395, 11, 81, 21);
		panelHead.add(btnLogOut);
		btnLogOut.setBackground(Color.BLACK);
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setBounds(10, 43, 466, 51);
		panelHead.add(lblMediaMarta);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.BOLD, 25));

		lblProducts = new JLabel("PRODUCTS");
		lblProducts.setBounds(10, 83, 466, 19);
		panelHead.add(lblProducts);
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setForeground(Color.WHITE);
		lblCodUser.setBounds(395, 31, 81, 19);
		panelHead.add(lblCodUser);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));	

		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(ProductWindow.class.getResource("/img/MediaMartaLogoW.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 40));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(-69, -34, 258, 191);
		panelHead.add(logo);
		
		// Body Panel
		panelBody = new JPanel();
		panelBody.setBackground(Color.WHITE);
		panelBody.setBounds(0, 113, 486, 500);
		getContentPane().add(panelBody);
		panelBody.setLayout(null);

		// List, Scroll & ComboBox
		listName = new JList<String>();
		listName.setBounds(10, 43, 327, 406);
		panelBody.add(listName);

		listPrice = new JList<String>();
		listPrice.setBounds(347, 43, 129, 406);
		panelBody.add(listPrice);

		comboxFilter = new JComboBox<String>();
		comboxFilter.setBounds(10, 11, 466, 22);
		panelBody.add(comboxFilter);
		comboxFilter.addItem("ALL");
		comboxFilter.addItem("MOBILES");
		comboxFilter.addItem("COMPUTERS");

		loadProductsList();

		// Buttons
		btnAddNew = new JButton("NEW PRODUCT");
		btnAddNew.setForeground(Color.BLACK);
		btnAddNew.setBackground(Color.RED);
		btnAddNew.setBounds(10, 454, 196, 35);
		panelBody.add(btnAddNew);
		btnAddNew.setFont(new Font("Times New Roman", Font.BOLD, 15));

		btnRemove = new JButton("REMOVE");
		btnRemove.setBackground(Color.RED);
		btnRemove.setForeground(Color.BLACK);
		btnRemove.setBounds(280, 454, 196, 35);
		panelBody.add(btnRemove);
		btnRemove.setFont(new Font("Times New Roman", Font.BOLD, 15));

		btnBuy = new JButton("BUY");
		btnBuy.setForeground(Color.WHITE);
		btnBuy.setBackground(Color.BLACK);
		btnBuy.setBounds(10, 454, 196, 35);
		panelBody.add(btnBuy);
		btnBuy.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		// Buttons visibility
		if (user.getTypeU() == TypeU.ADMIN) { // In case the user is an admin these buttons will be visible
			btnBuy.setVisible(false);
			btnAddNew.setVisible(true);
			btnRemove.setVisible(true);
		} else { // In case the user is a client these buttons will be visible
			btnBuy.setVisible(true);
			btnAddNew.setVisible(false);
			btnRemove.setVisible(false);
		}

		// Adding action listener
		btnClose.addActionListener(this);
		btnLogOut.addActionListener(this);
		comboxFilter.addActionListener(this);
		btnRemove.addActionListener(this);
		btnAddNew.addActionListener(this);
		btnBuy.addActionListener(this);
	}

	/**[METHODS]**/

	// Loads the products to the list depending on the filter
	public void loadProductsList() {
		Map<String, Product> products = cont.verifyProduct();
		DefaultListModel<String> modelName = new DefaultListModel<String>();
		DefaultListModel<String> modelPrice = new DefaultListModel<String>();

		listName.removeAll();
		listPrice.removeAll();

		switch ((String) comboxFilter.getSelectedItem()) {
		case "MOBILES": // Uses the TreeMap ordered by the key as the name
			if (!products.isEmpty()) {
				for (Product p : products.values()) {
					if (p.getStock()>0 && p.getTypeP() == TypeP.MOBILE) {
						modelName.addElement(p.getNameP());
						modelPrice.addElement(p.getPrice()+" €");
					}
				}
			}
			break;
		case "COMPUTERS": // Creates a TreeMap ordered by the key as the price
			if (!products.isEmpty()) {
				for (Product p : products.values()) {
					if (p.getStock()>0 && p.getTypeP() == TypeP.COMPUTER) {
						modelName.addElement(p.getNameP());
						modelPrice.addElement(p.getPrice()+" €");
					}
				}
			}
			break;
		default:
			if (!products.isEmpty()) {
				for (Product p : products.values()) {
					if (p.getStock()>0) {
						modelName.addElement(p.getNameP());
						modelPrice.addElement(p.getPrice()+" €");
					}
				}
			}
			break;
		}
		listName.setModel(modelName);
		listPrice.setModel(modelPrice);
	}

	// Obtains the name and price of the selected product
	public Product obtainNamePrice() {
		Product product = new Product();
		product = cont.obtainProduct(listName.getSelectedValue());
		return product;
	}

	/**[ACTION PERFORMER]**/

	// Action Performer
	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window
		if (e.getSource() == btnLogOut) {
			MainWindow frame = new MainWindow(cont);
			frame.setVisible(true);
			JFrame parent = (JFrame)this.getParent(); // Obtains the parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		}
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Detects when new option of order is choosed
		if (e.getSource() == comboxFilter) {
			loadProductsList();
		}
		// Opens the window for the Check out
		if (e.getSource() == btnBuy) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				boolean type = true; // true = Product | false = Component
				CheckOutWindow checkOut = new CheckOutWindow(this, cont, user, obtainNamePrice().getNameP(), obtainNamePrice().getPrice(), type);
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to buy");
			}
		}
		// Opens the window to add a new product
		if (e.getSource() == btnAddNew) {
			boolean type = true; // true = Product | false = Component
			AddNewWindow addNew = new AddNewWindow(this, cont, user, obtainNamePrice().getNameP(), type);
			addNew.setVisible(true);
		}
		// Opens the window to delete
		if (e.getSource() == btnRemove) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				boolean type = true; // true = Product | false = Component
				VerificationWindow checkOut = new VerificationWindow(this, cont, obtainNamePrice().getNameP(), type);
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to delete");
			}
		}
	}
}