package windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;

/* CONTINUE WINDOW
 * Go to->(ProductWindow/ComponentWindow/BrandWindow)
 * Back to->(MenuWindow)*/
public class ContinueWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel lblTitle;
	private JButton btnYes, btnNo;
	private JLabel logo;

	/**[WINDOW CREATION]*/

	public ContinueWindow(JDialog parent, User user, boolean type) {
		super(parent, true); // Blocks the father window
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignInWindow.class.getResource("/img/MediaMartaLogoB.png")));

		// Window
		setTitle("Verify action");
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.RED);
		setResizable(false); // Blocks the window so it can't be modified the size

		// Titles
		lblTitle = new JLabel(user.getUsername() + ", do you want to keep buying "+verifyType(type)+"?");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 44, 416, 37);
		getContentPane().add(lblTitle);

		// Buttons
		btnYes = new JButton("YES");
		btnYes.setForeground(Color.WHITE);
		btnYes.setBackground(Color.BLACK);
		btnYes.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnYes.setBounds(28, 107, 170, 57);
		getContentPane().add(btnYes);

		btnNo = new JButton("NO");
		btnNo.setBackground(Color.BLACK);
		btnNo.setForeground(Color.WHITE);
		btnNo.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNo.setBounds(235, 107, 170, 57);
		getContentPane().add(btnNo);
		
		// Logo
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(SignInWindow.class.getResource("/img/MediaMartaLogoW.png")));
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Serif", Font.BOLD, 100));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(110, 105, 217, 217);
		getContentPane().add(logo);

		// Adding action listener
		btnYes.addActionListener(this);
		btnNo.addActionListener(this);
	}

	/**[METHODS]**/

	// Verifying the type true = Product | false = Component
	public String verifyType(boolean type) {
		if (type) {
			return "products";
		} else {
			return "components";
		}
	}

	// Refresh parent window list
	public void refreshParentList() {
		JDialog parent = (JDialog)this.getParent(); // Obtains the parent window
		if (parent instanceof ProductWindow) { // Checks the parent window type
			ProductWindow productWindow = (ProductWindow)parent; // Cast it to its type to be able to use it's methods
			productWindow.loadProductsList(); // Calls the parent method to reload the list
		} else if (parent instanceof ComponentWindow) {
			ComponentWindow productWindow = (ComponentWindow)parent;
			productWindow.loadComponentList();
		} else if (parent instanceof BrandWindow) {
			BrandWindow productWindow = (BrandWindow)parent;
			productWindow.loadList();
		} else if (parent instanceof LowStockWindow) {
			LowStockWindow lowStockWindow = (LowStockWindow)parent;
			lowStockWindow.loadList();
		}
	}

	/**[ACTION PERFORMER]**/

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
			JDialog parent = (JDialog)this.getParent(); // Obtains the parent window
			JDialog parentsParent = (JDialog)parent.getParent(); // Obtains the parents parent window
			parentsParent.dispose(); // Closes the parents parent window
			parent.dispose(); // Closes the parent window
			this.dispose();
		}
	}
}
