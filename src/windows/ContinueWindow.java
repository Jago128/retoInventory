package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.LoginController;
import model.User;

// CONTINUE WINDOW
// Go to->(ProductWindow/ComponentWindow/BrandWindow)
// Back to->(MenuWindow)
public class ContinueWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel lblTitle;
	private JButton btnYes, btnNo;
	private LoginController cont;
	private User user;

	/*****[WINDOW CREATION]**************************************************************************************************/
	
	public ContinueWindow(JDialog parent, LoginController cont, User user, String name, boolean type) {
		super(parent, true); // Blocks the father window
		this.cont = cont;
		this.user = user;

		// Window
		setTitle("Verify action");
		setBounds(100, 100, 450, 194);
		getContentPane().setLayout(null);

		// Titles
		lblTitle = new JLabel(name+", do you want to keep buying "+verifyType(type)+"?");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 32, 416, 37);
		getContentPane().add(lblTitle);

		// Buttons
		btnYes = new JButton("YES");
		btnYes.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnYes.setBounds(10, 80, 170, 57);
		getContentPane().add(btnYes);

		btnNo = new JButton("NO");
		btnNo.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNo.setBounds(256, 79, 170, 57);
		getContentPane().add(btnNo);

		// Adding action listener
		btnYes.addActionListener(this);
		btnNo.addActionListener(this);
	}
	
	/*****[METHODS]*********************************************************************************************************/
	
	// Verifying the type true = Product | false = Component
	public String verifyType(boolean type) {
		if (type) {
			return "products";
		} else {
			return "components";
		}
	}	

	/*****[ACTION PERFORMER]**************************************************************************************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Goes back to to ProductWindow, ComponentWindow or BrandWindow
		if (e.getSource() == btnYes) {
			JDialog parent = (JDialog)this.getParent(); // Obtains the parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		} 
		// Goes back to the MenuWindow closing the windows in between
		if (e.getSource() == btnNo) {
			MenuWindow menu = new MenuWindow(user, cont); // The admin variable is sent to show or not certain option in the next windows			
			menu.setVisible(true);
			JDialog parent = (JDialog)this.getParent(); // Obtains the parent window			
			JDialog parentsParent = (JDialog)parent.getParent(); // Obtains the parents parent window	
			parentsParent.dispose(); // Closes the parents parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		} 
	}
}
