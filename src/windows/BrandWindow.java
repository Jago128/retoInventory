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
	private JComboBox <String> comboBoxBrands;
	private JList<String> listName, listPrice;
	private User user;
	private Map<String, Product> products;
	private Map<String, Comp> components;

	/**[WINDOW CREATION]**/

	public BrandWindow(JFrame parent, LoginController cont, User user) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Brands");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(10, 24, 446, 46);
		getContentPane().add(lblMediaMarta);

		lblBrands = new JLabel("BRANDS");
		lblBrands.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrands.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBrands.setBounds(10, 58, 446, 19);
		getContentPane().add(lblBrands);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		// ComboBox & List		
		comboBoxBrands = new JComboBox <String>();
		comboBoxBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		comboBoxBrands.setBounds(10, 110, 446, 29);
		getContentPane().add(comboBoxBrands);		
		loadBrandsComboBox();		

		listName = new JList<String>();
		listName.setBounds(10, 147, 314, 363);
		getContentPane().add(listName);		

		listPrice = new JList<String>();
		listPrice.setBounds(327, 147, 129, 363);
		getContentPane().add(listPrice);

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		getContentPane().add(btnLogOut);

		btnBuy = new JButton("BUY");
		btnBuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBuy.setBounds(137, 533, 196, 35);
		if (user.getTypeU()==TypeU.CLIENT) { // In case the user is client the button will be visible
			btnBuy.setVisible(true);
		} else { // The admin will not have this option visible
			btnBuy.setVisible(false);
		}
		getContentPane().add(btnBuy);

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
			btnRemove.setVisible(true);			
		} else {  // In case the user is a client these buttons will be visible
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
			for (Brand b : brands.values()){
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
			for (Product p : products.values()){
				if(p.getStock()>0) {
					modelName.addElement(p.getNameP());
					modelPrice.addElement(p.getPrice()+" €");
				}
			}
		}	
		components = cont.showComponentsBrand((String)comboBoxBrands.getSelectedItem()); 
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				if(c.getStock()>0) {
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
			type=true;
		} else if (components.containsKey(listName.getSelectedValue())) {
			type=false;
		}
		return type;
	}

	// Obtains the name of the selected product or component
	public String obtainName() {
		String name;

		if (verifyType()) {
			Product product = new Product();
			product=cont.obtainProduct(listName.getSelectedValue());
			name=product.getNameP();
		} else {
			Comp component = new Comp();
			component=cont.obtainComponent(listName.getSelectedValue());
			name=component.getNameC();
		}
		return name;
	}

	// Obtains the price of the selected product or component
	public double obtainPrice() {
		double price;

		if (verifyType()) {
			Product product = new Product();
			product=cont.obtainProduct(listName.getSelectedValue());
			price=product.getPrice();
		} else {
			Comp component = new Comp();
			component=cont.obtainComponent(listName.getSelectedValue());
			price=component.getPrice();
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
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		// Refreshes the Products and components from the list
		if (e.getSource()==comboBoxBrands) {
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
		if (e.getSource()==btnBuy) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				CheckOutWindow checkOut = new CheckOutWindow(this, cont, user, obtainName(), obtainPrice(), verifyType());
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to buy");
			}
		} 
		// Opens the window to delete
		if (e.getSource()==btnRemove) {
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