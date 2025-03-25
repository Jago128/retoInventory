package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;
import controller.LoginController;
import model.Brand;
import model.Comp;
import model.Product;
import model.TypeC;
import model.TypeP;
import model.TypeU;
import model.User;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

// ADD NEW ITEM WINDOW 
// Go to->(*close*)
// Back to->(ProductWindow, ComponentWindow, BrandWindow)
public class AddNewWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblTitle, lblName, lblType, lblBrand, lblMessage;
	private JTextField textName;
	private JButton btnClose, btnSubmit;
	private JRadioButton rdbtnA, rdbtnB, rdbtnC;
	private JSpinner spinnerQuantity, spinnerPrice;
	private JComboBox <String> comboBoxBrands;
	private Map<String, Brand> brands;
	private boolean type;  // true = Product | false = Component
	private TypeP productType;
	private TypeC componentType;
	

	//SQLINSERTPROD = "INSERT INTO PRODUCT (NAMEP, TYPEP, PRICE, STOCK, CODBRAND) VALUES (?, ?, ?, ?, ?)";
	//SQLINSERTCOMP = "INSERT INTO COMPONENT (NAMECOMP, TYPEC, STOCKCOMPONENT, PRICECOMP, CODBRAND) VALUES (?, ?, ?, ?)";

	public AddNewWindow(JDialog parent, LoginController cont, String name, boolean type) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.cont = cont;
		this.type = type;  // true = Product | false = Component

		// Window
		setTitle("MEDIAMARTA: Add New "+verifyType(type));
		setBounds(100, 100, 480, 442);
		getContentPane().setLayout(null);

		// Titles
		lblTitle = new JLabel(verifyType(type)+" Information");
		lblTitle.setBounds(10, 10, 416, 43);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getContentPane().add(lblTitle);

		lblName = new JLabel(" NAME:");
		lblName.setBounds(10, 68, 214, 27);
		lblName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		getContentPane().add(lblName);

		lblType = new JLabel(" TYPE:");
		lblType.setBounds(10, 108, 214, 27);
		lblType.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		getContentPane().add(lblType);

		JLabel lblQuantity = new JLabel(" QUANTITY:");
		lblQuantity.setBounds(10, 235, 214, 27);
		lblQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		getContentPane().add(lblQuantity);

		JLabel lblPrice = new JLabel(" PRICE:");
		lblPrice.setBounds(10, 192, 214, 27);
		lblPrice.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		getContentPane().add(lblPrice);

		lblBrand = new JLabel(" BRAND:");
		lblBrand.setBounds(10, 273, 214, 27);
		lblBrand.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		getContentPane().add(lblBrand);

		lblMessage = new JLabel("");
		lblMessage.setBounds(10, 313, 446, 34);
		lblMessage.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		getContentPane().add(lblMessage);

		// Text Fields
		textName = new JTextField();
		textName.setBounds(231, 68, 225, 29);
		textName.setColumns(10);
		getContentPane().add(textName);
		
		// Radio Buttons
		rdbtnA = new JRadioButton("");
		rdbtnA.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		rdbtnA.setBounds(67, 150, 111, 23);
		getContentPane().add(rdbtnA);

		rdbtnB = new JRadioButton("");
		rdbtnB.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		rdbtnB.setBounds(180, 150, 111, 23);
		getContentPane().add(rdbtnB);

		rdbtnC = new JRadioButton("");
		rdbtnC.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		rdbtnC.setBounds(293, 150, 111, 23);
		getContentPane().add(rdbtnC);

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
		} else {  // In case its a Component it can have 3 types					
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
		spinnerPrice.setBounds(231, 191, 225, 30);
		getContentPane().add(spinnerPrice);

		SpinnerModel smQ = new SpinnerNumberModel(1, 1, 10, 1); // Default, Min, Max, Increment
		spinnerQuantity = new JSpinner(smQ);
		spinnerQuantity.setBounds(231, 232, 225, 30);
		getContentPane().add(spinnerQuantity);

		comboBoxBrands = new JComboBox <String>();
		comboBoxBrands.setBounds(231, 273, 225, 29);
		comboBoxBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		getContentPane().add(comboBoxBrands);		
		loadBrandsComboBox();

		// Buttons
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBounds(154, 358, 147, 34);
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		getContentPane().add(btnSubmit);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);		

		// Adding action listener
		rdbtnA.addActionListener(this);
		rdbtnB.addActionListener(this);
		rdbtnC.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnClose.addActionListener(this);
	}

	// Verifying the type true = Product | false = Component | null = Brand
	public String verifyType(boolean type) {
		if (type) {
			return "Product";
		}
		else if (!type) {
			return "Component";
		}
		else {
			return "Brand";
		}
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

	// Verifies the Text field
	public boolean verifyCredentialsName() {			
		if (textName==null || textName.getText().equals("")) {			
			lblName.setText("*NAME:");
			lblName.setForeground(Color.RED);
			return false;
		} else {
			return true;
		}
	}

	// Verifies the Type field
	public boolean verifyCredentialsType() {			
		if (rdbtnA.isSelected() || rdbtnB.isSelected() || rdbtnC.isSelected()) {						
			return true;
		} else {
			lblType.setText("*TYPE:");
			lblType.setForeground(Color.RED);
			return false;
		}
	}

	// Adquires the Type value
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
			if (rdbtnC.isSelected()) {
				componentType = TypeC.RAM;
			}
			if (rdbtnC.isSelected()) {
				componentType = TypeC.PROCESSOR;
			}
		}
	}

	// Verifies the Brand field
	public boolean verifyCredentialsBrand() {			
		if (comboBoxBrands.getSelectedIndex()==-1) {			
			lblBrand.setText("*BRAND:");
			lblBrand.setForeground(Color.RED);
			return false;
		} else {
			return true;
		}
	}
	
	// Gets the Brand code
		public int setBrandCode() {	
			String brandName = ((Brand) comboBoxBrands.getSelectedItem()).getNameB();
			return cont.getBrandCode(brandName);
		}

	// Action performer
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
				if(type) { // If all fields are filled depending on the choices will create the Product or Component to add it to the database
					Product product = new Product(textName.getText(), productType, (double)spinnerPrice.getValue(), (int)spinnerQuantity.getValue(), setBrandCode());
					cont.insertProd(product);
				} else {					
					Comp component = new Comp(textName.getText(), componentType, setBrandCode(), (int)spinnerQuantity.getValue(), (double)spinnerPrice.getValue()); //String nameC, TypeC typeC, int codBrand, int stock, double price
					cont.insertComp(component);
				}
			} else {
				lblMessage.setText("Required parameters must be filled to submit");
			}
		}

		/*if (e.getSource() == btnSubmit) { // Verifies all the text fields are filled
			if (textName==null || textName.getText().equals("") || comboBoxBrands.getSelectedIndex()==-1) {
				lblMessage.setText("Required parameters must be filled to submit");
				if(textName==null || textName.equals("")) {
					lblName.setText("*NAME:");
					lblName.setForeground(Color.RED);
				}	
				if(comboBoxBrands.getSelectedIndex()==-1) {
					lblBrand.setText("*BRAND:");
					lblBrand.setForeground(Color.RED);
				}
			} else { // If all fields are filled depending on the choices will create the Product or Component to add it to the database
				setType();
				if(type) { // If all fields are filled depending on the choices will create the Product or Component to add it to the database
					Product product = new Product(textName.getText(), productType, (double)spinnerPrice.getValue(), (int)spinnerQuantity.getValue(), setBrandCode());
					cont.insertProd(product);
				} else {					
					Comp component = new Comp(textName.getText(), componentType, setBrandCode(), (int)spinnerQuantity.getValue(), (double)spinnerPrice.getValue()); //String nameC, TypeC typeC, int codBrand, int stock, double price
					cont.insertComp(component);
				}
			}
		}*/
	}
}
