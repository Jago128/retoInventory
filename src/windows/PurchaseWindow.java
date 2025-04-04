package windows;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import model.*;

/* SHOW PURCHASE WINDOW  
 * Go to->(*close*)
 * Back to->(MenuWindow)*/
public class PurchaseWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginController cont;
	private JButton btnLogOut, btnClose;
	private User user;
	private JList<String> listPurchases;

	/**[WINDOW CREATION]**/

	public PurchaseWindow(LoginController controlador, User user) {
		this.cont = controlador;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: "+user.getUsername()+"'s Purchases");
		setBounds(100, 100, 480, 636);
		getContentPane().setBackground(Color.WHITE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		JLabel lblYourPurchases = new JLabel(user.getUsername()+"'s Purchases");
		lblYourPurchases.setBounds(5, 37, 461, 46);
		lblYourPurchases.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourPurchases.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		contentPane.setLayout(null);
		contentPane.add(lblYourPurchases);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		contentPane.add(lblCodUser);

		// Lists & Scroll
		listPurchases = new JList<String>();
		listPurchases.setBounds(10, 104, 446, 485);
		contentPane.add(listPurchases);
		loadProductsList();

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		contentPane.add(btnLogOut);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		contentPane.add(btnClose);

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	// Loads the products to the list
	public void loadProductsList() {
		Map<Integer, Purchase> purchases = cont.getPurchases(user.getCodU());
		Map<Integer, Buy> buys = cont.getBuys(user.getCodU());
		DefaultListModel<String> model = new DefaultListModel<String>();

		listPurchases.removeAll();

		if (!purchases.isEmpty()) {
			for (Purchase p : purchases.values()) {
				model.addElement(p.allData());
			}
		}
		if (!buys.isEmpty()) {
			for (Buy b : buys.values()) {
				model.addElement(b.allData());
			}
		}
		listPurchases.setModel(model);
	}

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window
		if (e.getSource() == btnLogOut) {
			MainWindow main = new MainWindow(cont);
			main.setVisible(true);
			this.dispose();
		}
		// Closes the window
		if (e.getSource() == btnClose) {
			MenuWindow menu = new MenuWindow(cont, user);
			menu.setVisible(true);
			this.dispose();
		}
	}
}
