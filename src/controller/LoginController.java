package controller;

import java.util.Map;

import model.*;
import windows.MainWindow;

public class LoginController {
	//Prepare the DB Implementation
	MediaMartaDAO dao=new DBImplementation();
	
	public void visualizarPantalla() {
		//Create the Login Frame
		MainWindow frame = new MainWindow(this);
		frame.setVisible(true);
	}
	
	//Set up methods for usage
	// User related methods
	public boolean verifyUser(User user) {
		return dao.verifyUser(user);
	}
	
	public boolean verifyUserPassword(User user) {
		return dao.verifyUserPassword(user);
	}
	
	public boolean verifyUserType(User user) {
		return dao.verifyUserType(user);
	}
	
	public boolean registerUser(User user) {
		return dao.registerUser(user);
	}
	// Product, Comp and Brand methods
	public Map<String, Product> verifyProduct() {
		return dao.verifyProduct();
	}
	
	public Map<String, Product> showProductsBrand() {
		return dao.showProductsBrand();
	}
	
	public Map<String, Comp> showComponentsBrand() {
		return dao.showComponentsBrand();
	}
	
	public Map<String, Brand> verifyBrands() {
		return dao.verifyBrands();
	}
	public Map<String, Comp> verifyComponent() {
		return dao.verifyComponent();
	}
	
	public boolean insertProd(Product prod) {
		return dao.insertProd(prod);
	}
	
	public boolean insertComp(Comp comp) {
		return dao.insertComp(comp);
	}
	
	public boolean sellAndSubstract(String codUser, String nomProd, int amount) {
		return dao.sellAndSubstract(codUser, nomProd, amount);
	}
	
	public boolean deleteProd(String nom) {
		return dao.deleteProd(nom);
	}
}
