package windows;

import java.awt.*;
import java.awt.event.*;
import model.*;
import javax.swing.*;
import controller.LoginController;
import java.util.*;

/* ADD NEW ITEM WINDOW
 * Go to->(*close*)
 * Back to->(ProductWindow/ComponentWindow/BrandWindow)*/
public class AddNewWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitle, lblName, lblType, lblBrand, lblMessage;
	private JTextField textName;
	private JButton btnClose, btnSubmit;
	private JRadioButton rdbtnA, rdbtnB, rdbtnC;
	private JSpinner spinnerQuantity, spinnerPrice;
	private JComboBox<String> comboBoxBrands;
	private Map<String, Brand> brands;
	private boolean type; // true = Product | false = Component
	private TypeP productType;
	private TypeC componentType;
	private JPanel panelHead, panelLeftBody, panelRightBody;
	private JLabel logo;

	/**[WINDOW CREATION]*/

	public AddNewWindow(JDialog parent, LoginController cont, User user, String name, boolean type) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = cont;
		this.type = type; // true = Product | false = Component

		// Window
		setTitle("MEDIAMARTA: Add New "+verifyType(type));
		setBounds(100, 100, 560, 520);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Head Panel
		panelHead = new JPanel();
		panelHead.setBackground(Color.BLACK);
		panelHead.setBounds(0, 0, 546, 113);
		getContentPane().add(panelHead);
		panelHead.setLayout(null);

		// Buttons
		btnClose = new JButton("CLOSE");
		btnClose.setForeground(Color.BLACK);
		btnClose.setBackground(Color.WHITE);
		btnClose.setBounds(10, 11, 80, 21);
		panelHead.add(btnClose);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));

		// Titles
		lblTitle = new JLabel(verifyType(type)+" Information");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(0, 41, 546, 43);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelHead.add(lblTitle);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setForeground(Color.RED);
		lblCodUser.setBounds(455, 11, 81, 19);
		panelHead.add(lblCodUser);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(ProductWindow.class.getResource("/img/MediaMartaLogoR.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 40));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(-69, -34, 258, 191);
		panelHead.add(logo);

		// Left Body Panel
		panelLeftBody = new JPanel();
		panelLeftBody.setBackground(Color.RED);
		panelLeftBody.setBounds(0, 113, 173, 370);
		getContentPane().add(panelLeftBody);
		panelLeftBody.setLayout(null);

		// Labels
		lblName = new JLabel(" NAME:");
		lblName.setForeground(Color.BLACK);
		lblName.setBounds(10, 68, 153, 27);
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panelLeftBody.add(lblName);

		lblType = new JLabel(" TYPE:");
		lblType.setForeground(Color.BLACK);
		lblType.setBounds(10, 113, 153, 27);
		lblType.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panelLeftBody.add(lblType);

		JLabel lblQuantity = new JLabel(" QUANTITY:");
		lblQuantity.setForeground(Color.BLACK);
		lblQuantity.setBounds(10, 197, 153, 27);
		lblQuantity.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panelLeftBody.add(lblQuantity);

		JLabel lblPrice = new JLabel(" PRICE:");
		lblPrice.setForeground(Color.BLACK);
		lblPrice.setBounds(10, 159, 153, 27);
		lblPrice.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panelLeftBody.add(lblPrice);

		lblBrand = new JLabel(" BRAND:");
		lblBrand.setForeground(Color.BLACK);
		lblBrand.setBounds(10, 235, 153, 27);
		lblBrand.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panelLeftBody.add(lblBrand);

		// Left Right Panel
		panelRightBody = new JPanel();
		panelRightBody.setBackground(Color.WHITE);
		panelRightBody.setBounds(171, 113, 375, 370);
		getContentPane().add(panelRightBody);
		panelRightBody.setLayout(null);

		// Labels
		lblMessage = new JLabel("");
		lblMessage.setForeground(Color.RED);
		lblMessage.setBounds(10, 284, 355, 34);
		lblMessage.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panelRightBody.add(lblMessage);

		// Text Fields
		textName = new JTextField();
		textName.setBounds(46, 64, 290, 29);
		textName.setColumns(10);
		panelRightBody.add(textName);

		// Radio Buttons
		rdbtnA = new JRadioButton("");
		rdbtnA.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		rdbtnA.setBounds(148, 111, 86, 29);
		panelRightBody.add(rdbtnA);

		rdbtnB = new JRadioButton("");
		rdbtnB.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		rdbtnB.setBounds(46, 111, 86, 29);
		panelRightBody.add(rdbtnB);

		rdbtnC = new JRadioButton("");
		rdbtnC.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		rdbtnC.setBounds(249, 111, 86, 29);
		panelRightBody.add(rdbtnC);

		ButtonGroup group = new ButtonGroup(); // Creates a group for the Radio Buttons to only one can be choosed
		group.add(rdbtnA);
		group.add(rdbtnB);
		group.add(rdbtnC);

		// Radio Buttons visibility
		if (type) { // In case its a Product only can have 2 types
			rdbtnB.setText("Mobile");
			rdbtnC.setText("Computer");
			rdbtnA.setVisible(false);
			rdbtnB.setVisible(true);
			rdbtnC.setVisible(true);
		} else { // In case its a Component it can have 3 types
			rdbtnA.setText("Graphics");
			rdbtnB.setText("RAM");
			rdbtnC.setText("Processor");
			rdbtnA.setVisible(true);
			rdbtnB.setVisible(true);
			rdbtnC.setVisible(true);
		}

		// ComboBox & Spinner
		SpinnerModel smP = new SpinnerNumberModel(1, 0.1, 9999.99, 1); // Default, Min, Max, Increment
		spinnerPrice = new JSpinner(smP);
		spinnerPrice.setBounds(45, 155, 290, 30);
		panelRightBody.add(spinnerPrice);

		SpinnerModel smQ = new SpinnerNumberModel(1, 1, 10, 1); // Default, Min, Max, Increment
		spinnerQuantity = new JSpinner(smQ);
		spinnerQuantity.setBounds(44, 196, 291, 30);
		panelRightBody.add(spinnerQuantity);

		comboBoxBrands = new JComboBox<String>();
		comboBoxBrands.setBounds(44, 237, 291, 29);
		comboBoxBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panelRightBody.add(comboBoxBrands);
		
		loadBrandsComboBox();

		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBackground(Color.RED);
		btnSubmit.setForeground(Color.BLACK);
		btnSubmit.setBounds(110, 329, 147, 34);
		btnSubmit.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelRightBody.add(btnSubmit);

		// Adding action listener
		rdbtnA.addActionListener(this);
		rdbtnB.addActionListener(this);
		rdbtnC.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Sets the color of the labels true = BLACK | false = RED
	public void setLabelColor(JLabel label, boolean correct) {
		if (correct) {
			label.setForeground(Color.BLACK);
		} else {
			label.setForeground(Color.RED);
		}
	}

	// Sets the color of the text fields true = WHITE | false = RED
	public void setTextColor(JTextField field, boolean correct) {
		if (correct) {
			field.setBackground(Color.WHITE);
		} else {
			field.setBackground(new Color(250, 128, 114));
		}
	}

	// Verifying the type true = Product | false = Component
	public String verifyType(boolean type) {
		if (type) {
			return "Product";
		} else {
			return "Component";
		}
	}

	// Loads the brands to the combo box
	public void loadBrandsComboBox() {
		brands = cont.verifyBrands();
		if (!brands.isEmpty()) {
			for (Brand b : brands.values()) {
				comboBoxBrands.addItem(b.getNameB());
			}
			comboBoxBrands.setSelectedIndex(-1);
		}
	}

	// Verifies the Text field
	public boolean verifyCredentialsName() {
		if (textName == null || textName.getText().equals("")) {
			lblName.setText("*NAME:");
			setLabelColor(lblName, false);
			return false;
		} else {
			lblName.setText(" NAME:");
			setLabelColor(lblName, true);
			return true;
		}
	}

	// Verifies the Type field
	public boolean verifyCredentialsType() {
		if (rdbtnA.isSelected() || rdbtnB.isSelected() || rdbtnC.isSelected()) {
			lblType.setText(" TYPE:");
			setLabelColor(lblType, true);
			return true;
		} else {
			lblType.setText("*TYPE:");
			setLabelColor(lblType, false);
			return false;
		}
	}

	// Acquires the Type value
	public void setType() {
		if (type) {
			if (rdbtnB.isSelected()) {
				productType = TypeP.MOBILE;
			}
			if (rdbtnC.isSelected()) {
				productType = TypeP.COMPUTER;
			}
		} else {
			if (rdbtnA.isSelected()) {
				componentType = TypeC.GRAPHICS;
			}
			if (rdbtnB.isSelected()) {
				componentType = TypeC.RAM;
			}
			if (rdbtnC.isSelected()) {
				componentType = TypeC.PROCESSOR;
			}
		}
	}

	// Verifies the Brand field
	public boolean verifyCredentialsBrand() {
		if (comboBoxBrands.getSelectedIndex() == -1) {
			lblBrand.setText("*BRAND:");
			lblBrand.setForeground(Color.RED);
			return false;
		} else {
			lblBrand.setText("BRAND:");
			lblBrand.setForeground(Color.BLACK);
			return true;
		}
	}

	// Gets the Brand code
	public int setBrandCode() {
		String brandName = (String)comboBoxBrands.getSelectedItem();
		return cont.getBrandCode(brandName);
	}

	// Refresh parent window list
	public void refreshParentList() {
		JDialog parent = (JDialog)this.getParent(); // Obtains the parent window
		if (parent instanceof ProductWindow) { // Checks the parent window type
			ProductWindow productWindow = (ProductWindow)parent; // Cast it to its type to be able to use it's methods
			productWindow.loadProductsList(); // Calls the parent method to reload the list
		} else if (parent instanceof ComponentWindow) {
			ComponentWindow productWindow = (ComponentWindow)parent;
			productWindow.loadComponentList();
		} else if (parent instanceof BrandWindow) {
			BrandWindow productWindow = (BrandWindow)parent;
			productWindow.loadList();
		} else if (parent instanceof LowStockWindow) {
			LowStockWindow lowStockWindow = (LowStockWindow)parent;
			lowStockWindow.loadList();
		}
	}

	/**[ACTION PERFORMER]*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes the window
		if (e.getSource() == btnClose) {
			this.dispose();
		}

		// Verifies the text fields and adds the Product or Component
		if (e.getSource() == btnSubmit) { // Verifies all the text fields are filled
			if (verifyCredentialsName() && verifyCredentialsType() && verifyCredentialsBrand()) {
				setType();
				if (type) { // If all fields are filled depending on the choices will create the Product or Component to add it to the database
					Product product = new Product(textName.getText(), productType, (double)spinnerPrice.getValue(), (int)spinnerQuantity.getValue(), setBrandCode());
					cont.insertProd(product);
					JOptionPane.showMessageDialog(null, "Product "+product.getNameP()+" with price "+product.getPrice()+"€ added with "+product.getStock()+" units of stock succesfully.");
					refreshParentList();
					this.dispose();
				} else {
					Comp component = new Comp(textName.getText(), componentType, setBrandCode(), (int)spinnerQuantity.getValue(), (double)spinnerPrice.getValue());
					cont.insertComp(component);
					JOptionPane.showMessageDialog(null, "Component "+component.getNameC()+" with price "+component.getPrice()+"€ added with "+component.getStock()+" units of stock succesfully.");
					refreshParentList();
					this.dispose();
				}
			} else {
				lblMessage.setText("Required parameters must be filled to submit.");
			}
		}
	}
}
