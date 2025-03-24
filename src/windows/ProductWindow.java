package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import controller.LoginController;
import model.*;

// SHOW PRODUCT WINDOW  
// Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
// Back to->(MainWindow, MenuWindow)
public class ProductWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblProducts;
	private JButton btnLogOut, btnBuy, btnRemove,btnClose;
	private JList<String> list;
	private Map<String, Product> products;	
	private User user;

	public ProductWindow(JFrame parent, LoginController cont, User user) {
		super(parent,true); // Blocks the father window
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Products");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(10, 24, 461, 46);
		getContentPane().add(lblMediaMarta);

		lblProducts = new JLabel("PRODUCTS");
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblProducts.setBounds(10, 58, 461, 19);
		getContentPane().add(lblProducts);

		// Labels
		JLabel lblCodUser = new JLabel(user.getCodU());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		// List
		list = new JList<String>();
		list.setBounds(10, 104, 446, 406);
		getContentPane().add(list);
		loadProductsList();

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(new Color(240, 240, 240));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		getContentPane().add(btnLogOut);

		btnBuy = new JButton("BUY");
		btnBuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBuy.setBounds(10, 533, 196, 35);
		if (user.getTypeU()==TypeU.CLIENT) { // In case the user is client the button will be visible
			btnBuy.setVisible(true);
		} else { // The admin will not have this option visible
			btnBuy.setVisible(false);
		}
		getContentPane().add(btnBuy);

		btnRemove = new JButton("REMOVE");
		btnRemove.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRemove.setBounds(260, 533, 196, 35);
		if (user.getTypeU()==TypeU.ADMIN) { // In case the user is admin the button will be visible
			btnRemove.setVisible(true);
		} else { // The client will not have this option visible
			btnRemove.setVisible(false);
		}
		getContentPane().add(btnRemove);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane ().add(btnClose);

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnBuy.addActionListener(this);
		btnRemove.addActionListener(this);
		btnClose.addActionListener(this);	
	}

	// Loads the products to the list
	public void loadProductsList() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		products = cont.verifyProduct();
		if(!products.isEmpty()) {
			for (Product p : products.values()){
				model.addElement(p.getNameP());
			}
		}
		list.setModel(model);
	}
	
	// Obtains the name and price of the selected product
	public Product obtainNamePrice() {
		Product product = new Product();
		product=cont.obtainProductNamePrice(list.getSelectedValue());
		return product;
	}
	
	// Action performer
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
		if (e.getSource()==btnBuy) {
			CheckOutWindow checkOut = new CheckOutWindow(this, cont, user, obtainNamePrice().getNameP(), obtainNamePrice().getPrice());
			checkOut.setVisible(true);
		}
		// Opens the window to delete
		if (e.getSource()==btnRemove) {
			boolean type = true;  // true = Product | false = Component
			System.out.println("Get name desde la ventana productWindow "+obtainNamePrice().getNameP());
			VerificationWindow checkOut = new VerificationWindow(this, cont, obtainNamePrice().getNameP(), type);
			checkOut.setVisible(true);
		}
	}
}