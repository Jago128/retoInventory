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
	private JButton btnCerrar;
	private JList<String> list;
	private Map<String, Product> products;
	private JLabel lblTitulo;
	private LoginController cont;
	private boolean admin;

	public ProductWindow(JFrame parent, boolean admin, LoginController cont) {
		super(parent,true); //Recibe la ventana padra para bloquearla y que no se pueda modificar
		this.cont=cont;

		setTitle("PRODUCTS");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblTitulo = new JLabel("The products in our shop");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTitulo.setBounds(68, 11, 313, 43);
		getContentPane().add(lblTitulo);
		
		list = new JList<String>();
		list.setBounds(10, 64, 416, 168);
		getContentPane().add(list);
		loadProducts();
		
		btnCerrar = new JButton("CERRAR");
		btnCerrar.setBounds(356, 242, 80, 21);
		btnCerrar.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane ().add(btnCerrar);
				
		btnCerrar.addActionListener(this);	
	}

	public void loadProducts() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		products = cont.verifyProduct();
		if(!products.isEmpty()) {
			for (Product p : products.values()){
				model.addElement(p.getNameP());
			}
		}
		list.setModel(model);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnCerrar) {
			this.dispose();
		}
	}
}