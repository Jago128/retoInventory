package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.User;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

// MENU WINDOW 
// Go to->(ProductWindow, ComponentWindow, BrandWindow, LowStockWindow)
// Back to->(*close*)
public class MenuWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnProducts, btnComponents, btnBrands, btnCheckStock, btnClose;
	private LoginController cont;
	private boolean admin;

	public MenuWindow(boolean admin, LoginController controlador) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnProducts = new JButton("PRODUCTS");
		btnProducts.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnProducts.setBounds(10, 71, 416, 35);
		contentPane.add(btnProducts);

		btnComponents = new JButton("COMPONENTS");
		btnComponents.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnComponents.setBounds(10, 115, 416, 35);
		contentPane.add(btnComponents);

		btnBrands = new JButton("BRANDS");
		btnBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBrands.setBounds(10, 160, 416, 35);
		contentPane.add(btnBrands);

		btnCheckStock = new JButton("CHECK STOCK");
		btnCheckStock.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnCheckStock.setBounds(117, 205, 196, 35);
		contentPane.add(btnCheckStock);
		if(admin) { // In case the user is admin the button will be visible
			btnCheckStock.setVisible(true);
		}
		else { // If not the user will not have this option visible
			btnCheckStock.setVisible(false);
		}

		btnClose = new JButton("CLOSE");
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnClose.setBounds(369, 246, 67, 21);
		contentPane.add(btnClose);

		btnProducts.addActionListener(this);
		btnComponents.addActionListener(this);
		btnBrands.addActionListener(this);
		btnClose.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
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

