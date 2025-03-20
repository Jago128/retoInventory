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
	public boolean verifyUser(User user) {
		return dao.verifyUser(user);
	}
	
	public boolean verifyUserPassword(User user) {
		return dao.verifyUserPassword(user);
	}
	
	public boolean verifyUserType(User user) {
		return dao.verifyUserType(user);
	}
	
	public Map<String, Product> verifyProduct() {
		return dao.verifyProduct();
	}

	public Map<String, Brand> verifyBrandsAndItsItems() {
		return dao.verifyBrandsAndItsItems();
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
	
	public boolean sellAndSubstract(double amount, String nom, String codUser) {
		return dao.sellAndSubstract(amount, nom, codUser);
	}
	
	public boolean deleteProd(String nom) {
		return dao.deleteProd(nom);
	}
}
