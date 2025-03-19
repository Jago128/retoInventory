package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.User;

// MENU WINDOW 
// Go to->(ProductWindow, ComponentWindow, BrandWindow, LowStockWindow)
// Back to->(*close*)
public class MenuWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public MenuWindow(LoginController controlador) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}
