package controller;

import model.*;
import windows.MainWindow;

public class LoginController {
	MediaMartaDAO dao=new DBImplementation();
	
	public void visualizarPantalla() {
		MainWindow frame = new MainWindow(this);
		frame.setVisible(true);
	}
}
