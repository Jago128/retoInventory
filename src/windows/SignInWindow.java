package windows;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;

// CREATE NEW USER WINDOW 
// Go to->(MenuWindow)
// Back to->(MainWindow)
public class SignInWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private LoginController cont;

	public SignInWindow(LoginController controlador) {
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
