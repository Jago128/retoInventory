package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.Brand;
import model.Comp;
import model.Product;
import model.TypeU;
import model.User;

public class PurchaseWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginController cont;
	private JButton btnLogOut, btnCheckStock, btnClose;
	private User user;
	private Map<String, Brand> brands;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private JList<String> listPurchases;

	/**[WINDOW CREATION]**/

	public PurchaseWindow(LoginController controlador, User user) {
		this.cont = controlador;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 636);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		JLabel lblYourPurchases = new JLabel(user.getUsername()+"'s Purchases");
		lblYourPurchases.setBounds(5, 37, 461, 46);
		lblYourPurchases.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourPurchases.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		contentPane.setLayout(null);
		contentPane.add(lblYourPurchases);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		contentPane.add(lblCodUser);

		// Lists & Scroll
		listPurchases = new JList<String>();
		listPurchases.setBounds(5, 104, 451, 422);
		contentPane.add(listPurchases);

		loadProductsList();
		
		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		contentPane.add(btnLogOut);

		btnCheckStock = new JButton("CHECK STOCK");
		btnCheckStock.setBounds(134, 554, 196, 35);
		btnCheckStock.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		if (user.getTypeU()==TypeU.ADMIN) { // In case the user is admin the button will be visible
			btnCheckStock.setVisible(true);
		} else { // The client will not have this option visible
			btnCheckStock.setVisible(false);
		}
		contentPane.add(btnCheckStock);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		contentPane.add(btnClose);

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnCheckStock.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Loads the products to the list
	public void loadProductsList() {
		listPurchases.removeAll();
		
		DefaultListModel<String> modelProd = new DefaultListModel<String>();
		DefaultListModel<String> modelComp = new DefaultListModel<String>();
		DefaultListModel<String> modelBrand = new DefaultListModel<String>();
		
		products = cont.verifyProduct();
		if(!products.isEmpty()) {
			for (Product p : products.values()){
				modelProd.addElement(p.nameAndPrice());
			}
		}
	
		components = cont.verifyComponent();
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				modelComp.addElement(c.nameAndPrice());
			}
		}
		
		brands = cont.verifyBrands();
		if (!brands.isEmpty()) {
			for (Brand b : brands.values()) {
				modelBrand.addElement(b.getNameB());
			}
		}
		
		listPurchases.setModel(modelProd);
	}

	/**[ACTION PERFORMER]**/

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
		// Opens the window of the low stock window (only visible to admin users)
		if (e.getSource() == btnCheckStock) { // The admin variable is sent to show or not certain option in the next windows
			
		}
	}
}
