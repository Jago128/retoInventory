package windows;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

// MENU WINDOW 
// Go to->(ProductWindow, ComponentWindow, BrandWindow, LowStockWindow)
// Back to->(*close*)
public class MenuWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnLogOut, btnProducts, btnComponents, btnBrands, btnCheckStock, btnClose;
	private LoginController cont;
	private boolean admin;

	public MenuWindow(boolean admin, LoginController controlador) {
		this.cont=controlador;
		this.admin=admin;

		//Window
		setTitle("MEDIAMARTA: Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setBounds(100, 100, 480, 636);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//Titles
		JLabel lblWelcomeTo = new JLabel("Welcome to");
		lblWelcomeTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeTo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblWelcomeTo.setBounds(5, 10, 461, 19);
		contentPane.add(lblWelcomeTo);

		JLabel lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setBounds(5, 20, 461, 46);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		contentPane.setLayout(null);
		contentPane.add(lblMediaMarta);

		//Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(new Color(240, 240, 240));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(385, 8, 81, 21);
		contentPane.add(btnLogOut);

		btnProducts = new JButton("PRODUCTS");
		btnProducts.setBounds(5, 104, 461, 35);
		btnProducts.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		contentPane.add(btnProducts);

		btnComponents = new JButton("COMPONENTS");
		btnComponents.setBounds(5, 231, 461, 35);
		btnComponents.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		contentPane.add(btnComponents);

		btnBrands = new JButton("BRANDS");
		btnBrands.setBounds(5, 363, 461, 35);
		btnBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		contentPane.add(btnBrands);

		btnCheckStock = new JButton("CHECK STOCK");
		btnCheckStock.setBounds(134, 554, 196, 35);
		btnCheckStock.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		if(admin) { // In case the user is admin the button will be visible
			btnCheckStock.setVisible(true);
		}
		else { // If not the user will not have this option visible
			btnCheckStock.setVisible(false);
		}
		contentPane.add(btnCheckStock);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(399, 578, 67, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		contentPane.add(btnClose);	

		//Adding action listener
		btnProducts.addActionListener(this);
		btnComponents.addActionListener(this);
		btnBrands.addActionListener(this);
		btnClose.addActionListener(this);
	}

	//Action performer
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window	
		if (e.getSource()==btnLogOut) {
			MainWindow main=new  MainWindow(cont);
			main.setVisible(true);
			this.dispose();
		}
		// Closes the window	
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		// Opens the window of the products
		if (e.getSource()==btnProducts) {
			ProductWindow product=new  ProductWindow(this, admin, cont); // The admin variable is sent to show or not certain option in the next windows
			product.setVisible(true);
		}
		// Opens the window of the components
		if (e.getSource()==btnComponents) {
			ComponentWindow component=new  ComponentWindow(this, admin, cont); // The admin variable is sent to show or not certain option in the next windows
			component.setVisible(true);
		}
		// Opens the window of the brands
		if (e.getSource()==btnBrands) {
			BrandWindow brand=new  BrandWindow(this, admin, cont); // The admin variable is sent to show or not certain option in the next windows
			brand.setVisible(true);
		}
		// Opens the window of the low stock window (only visible with admin users)
		if (e.getSource()==btnCheckStock) {
			LowStockWindow lowStock=new  LowStockWindow(this, cont); 
			lowStock.setVisible(true);
		}
	}
}

