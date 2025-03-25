package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.*;

import controller.LoginController;
import model.*;

// SHOW BY BRAND WINDOW  
// Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
// Back to->(MainWindow, MenuWindow)
public class BrandWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblBrands;
	private JButton btnLogOut, btnBuy, btnClose;
	private JComboBox <String> comboBoxBrands;
	private JList<String> list;
	private Map<String, Brand> brands;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private DefaultListModel<String> model;

	public BrandWindow(JFrame parent, LoginController cont, User user) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		
	
		// Window
		setTitle("MEDIAMARTA: Brands");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(10, 24, 461, 46);
		getContentPane().add(lblMediaMarta);

		lblBrands = new JLabel("BRANDS");
		lblBrands.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrands.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBrands.setBounds(10, 58, 461, 19);
		getContentPane().add(lblBrands);

		// Labels
		JLabel lblCodUser = new JLabel(user.getCodU());
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

		list = new JList<String>();
		list.setBounds(10, 167, 446, 343);
		getContentPane().add(list);		

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(new Color(240, 240, 240));
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

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);		

		// Adding action listener
		comboBoxBrands.addActionListener(this);
		btnLogOut.addActionListener(this);
		btnBuy.addActionListener(this);
		btnClose.addActionListener(this);
	}

	// Loads the brands to the combo box
	public void loadBrandsComboBox() {		
		brands = cont.verifyBrands();
		if(!brands.isEmpty()) {
			for (Brand b : brands.values()){
				comboBoxBrands.addItem(b.getNameB());				
			}
			comboBoxBrands.setSelectedIndex(-1);
		}		
	}

	// Loads the products to the list
	public void loadProductsList(DefaultListModel<String> model) {		
		products = cont.showProductsBrand((String)comboBoxBrands.getSelectedItem()); //HERE
		if(!products.isEmpty()) {
			for (Product p : products.values()){
				model.addElement(p.getNameP());
				System.out.print("List: "+model+"\n");
				System.out.print("Variable: "+p.getNameP()+"\n");
			}
		}
		list.setModel(model);
	}

	// Loads the components to the list
	public void loadComponentsList(DefaultListModel<String> model) {		
		components = cont.showComponentsBrand((String)comboBoxBrands.getSelectedItem()); //HERE
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				model.addElement(c.getNameC());
				System.out.print("List: "+model+"\n");
				System.out.print("Variable: "+c.getNameC()+"\n");
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
			JFrame parent = (JFrame)this.getParent(); // Obtains the parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		}
		// Closes the window
		if (e.getSource()==btnClose) {
			this.dispose();
		} 		
		if(e.getSource()==comboBoxBrands) {
			if (comboBoxBrands.getSelectedIndex()>-1) { // It will refresh and fill the list with items of the brand selected in the ComboBox
				list.removeAll();			
				loadProductsList(model);
				loadComponentsList(model);
			}else { // The list will be empty while there is nothing selected in the ComboBox
				list.removeAll();
			}
		}
	}
}