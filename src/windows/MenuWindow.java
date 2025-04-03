package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import model.*;
import controller.LoginController;

// MENU WINDOW
// Go to->(ProductWindow, ComponentWindow, BrandWindow, LowStockWindow)
// Back to->(MainWindow, *close*)
public class MenuWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginController cont;
	private JButton btnLogOut, btnPurchases, btnProducts, btnComponents, btnBrands, btnCheckStock, btnClose;
	private User user;
	private Map<String, Brand> brands;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private JList<String> listProduct, listComp, listBrand;

	/**[WINDOW CREATION]**/

	public MenuWindow(LoginController controlador, User user) {
		this.cont = controlador;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 636);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		JLabel lblWelcomeTo = new JLabel("Welcome to");
		lblWelcomeTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeTo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblWelcomeTo.setBounds(5, 27, 461, 19);
		contentPane.add(lblWelcomeTo);

		JLabel lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setBounds(5, 37, 461, 46);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		contentPane.setLayout(null);
		contentPane.add(lblMediaMarta);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		contentPane.add(lblCodUser);

		// Lists & Scroll
		listProduct = new JList<String>();		
		listProduct.setLayoutOrientation(JList.VERTICAL);

		JScrollPane scrollPaneProduct = new JScrollPane(listProduct); // Creates a ScrollPane where the list will be
		scrollPaneProduct.setViewportView(listProduct);
		scrollPaneProduct.setBounds(5, 137, 451, 92); // Set bounds of the ScrollPane
		scrollPaneProduct.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPaneProduct);

		listComp = new JList<String>();
		contentPane.add(listComp);

		JScrollPane scrollPaneComponent = new JScrollPane(listComp); // Creates a ScrollPane where the list will be
		scrollPaneComponent.setViewportView(listComp);
		scrollPaneComponent.setBounds(5, 283, 451, 92); // Set bounds of the ScrollPane
		scrollPaneComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPaneComponent);

		listBrand = new JList<String>();
		contentPane.add(listBrand);

		JScrollPane scrollPaneBrand = new JScrollPane(listBrand); // Creates a ScrollPane where the list will be
		scrollPaneBrand.setViewportView(listBrand);
		scrollPaneBrand.setBounds(5, 437, 451, 92); // Set bounds of the ScrollPane
		scrollPaneBrand.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPaneBrand);

		loadProductsList();

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		contentPane.add(btnLogOut);

		btnProducts = new JButton("PRODUCTS");
		btnProducts.setBounds(5, 104, 451, 35);
		btnProducts.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		contentPane.add(btnProducts);

		btnComponents = new JButton("COMPONENTS");
		btnComponents.setBounds(5, 249, 451, 35);
		btnComponents.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		contentPane.add(btnComponents);

		btnBrands = new JButton("BRANDS");
		btnBrands.setBounds(5, 403, 451, 35);
		btnBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		contentPane.add(btnBrands);

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

		btnPurchases = new JButton("Purchases");
		btnPurchases.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnPurchases.setBackground(UIManager.getColor("Button.background"));
		btnPurchases.setBounds(294, 5, 81, 21);
		if (user.getTypeU()==TypeU.CLIENT) { // In case the user is client the button will be visible
			btnPurchases.setVisible(true);
		} else { // The admin will not have this option visible
			btnPurchases.setVisible(false);
		}
		contentPane.add(btnPurchases);		

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnPurchases.addActionListener(this);
		btnProducts.addActionListener(this);
		btnComponents.addActionListener(this);
		btnBrands.addActionListener(this);
		btnCheckStock.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Loads the products to the list
	public void loadProductsList() {
		listProduct.removeAll();
		listComp.removeAll();
		listBrand.removeAll();		

		DefaultListModel<String> modelProd = new DefaultListModel<String>();
		DefaultListModel<String> modelComp = new DefaultListModel<String>();
		DefaultListModel<String> modelBrand = new DefaultListModel<String>();

		products = cont.verifyProduct();
		if(!products.isEmpty()) {
			for (Product p : products.values()){
				if(p.getStock()>0) {
					modelProd.addElement(p.nameAndPrice());
				}
			}
		}

		components = cont.verifyComponent();
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				if(c.getStock()>0) {
					modelComp.addElement(c.nameAndPrice());
				}				
			}
		}

		brands = cont.verifyBrands();
		if (!brands.isEmpty()) {
			for (Brand b : brands.values()) {
				modelBrand.addElement(b.getNameB());
			}
		}

		listProduct.setModel(modelProd);
		listComp.setModel(modelComp);
		listBrand.setModel(modelBrand);
	}

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window
		if (e.getSource() == btnLogOut) {
			MainWindow main = new MainWindow(cont);
			main.setVisible(true);
			this.dispose();
		}// Logs-Out and moves back to the Main Window
		if (e.getSource() == btnPurchases) {
			PurchaseWindow purchase = new PurchaseWindow(cont, user);
			purchase.setVisible(true);
			this.dispose();
		}
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Opens the window of the products
		if (e.getSource() == btnProducts) {
			ProductWindow product = new ProductWindow(this, cont, user); // The admin variable is sent to show or not certain options in the next windows
			product.setVisible(true);
		}
		// Opens the window of the components
		if (e.getSource() == btnComponents) {
			ComponentWindow component = new ComponentWindow(this, cont, user); // The admin variable is sent to show or not certain option in the next windows
			component.setVisible(true);
		}
		// Opens the window of the brands
		if (e.getSource() == btnBrands) {
			BrandWindow brand = new BrandWindow(this, cont, user); // The admin variable is sent to show or not certain option in the next windows
			brand.setVisible(true);
		}
		// Opens the window of the low stock window (only visible to admin users)
		if (e.getSource() == btnCheckStock) { // The admin variable is sent to show or not certain option in the next windows
			LowStockWindow lowStock = new LowStockWindow(this, cont, user);
			lowStock.setVisible(true);
		}
	}
}
