package windows;

import java.awt.*;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;
import model.Brand;
import model.Comp;
import model.Product;
import model.User;

// SHOW LOW STOCK WINDOW  
// Go to->(ReestockWindow)
// Back to->(CheckOutWindow, NewItemWindow, VerificationWindow)
public class LowStockWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblLowStock;
	private JButton btnLogOut, btnBuy, btnClose;
	private JComboBox <String> comboBoxBrands;
	private JList<String> list;
	private Map<String, Brand> brands;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private User user;

	public LowStockWindow(JFrame parent, LoginController controlador) {
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

		// List
		list = new JList<String>();
		list.setBounds(10, 87, 446, 423);
		getContentPane().add(list);
		
	}
