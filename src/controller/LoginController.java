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
