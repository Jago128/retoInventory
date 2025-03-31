package controller;

import java.util.Map;

import model.*;
import windows.MainWindow;

public class LoginController {
	/**[DATABASE]**/

	// Prepare the DB Implementation
	MediaMartaDAO dao = new DBImplementation();

	// Creates and starts the windows
	public void showWindow() {
		MainWindow frame = new MainWindow(this);
		frame.setVisible(true);
	}

	/**[USERS]**/

	public boolean verifyUser(User user) {
		return dao.verifyUser(user);
	};

	public boolean verifyUserPassword(User user) {
		return dao.verifyUserPassword(user);
	};

	public boolean verifyUserType(User user) {
		return dao.verifyUserType(user);
	};

	public boolean registerUser(User user) {
		return dao.registerUser(user);
	};

	/**[PRODUCTS]**/

	public Map<String, Product> verifyProduct() {
		return dao.verifyProduct();
	};

	public Product obtainProductNamePrice(String name) {
		return dao.obtainProductNamePrice(name);
	};

	public boolean insertProd(Product prod) {
		return dao.insertProd(prod);
	};

	public boolean deleteProd(String nom) {
		return dao.deleteProd(nom);
	};

	public int checkStock(String nomItem, boolean type) {
		return dao.checkStock(nomItem, type);
	};

	public Map<String, Product> showProdsOrderedByStock() {
		return dao.showProdsOrderedByStock();
	};

	/**[COMPONENTS]**/

	public Map<String, Comp> verifyComponent() {
		return dao.verifyComponent();
	};

	public Comp obtainComponentNamePrice(String name) {
		return dao.obtainComponentNamePrice(name);
	};

	public boolean insertComp(Comp comp) {
		return dao.insertComp(comp);
	};

	public boolean deleteComp(String nom) {
		return dao.deleteComp(nom);
	};

	public Map<String, Comp> showCompsOrderedByStock() {
		return dao.showCompsOrderedByStock();
	};

	/**[PRODUCTS & COMPONENTS]**/

	public void sellAndSubstract(String codUser, String nomProd, int amount, double price, boolean comp) {
		dao.sellAndSubstract(codUser, nomProd, amount, price, comp);
	};

	public boolean restock(String name, int quantity, boolean type) {
		return dao.restock(name, quantity, type);
	};

	/**[BRANDS]**/

	public Map<String, Brand> verifyBrands() {
		return dao.verifyBrands();
	};

	public int getBrandCode(String brandName) {
		return dao.getBrandCode(brandName);
	};

	public Map<String, Product> showProductsBrand(String brand) {
		return dao.showProductsBrand(brand);
	};

	public Map<String, Comp> showComponentsBrand(String brand) {
		return dao.showComponentsBrand(brand);
	};
}