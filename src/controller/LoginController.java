package controller;

import model.*;
import windows.WindowLogin;

public class LoginController {
	MediaMartaDAO dao=new DBImplementation();
	
	public void visualizarPantalla() {
		WindowLogin frame = new WindowLogin(this);
		frame.setVisible(true);
	}
}
