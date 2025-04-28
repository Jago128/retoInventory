package windows;

import java.awt.*;
import java.awt.event.*;
import model.*;
import javax.swing.*;
import controller.LoginController;
import java.util.*;

/* SHOW LOW STOCK WINDOW  
 * Go to->(ReestockWindow)
 * Back to->(CheckOutWindow, NewItemWindow, VerificationWindow)*/
public class LowStockWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblLowStock;
	private JButton btnLogOut, btnRestock, btnClose;
	private JList<String> listName, listStock;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private User user;
	private JPanel panelHead, panelBody;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public LowStockWindow(JFrame parent, LoginController cont, User user) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Low Stock Items");
		setBounds(100, 100, 500, 650);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Head Panel
		panelHead = new JPanel();
		panelHead.setBackground(Color.BLACK);
		panelHead.setBounds(0, 0, 486, 113);
		getContentPane().add(panelHead);
		panelHead.setLayout(null);

		// Buttons
		btnClose = new JButton("CLOSE");
		btnClose.setForeground(Color.BLACK);
		btnClose.setBackground(Color.WHITE);
		btnClose.setBounds(10, 11, 80, 21);
		panelHead.add(btnClose);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));

		btnLogOut = new JButton("Log-Out");
		btnLogOut.setForeground(Color.BLACK);
		btnLogOut.setBounds(395, 11, 81, 21);
		panelHead.add(btnLogOut);
		btnLogOut.setBackground(Color.WHITE);
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));		

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setForeground(Color.WHITE);
		lblMediaMarta.setBounds(10, 43, 466, 51);
		panelHead.add(lblMediaMarta);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.BOLD, 25));

		lblLowStock = new JLabel("LOW STOCK ITEMS");
		lblLowStock.setForeground(Color.WHITE);
		lblLowStock.setBounds(10, 83, 466, 19);
		panelHead.add(lblLowStock);
		lblLowStock.setHorizontalAlignment(SwingConstants.CENTER);
		lblLowStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		// Labels
		JLabel lblCodUser = new JLabel(user.getCodU());
		lblCodUser.setForeground(Color.RED);
		lblCodUser.setBounds(395, 32, 81, 19);
		panelHead.add(lblCodUser);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(ProductWindow.class.getResource("/img/MediaMartaLogoR.png")));
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

		// List
		listName = new JList<String>();
		listName.setBounds(10, 25, 327, 406);
		panelBody.add(listName);

		listStock = new JList<String>();
		listStock.setBounds(347, 25, 129, 406);
		panelBody.add(listStock);

		loadList();

		// Buttons
		btnRestock = new JButton("RESTOCK");
		btnRestock.setForeground(Color.BLACK);
		btnRestock.setBackground(Color.RED);
		btnRestock.setBounds(147, 454, 196, 35);
		panelBody.add(btnRestock);
		btnRestock.setFont(new Font("Times New Roman", Font.BOLD, 15));

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnRestock.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Loads the list
	public void loadList() {
		listName.removeAll();
		listStock.removeAll();

		DefaultListModel<String> modelName = new DefaultListModel<String>();
		DefaultListModel<String> modelStock = new DefaultListModel<String>();

		products = cont.showProdsOrderedByStock();
		if (!products.isEmpty()) {
			for (Product p : products.values()) {
				modelName.addElement(p.getNameP());
				modelStock.addElement("Stock: " + cont.checkStock(p.getNameP(), true));
			}
		}
		components = cont.showCompsOrderedByStock();
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				modelName.addElement(c.getNameC());
				modelStock.addElement("Stock: " + cont.checkStock(c.getNameC(), false));
			}
		}
		listName.setModel(modelName);
		listStock.setModel(modelStock);
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

	// Obtains the name of the selected product or component
	public int obtainCode() {
		int code;

		if (verifyType()) {
			Product product = new Product();
			product = cont.obtainProduct(listName.getSelectedValue());
			code = product.getCodP();
		} else {
			Comp component = new Comp();
			component = cont.obtainComponent(listName.getSelectedValue());
			code = component.getCodC();
		}
		return code;
	}

	/**[ACTION PERFORMER]**/

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
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Opens the window to restock window
		if (e.getSource() == btnRestock) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				RestockWindow restock = new RestockWindow(this, cont, user, obtainName(), obtainCode(), obtainPrice(),
						verifyType());
				restock.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to add stock");
			}

		}
	}
}
