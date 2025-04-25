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
	private JPanel panelHead, panelBody;
	private JLabel logo;

	/**[WINDOW CREATION]**/

	public PurchaseWindow(LoginController controlador, User user) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));
		this.cont = controlador;
		this.user = user;

		// Window
		setTitle("MEDIAMARTA: "+user.getUsername()+"'s Purchases");
		setBounds(100, 100, 800, 650);
		getContentPane().setBackground(Color.WHITE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		// Head Panel
		panelHead = new JPanel();
		panelHead.setBackground(Color.RED);
		panelHead.setBounds(0, 0, 786, 113);
		getContentPane().add(panelHead);
		panelHead.setLayout(null);

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setForeground(Color.WHITE);
		btnLogOut.setBackground(Color.BLACK);
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(695, 11, 81, 21);
		panelHead.add(btnLogOut);

		btnClose = new JButton("CLOSE");
		btnClose.setForeground(Color.WHITE);
		btnClose.setBackground(Color.BLACK);
		btnClose.setBounds(10, 11, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		panelHead.add(btnClose);

		// Labels
		JLabel lblYourPurchases = new JLabel(user.getUsername()+"'s Purchases");
		lblYourPurchases.setBounds(5, 37, 771, 46);
		lblYourPurchases.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourPurchases.setFont(new Font("Times New Roman", Font.BOLD, 25));
		contentPane.setLayout(null);
		panelHead.add(lblYourPurchases);

		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(ProductWindow.class.getResource("/img/MediaMartaLogoW.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 40));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(-69, -34, 258, 191);
		panelHead.add(logo);

		// Labels
		JLabel lblCodUser = new JLabel(user.getUsername());
		lblCodUser.setForeground(Color.WHITE);
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(695, 33, 81, 19);
		panelHead.add(lblCodUser);

		// Body Panel
		panelBody = new JPanel();
		panelBody.setBackground(Color.WHITE);
		panelBody.setBounds(0, 113, 786, 500);
		getContentPane().add(panelBody);
		panelBody.setLayout(null);

		// Lists & Scroll
		listPurchases = new JList<String>();
		listPurchases.setBounds(10, 11, 766, 478);
		panelBody.add(listPurchases);

		loadProductsList();

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
