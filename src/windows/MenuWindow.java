package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import model.*;
import controller.LoginController;

/* MENU WINDOW
 * Go to->(ProductWindow, ComponentWindow, BrandWindow, LowStockWindow)
 * Back to->(MainWindow, *close*)*/
public class MenuWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginController cont;
	private JButton btnLogOut, btnPurchases, btnProducts, btnComponents, btnBrands, btnCheckStock;
	private User user;
	private Map<String, Brand> brands;
	private Map<String, Product> products;
	private Map<String, Comp> components;
	private JList<String> listProduct, listComp, listBrand;
	private JPanel panelHead, panelBody;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public MenuWindow(LoginController controlador, User user) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = controlador;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false); // Blocks the window so it can't be modified the size
		contentPane.setLayout(null);

		// Head Panel
		panelHead = new JPanel();
		panelHead.setBackground(Color.RED);
		panelHead.setBounds(0, 0, 486, 113);
		contentPane.add(panelHead);
		panelHead.setLayout(null);

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setForeground(Color.WHITE);
		btnLogOut.setBounds(395, 11, 81, 21);
		panelHead.add(btnLogOut);
		btnLogOut.setBackground(Color.BLACK);
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));

		btnPurchases = new JButton("Purchases");
		btnPurchases.setForeground(Color.WHITE);
		btnPurchases.setBounds(318, 11, 81, 21);
		panelHead.add(btnPurchases);
		btnPurchases.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnPurchases.setBackground(Color.BLACK);
		btnPurchases.addActionListener(this);
		if (user.getTypeU() == TypeU.CLIENT) { // In case the user is client the button will be visible
			btnPurchases.setVisible(true);
		} else { // The admin will not have this option visible
			btnPurchases.setVisible(false);
		}

		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(ProductWindow.class.getResource("/img/MediaMartaLogoW.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 40));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(-69, -34, 258, 191);
		panelHead.add(logo);

		// Titles
		JLabel lblWelcomeTo = new JLabel("Welcome to");
		lblWelcomeTo.setBounds(0, 51, 486, 19);
		panelHead.add(lblWelcomeTo);
		lblWelcomeTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeTo.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setBounds(0, 56, 486, 46);
		panelHead.add(lblMediaMarta);
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.BOLD, 25));

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setForeground(Color.WHITE);
		lblCodUser.setBounds(395, 31, 81, 19);
		panelHead.add(lblCodUser);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		// Body Panel
		panelBody = new JPanel();
		panelBody.setBackground(Color.WHITE);
		panelBody.setBounds(0, 113, 486, 500);
		contentPane.add(panelBody);
		panelBody.setLayout(null);

		// Lists & Scroll
		listProduct = new JList<String>();
		listProduct.setLayoutOrientation(JList.VERTICAL);

		JScrollPane scrollPaneProduct = new JScrollPane(listProduct);
		scrollPaneProduct.setBounds(10, 44, 466, 92);
		panelBody.add(scrollPaneProduct);
		scrollPaneProduct.setViewportView(listProduct);
		scrollPaneProduct.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		listComp = new JList<String>();
		listComp.setBounds(1, 1, 432, 90);
		contentPane.add(listComp);

		JScrollPane scrollPaneComponent = new JScrollPane(listComp);
		scrollPaneComponent.setBounds(10, 190, 466, 92);
		panelBody.add(scrollPaneComponent);
		scrollPaneComponent.setViewportView(listComp);
		scrollPaneComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		listBrand = new JList<String>();
		listBrand.setBounds(1, 1, 432, 90);
		contentPane.add(listBrand);

		JScrollPane scrollPaneBrand = new JScrollPane(listBrand);
		scrollPaneBrand.setBounds(10, 344, 466, 92);
		panelBody.add(scrollPaneBrand);
		scrollPaneBrand.setViewportView(listBrand);
		scrollPaneBrand.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		loadProductsList();

		// Buttons
		btnProducts = new JButton("PRODUCTS");
		btnProducts.setForeground(Color.WHITE);
		btnProducts.setBackground(Color.BLACK);
		btnProducts.setBounds(10, 11, 466, 35);
		panelBody.add(btnProducts);
		btnProducts.setFont(new Font("Times New Roman", Font.BOLD, 15));

		btnComponents = new JButton("COMPONENTS");
		btnComponents.setBackground(Color.BLACK);
		btnComponents.setForeground(Color.WHITE);
		btnComponents.setBounds(10, 156, 466, 35);
		panelBody.add(btnComponents);
		btnComponents.setFont(new Font("Times New Roman", Font.BOLD, 15));

		btnBrands = new JButton("BRANDS");
		btnBrands.setForeground(Color.WHITE);
		btnBrands.setBackground(Color.BLACK);
		btnBrands.setBounds(10, 310, 466, 35);
		panelBody.add(btnBrands);
		btnBrands.setFont(new Font("Times New Roman", Font.BOLD, 15));

		btnCheckStock = new JButton("CHECK STOCK");
		btnCheckStock.setBackground(Color.RED);
		btnCheckStock.setForeground(Color.BLACK);
		btnCheckStock.setBounds(147, 454, 196, 35);
		panelBody.add(btnCheckStock);
		btnCheckStock.setFont(new Font("Times New Roman", Font.BOLD, 15));
		if (user.getTypeU() == TypeU.ADMIN) { // In case the user is admin the button will be visible
			btnCheckStock.setVisible(true);
		} else { // The client will not have this option visible
			btnCheckStock.setVisible(false);
		}

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnCheckStock.addActionListener(this);
		btnBrands.addActionListener(this);
		btnComponents.addActionListener(this);
		btnProducts.addActionListener(this);
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
		if (!products.isEmpty()) {
			for (Product p : products.values()) {
				if (p.getStock()>0) {
					modelProd.addElement(p.nameAndPrice());
				}
			}
		}

		components = cont.verifyComponent();
		if (!components.isEmpty()) {
			for (Comp c : components.values()) {
				if (c.getStock()>0) {
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
		} // Logs-Out and moves back to the Main Window
		if (e.getSource() == btnPurchases) {
			PurchaseWindow purchase = new PurchaseWindow(cont, user);
			purchase.setVisible(true);
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
