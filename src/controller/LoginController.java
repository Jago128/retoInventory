package controller;

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
	
	// Verify the user exist
	public boolean verifyUser(User user) {
		return dao.verifyUser(user);
	}
	
	// Verify the user and the password exist and matches
	public boolean verifyUserPassword(User user) {
		return dao.verifyUserPassword(user);
	}
	
	// Verify the user type
	public boolean verifyUserType(User user) {
		return dao.verifyUserType(user);
	}
	
	//Set up methods for usage
	public boolean insertProd(Product prod) {
		return dao.insertProd(prod);
	}
	
	public boolean insertComp(Component comp) {
		return dao.insertComp(comp);
	}
	
	public boolean sellAndSubstract() {
		return dao.sellAndSubstract();
	}
	
	public boolean deleteProd(String nom) {
		return dao.deleteProd(nom);
	}
}
