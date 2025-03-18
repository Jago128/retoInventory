package controller;

import windows.WindowLogin;

public class LoginController {
	
	public void visualizarPantalla() {
		WindowLogin frame = new WindowLogin(this);
		frame.setVisible(true);
	}
}
