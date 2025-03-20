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
	
	public Map<String, Product> showProducts() {
		return dao.showProducts();
	}

	public Map<String, Brand> showBrandsAndItsItems() {
		return dao.showBrandsAndItsItems();
	}

	public Map<String, Component> showComponents() {
		return dao.showComponents();
	}
	
	public boolean verifyProduct(Product product) {
		return dao.verifyProduct(product);
	}
	
	public boolean verifyComponent(Component component){
		return dao.verifyComponent(component);
	}
	
	public boolean verifyBrand(Brand brand){
		return dao.verifyBrand(brand);
	}
	
	public boolean insertProd(Product prod) {
		return dao.insertProd(prod);
	}
	
	public boolean insertComp(Component comp) {
		return dao.insertComp(comp);
	}
	
	public boolean sellAndSubstract(double amount, String nom) {
		return dao.sellAndSubstract(amount, nom);
	}
	
	public boolean deleteProd(String nom) {
		return dao.deleteProd(nom);
	}
}
