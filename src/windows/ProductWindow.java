package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import controller.LoginController;
import model.Product;
import model.User;

// SHOW PRODUCT WINDOW  
// Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
// Back to->(MenuWindow)
public class ProductWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblProducts;
	private JButton btnLogOut, btnBuy, btnRemove, btnClose;
	private JList<String> list;
	private Map<String, Product> products;
	private boolean admin;

	public ProductWindow(JFrame parent, boolean admin, LoginController cont) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.admin = admin;

		// Window
		setTitle("MEDIAMARTA: Products");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(5, 8, 461, 46);
		getContentPane().add(lblMediaMarta);

		lblProducts = new JLabel("PRODUCTS");
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblProducts.setBounds(5, 42, 461, 19);
		getContentPane().add(lblProducts);

		// List
		list = new JList<String>();
		list.setBounds(10, 64, 446, 446);
		getContentPane().add(list);
		loadProductsList();

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(new Color(240, 240, 240));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(385, 8, 81, 21);
		getContentPane().add(btnLogOut);

		btnBuy = new JButton("BUY");
		btnBuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBuy.setBounds(10, 533, 196, 35);
		if (admin) {
			// If not the user will not have this option visible
			btnBuy.setVisible(false);
		} else { // In case the user is admin the button will be visible
			btnBuy.setVisible(true);
		}
		getContentPane().add(btnBuy);

		btnRemove = new JButton("REMOVE");
		btnRemove.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRemove.setBounds(260, 533, 196, 35);
		if (admin) { // In case the user is admin the button will be visible
			btnRemove.setVisible(true);
		} else { // If not the user will not have this option visible
			btnRemove.setVisible(false);
		}
		getContentPane().add(btnRemove);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(399, 578, 67, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);

		// Adding action listener
		btnBuy.addActionListener(this);
		btnRemove.addActionListener(this);
		btnClose.addActionListener(this);
	}

	// Loads the products to the list
	public void loadProductsList() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		products = cont.verifyProduct();
		if (!products.isEmpty()) {
			for (Product p : products.values()) {
				model.addElement(p.getNameP());
			}
		}
		list.setModel(model);
	}

	// Action performer
	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window
		if (e.getSource() == btnLogOut) {
			MainWindow main = new MainWindow(cont);
			main.setVisible(true);
			this.dispose();
		}
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		//
		if (e.getSource() == btnBuy) {
			this.dispose();
		}
		//
		if (e.getSource() == btnRemove) {
			this.dispose();
		}
	}
}