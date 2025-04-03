package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import controller.LoginController;

/* SHOW BY BRAND WINDOW  
 * Go to->(CheckOutWindow, VerificationWindow)
 * Back to->(MainWindow, MenuWindow)*/
public class BrandWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblBrands;
	private JButton btnLogOut, btnBuy, btnRemove, btnClose;
	private JComboBox <String> comboBoxBrands;
	private JList<String> listName, listPrice;
	private User user;

	/**[WINDOW CREATION]**/

	public BrandWindow(JFrame parent, LoginController cont) {
		super(parent, true); // Blocks the father window
		this.cont = cont;

		// Window
		setTitle("MEDIAMARTA: Brands");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(10, 24, 446, 46);
		getContentPane().add(lblMediaMarta);

		lblBrands = new JLabel("BRANDS");
		lblBrands.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrands.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBrands.setBounds(10, 58, 446, 19);
		getContentPane().add(lblBrands);

		// Labels
		JLabel lblCodUser = new JLabel("Username");
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		// ComboBox & List		
		comboBoxBrands = new JComboBox <String>();
		comboBoxBrands.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		comboBoxBrands.setBounds(10, 110, 446, 29);
		getContentPane().add(comboBoxBrands);				

		listName = new JList<String>();
		listName.setBounds(10, 147, 314, 363);
		getContentPane().add(listName);		

		listPrice = new JList<String>();
		listPrice.setBounds(327, 147, 129, 363);
		getContentPane().add(listPrice);

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		getContentPane().add(btnLogOut);

		btnBuy = new JButton("BUY");
		btnBuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBuy.setBounds(137, 533, 196, 35);
		getContentPane().add(btnBuy);

		btnRemove = new JButton("REMOVE");
		btnRemove.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRemove.setBounds(260, 533, 196, 35);
		getContentPane().add(btnRemove);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);		

		// Buttons visibility


		// Adding action listener
		comboBoxBrands.addActionListener(this);
		btnLogOut.addActionListener(this);
		btnBuy.addActionListener(this);
		btnRemove.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/



	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs out and moves back to the Main Window
		if (e.getSource() == btnLogOut) {
			MainWindow main = new MainWindow(cont);
			main.setVisible(true);
			JFrame parent = (JFrame)this.getParent(); // Obtains the parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		}
		// Closes the window
		if (e.getSource()==btnClose) {
			this.dispose();
		}
		// Refreshes the Products and components from the list
		if (e.getSource()==comboBoxBrands) {

		}
		// Opens the window for the Check out
		if (e.getSource()==btnBuy) {
			CheckOutWindow checkOut = new CheckOutWindow(this, cont);
			checkOut.setVisible(true);			
		} 
		// Opens the window to delete
		if (e.getSource()==btnRemove) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action							
				VerificationWindow checkOut = new VerificationWindow(this, cont);
				checkOut.setVisible(true);
			}
		}
	}
}