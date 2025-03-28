package windows;

import java.awt.*;
import java.awt.event.*;
import model.*;
import javax.swing.*;
import controller.LoginController;
import java.util.Map;

// SHOW LOW STOCK WINDOW  
// Go to->(ReestockWindow)
// Back to->(CheckOutWindow, NewItemWindow, VerificationWindow)
public class LowStockWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblLowStock;
	private JButton btnLogOut, btnRestock, btnClose;
	private JList<String> list;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private User user;

	/**[WINDOW CREATION]*/

	public LowStockWindow(JFrame parent, LoginController cont, User user) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Low Stock Items");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(10, 24, 461, 46);
		getContentPane().add(lblMediaMarta);

		lblLowStock = new JLabel("LOW STOCK ITEMS");
		lblLowStock.setHorizontalAlignment(SwingConstants.CENTER);
		lblLowStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblLowStock.setBounds(10, 58, 461, 19);
		getContentPane().add(lblLowStock);

		// Labels
		JLabel lblCodUser = new JLabel(user.getCodU());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		// List
		list = new JList<String>();
		list.setBounds(10, 94, 446, 416);
		getContentPane().add(list);
		loadList();

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(new Color(240, 240, 240));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		getContentPane().add(btnLogOut);

		btnRestock = new JButton("RESTOCK");
		btnRestock.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRestock.setBounds(137, 533, 196, 35);
		getContentPane().add(btnRestock);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);	

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnRestock.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]*/
	// Verify the type
	public boolean verifyType() {
		boolean type = false;

		if (products.containsKey(list.getSelectedValue())) {
			type=true;
		} else if (components.containsKey(list.getSelectedValue())) {
			type=false;
		}
		return type;
	}

	// Obtains the name of the selected product or component
	public String obtainName() {
		String name;

		if (verifyType()) {
			Product product = new Product();
			product=cont.obtainProductNamePrice(list.getSelectedValue());
			name=product.getNameP();
		} else {
			Comp component = new Comp();
			component=cont.obtainComponentNamePrice(list.getSelectedValue());
			name=component.getNameC();
		}
		return name;
	}

	// Obtains the price of the selected product or component
	public double obtainPrice() {
		double price;

		if (verifyType()) {
			Product product = new Product();
			product=cont.obtainProductNamePrice(list.getSelectedValue());
			price=product.getPrice();
		} else {
			Comp component = new Comp();
			component=cont.obtainComponentNamePrice(list.getSelectedValue());
			price=component.getPrice();
		}
		return price;
	}

	// Loads the list
	public void loadList() {	
		DefaultListModel<String> model = new DefaultListModel<String>();
		products = cont.showProdsOrderedByStock();
		components = cont.showCompsOrderedByStock();

		if(!products.isEmpty()) {
			for (Product p : products.values()){
				model.addElement(p.getNameP());
			}
		}		
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				model.addElement(c.getNameC());
			}
		}
		list.setModel(model);
	}	

	/**[ACTION PERFORMER]*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window
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
		// Opens the window to restock window
		if (e.getSource()==btnRestock) {
			if (!list.isSelectionEmpty()) { // If there is an item selected it will do the action				
				RestockWindow restock = new RestockWindow(this, cont, user, obtainName(), obtainPrice(), verifyType());
				restock.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to add stock");
			}

		} 
	}
}
