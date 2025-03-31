package main;

import controller.LoginController;

public class Main {

	public static void main(String[] args) {
		// Declare Login Controller and call visualizarPantalla
		LoginController cont=new LoginController();
		cont.showWindow();
	}
}