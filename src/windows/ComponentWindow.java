package windows;

import java.awt.*;
import java.awt.event.*;
import model.*;
import javax.swing.*;
import controller.LoginController;

/* SHOW COMPONENT WINDOW  
 * Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
 * Back to->(MainWindow, MenuWindow)*/
public class ComponentWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblProducts;
	private JButton btnLogOut, btnBuy, btnAddNew, btnRemove, btnClose;
	private JComboBox <String> comboxFilter;
	private JList<String> listName, listPrice;	
	private User user;

	/**[WINDOW CREATION]**/

	public ComponentWindow(JFrame parent, LoginController cont) {
		super(parent, true); // Blocks the father window
		this.cont = cont;

		// Window
		setTitle("MEDIAMARTA: Components");
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

		lblProducts = new JLabel("COMPONENTS");
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblProducts.setBounds(10, 58, 446, 19);
		getContentPane().add(lblProducts);

		// Labels
		JLabel lblCodUser = new JLabel("Username");
		lblCodUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblCodUser.setBounds(375, 27, 81, 19);
		getContentPane().add(lblCodUser);

		// List & ComboBox
		listName = new JList<String>();
		listName.setBounds(10, 104, 314, 406);
		getContentPane().add(listName);		

		listPrice = new JList<String>();
		listPrice.setBounds(327, 104, 129, 406);
		getContentPane().add(listPrice);

		comboxFilter = new JComboBox<String>();
		comboxFilter.setBounds(10, 81, 446, 22);
		getContentPane().add(comboxFilter);
		comboxFilter.addItem("ALL");
		comboxFilter.addItem("GRAPHICS");
		comboxFilter.addItem("RAM");
		comboxFilter.addItem("PROCESSORS");				

		// Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(375, 5, 81, 21);
		getContentPane().add(btnLogOut);

		btnBuy = new JButton("BUY");
		btnBuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBuy.setBounds(10, 533, 196, 35);
		getContentPane().add(btnBuy);

		btnAddNew = new JButton("NEW COMPONENT");
		btnAddNew.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnAddNew.setBounds(10, 533, 196, 35);
		getContentPane().add(btnAddNew);

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
		comboxFilter.addActionListener(this);
		btnLogOut.addActionListener(this);
		btnBuy.addActionListener(this);
		btnAddNew.addActionListener(this);
		btnRemove.addActionListener(this);
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
		if (e.getSource() == btnClose) {
			this.dispose();
		}
		// Detects when new option of order is choosed
		if (e.getSource()==comboxFilter) {
			
		}
		// Opens the window for the Check out
		if (e.getSource() == btnBuy) {
			if(!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				boolean type = false;  // true = Product | false = Component
				CheckOutWindow checkOut = new CheckOutWindow(this, cont);
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to buy");
			}
		}
		// Opens the window to add a new component
		if (e.getSource()==btnAddNew) {			
			boolean type = false;  // true = Product | false = Component
			AddNewWindow addNew = new AddNewWindow(this, cont);
			addNew.setVisible(true);
		}
		// Opens the window to delete
		if (e.getSource()==btnRemove) {
			if (!listName.isSelectionEmpty()) { // If there is an item selected it will do the action
				boolean type = false;  // true = Product | false = Component
				VerificationWindow checkOut = new VerificationWindow(this, cont);
				checkOut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "[ERROR] Select an item to delete");
			}
		}
	}
}