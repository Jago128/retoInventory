package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.LoginController;

/* SHOW LOW STOCK WINDOW  
 * Go to->(ReestockWindow)
 * Back to->(CheckOutWindow, NewItemWindow, VerificationWindow)*/
public class LowStockWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblLowStock;
	private JButton btnLogOut, btnRestock, btnClose;
	private JList<String> listName, listStock;

	/**[WINDOW CREATION]**/

	public LowStockWindow(JFrame parent, LoginController cont) {
		super(parent, true); // Blocks the father window
		this.cont = cont;

		// Window
		setTitle("MEDIAMARTA: Low Stock Items");
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

		lblLowStock = new JLabel("LOW STOCK ITEMS");
		lblLowStock.setHorizontalAlignment(SwingConstants.CENTER);
		lblLowStock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblLowStock.setBounds(10, 58, 446, 19);
		getContentPane().add(lblLowStock);

		// Labels
		JLabel lblCodUser = new JLabel("Username");
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		// List
		listName = new JList<String>();
		listName.setBounds(10, 104, 314, 406);
		getContentPane().add(listName);		

		listStock = new JList<String>();
		listStock.setBounds(327, 104, 129, 406);
		getContentPane().add(listStock);

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		getContentPane().add(btnLogOut);

		btnRestock = new JButton("RESTOCK");
		btnRestock.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRestock.setBounds(137, 533, 196, 35);
		getContentPane().add(btnRestock);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(5, 5, 80, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane().add(btnClose);	

		// Adding action listener
		btnLogOut.addActionListener(this);
		btnRestock.addActionListener(this);
		btnClose.addActionListener(this);
	}

	/**[METHODS]**/

	

	/**[ACTION PERFORMER]**/

	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window
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
		// Opens the window to restock window
		if (e.getSource()==btnRestock) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action				
				RestockWindow restock = new RestockWindow(this, cont);
				restock.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to add stock");
			}

		} 
	}
}
