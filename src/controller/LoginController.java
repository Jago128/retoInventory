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
	public boolean insertProd(Product p) {
		return dao.insertProd(p);
	}
	
	public boolean insertComp(Component c) {
		return dao.insertComp(c);
	}
	
	public boolean sellAndSubstract() {
		return dao.sellAndSubstract();
	}
	
	public boolean deleteProd(int cod) {
		return dao.deleteProd(cod);
	}
}
