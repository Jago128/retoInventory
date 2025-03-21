package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;
import model.Comp;

// SHOW BY BRAND WINDOW  
// Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
// Back to->(MenuWindow)
public class BrandWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoginController cont;
	private JLabel lblMediaMarta, lblBrands;
	private JButton btnLogOut, btnBuy, btnRemove,btnClose;
	private JList<String> list;
	private Map<String, Comp> components;	
	private boolean admin;

	public BrandWindow(JFrame parent, boolean admin, LoginController cont) {
		super(parent,true); //Blocks the father window
		this.cont=cont;
		this.admin=admin;

		//Window
		setTitle("MEDIAMARTA: Brands");
		setBounds(100, 100, 480, 636);
		getContentPane().setLayout(null);

		//Titles
		lblMediaMarta = new JLabel("MediaMarta");
		lblMediaMarta.setHorizontalAlignment(SwingConstants.CENTER);
		lblMediaMarta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblMediaMarta.setBounds(5, 8, 461, 46);
		getContentPane().add(lblMediaMarta);

		lblBrands = new JLabel("BRANDS");
		lblBrands.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrands.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBrands.setBounds(5, 42, 461, 19);
		getContentPane().add(lblBrands);

		//List
		list = new JList<String>();
		list.setBounds(10, 238, 446, 274);
		getContentPane().add(list);
		loadBrands();

		//Buttons
		btnLogOut = new JButton("Log-Out");
		btnLogOut.setBackground(new Color(240, 240, 240));
		btnLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnLogOut.setBounds(385, 8, 81, 21);
		getContentPane().add(btnLogOut);

		btnBuy = new JButton("BUY");
		btnBuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBuy.setBounds(10, 533, 196, 35);
		if(admin) {
			// If not the user will not have this option visible
			btnBuy.setVisible(false);
		}
		else { // In case the user is admin the button will be visible
			btnBuy.setVisible(true);
		}
		getContentPane().add(btnBuy);

		btnRemove = new JButton("REMOVE");
		btnRemove.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRemove.setBounds(260, 533, 196, 35);
		if(admin) { // In case the user is admin the button will be visible
			btnRemove.setVisible(true);
		}
		else { // If not the user will not have this option visible
			btnRemove.setVisible(false);
		}
		getContentPane().add(btnRemove);

		btnClose = new JButton("CLOSE");
		btnClose.setBounds(399, 578, 67, 21);
		btnClose.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane ().add(btnClose);

		//Adding action listener
		btnBuy.addActionListener(this);
		btnRemove.addActionListener(this);
		btnClose.addActionListener(this);	
	}
	
	//Action performer
	@Override
	public void actionPerformed(ActionEvent e) {
		// Logs-Out and moves back to the Main Window	
		if (e.getSource()==btnLogOut) {
			MainWindow main=new  MainWindow(cont);
			main.setVisible(true);
			this.dispose();
		}
	}
}