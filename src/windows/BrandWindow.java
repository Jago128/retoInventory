package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import controller.LoginController;
import java.util.*;

/* SHOW BY BRAND WINDOW
 * Go to->(CheckOutWindow, VerificationWindow)
 * Back to->(MainWindow, MenuWindow)*/
public class BrandWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblBrands;
	private JButton btnLogOut, btnBuy, btnRemove, btnClose;
	private JComboBox<String> comboBoxBrands;
	private JList<String> listName, listPrice;
	private User user;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private JPanel panelHead, panelBody;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public BrandWindow(JFrame parent, LoginController cont, User user) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Brands");
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
		btnClose.setFont(new Font("Times New Roman", Font.BOLD, 10));

		btnLogOut = new JButton("Log-Out");
		btnLogOut.setForeground(Color.WHITE);
		btnLogOut.setBounds(395, 11, 81, 21);
		panelHead.add(btnLogOut);
		btnLogOut.setBackground(Color.BLACK);
		btnLogOut.setFont(new Font("Times New Roman", Font.BOLD, 10));

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setBounds(10, 43, 466, 51);
		panelHead.add(lblMediaMarta);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.BOLD, 25));

		lblBrands = new JLabel("BRANDS");
		lblBrands.setBounds(10, 83, 466, 19);
		panelHead.add(lblBrands);
		lblBrands.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrands.setFont(new Font("Times New Roman", Font.PLAIN, 20));

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

		// ComboBox & List
		comboBoxBrands = new JComboBox<String>();
		comboBoxBrands.setBounds(10, 11, 466, 22);
		panelBody.add(comboBoxBrands);
		comboBoxBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		loadBrandsComboBox();

		listName = new JList<String>();
		listName.setBounds(10, 43, 327, 406);
		panelBody.add(listName);

		listPrice = new JList<String>();
		listPrice.setBounds(347, 43, 129, 406);
		panelBody.add(listPrice);

		// Buttons
		btnRemove = new JButton("REMOVE");
		btnRemove.setBackground(Color.RED);
		btnRemove.setForeground(Color.BLACK);
		btnRemove.setBounds(147, 454, 196, 35);
		panelBody.add(btnRemove);
		btnRemove.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		btnBuy = new JButton("BUY");
		btnBuy.setForeground(Color.WHITE);
		btnBuy.setBackground(Color.BLACK);
		btnBuy.setBounds(147, 454, 196, 35);
		panelBody.add(btnBuy);
		btnBuy.setFont(new Font("Times New Roman", Font.BOLD, 15));

		// Buttons visibility
		if (user.getTypeU() == TypeU.ADMIN) { // In case the user is an admin these buttons will be visible
			btnBuy.setVisible(false);
			btnRemove.setVisible(true);
		} else { // In case the user is a client these buttons will be visible
			btnBuy.setVisible(true);
			btnRemove.setVisible(false);
		}

		// Adding action listener
		comboBoxBrands.addActionListener(this);
		btnLogOut.addActionListener(this);
		btnBuy.addActionListener(this);
		btnRemove.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Loads the brands to the combo box
	public void loadBrandsComboBox() {
		Map<String, Brand> brands;

		brands = cont.verifyBrands();
		if (!brands.isEmpty()) {
			for (Brand b : brands.values()) {
				comboBoxBrands.addItem(b.getNameB());
			}
			comboBoxBrands.setSelectedIndex(-1);
		}
	}

	// Loads the list
	public void loadList() {
		listName.removeAll();
		listPrice.removeAll();

		DefaultListModel<String> modelName = new DefaultListModel<String>();
		DefaultListModel<String> modelPrice = new DefaultListModel<String>();

		products = cont.showProductsBrand((String)comboBoxBrands.getSelectedItem());
		if (!products.isEmpty()) {
			for (Product p : products.values()) {
				if (p.getStock() > 0) {
					modelName.addElement(p.getNameP());
					modelPrice.addElement(p.getPrice()+" €");
				}
			}
		}
		components = cont.showComponentsBrand((String)comboBoxBrands.getSelectedItem());
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				if (c.getStock() > 0) {
					modelName.addElement(c.getNameC());
					modelPrice.addElement(c.getPrice()+" €");
				}
			}
		}
		listName.setModel(modelName);
		listPrice.setModel(modelPrice);
	}

	// Verify the type
	public boolean verifyType() {
		boolean type = false;

		if (products.containsKey(listName.getSelectedValue())) {
			type = true;
		} else if (components.containsKey(listName.getSelectedValue())) {
			type = false;
		}
		return type;
	}

	// Obtains the name of the selected product or component
	public String obtainName() {
		String name;

		if (verifyType()) {
			Product product = new Product();
			product = cont.obtainProduct(listName.getSelectedValue());
			name = product.getNameP();
		} else {
			Comp component = new Comp();
			component = cont.obtainComponent(listName.getSelectedValue());
			name = component.getNameC();
		}
		return name;
	}

	// Obtains the price of the selected product or component
	public double obtainPrice() {
		double price;

		if (verifyType()) {
			Product product = new Product();
			product = cont.obtainProduct(listName.getSelectedValue());
			price = product.getPrice();
		} else {
			Comp component = new Comp();
			component = cont.obtainComponent(listName.getSelectedValue());
			price = component.getPrice();
		}
		return price;
	}

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs out and moves back to the Main Window
		if (e.getSource() == btnLogOut) {
			MainWindow main = new MainWindow(cont);
			main.setVisible(true);
			JFrame parent = (JFrame)this.getParent(); // Obtains the parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		}
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Refreshes the Products and components from the list
		if (e.getSource() == comboBoxBrands) {
			if (comboBoxBrands.getSelectedIndex()>-1) { // It will refresh and fill the list with items of the brand selected in the ComboBox
				listName.removeAll();
				listPrice.removeAll();
				loadList();
			} else { // The list will be empty while there is nothing selected in the ComboBox
				listName.removeAll();
				listPrice.removeAll();
			}
		}
		// Opens the window for the Check out
		if (e.getSource() == btnBuy) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				CheckOutWindow checkOut = new CheckOutWindow(this, cont, user, obtainName(), obtainPrice(), verifyType());
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to buy");
			}
		}
		// Opens the window to delete
		if (e.getSource() == btnRemove) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				boolean type = verifyType(); // true = Product | false = Component

				VerificationWindow checkOut = new VerificationWindow(this, cont, obtainName(), type);
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to delete");
			}
		}
	}
}