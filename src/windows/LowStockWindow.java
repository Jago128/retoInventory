package windows;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;

// SHOW LOW STOCK WINDOW  
// Go to->(ReestockWindow)
// Back to->(CheckOutWindow, NewItemWindow, VerificationWindow)
public class LowStockWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public LowStockWindow(JFrame parent, LoginController controlador) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
