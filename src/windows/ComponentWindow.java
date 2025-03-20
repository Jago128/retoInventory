package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;
import model.Comp;
import model.Product;

// SHOW COMPONENT WINDOW  
// Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
// Back to->(MenuWindow)
public class ComponentWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnCerrar;
	private JList<String> list;
	private Map<String, Comp> components;
	private JLabel lblTitulo;
	private final JPanel contentPanel = new JPanel();
	private LoginController cont;
	private boolean admin;

	public ComponentWindow(JFrame parent, boolean admin, LoginController cont) {
		super(parent,true); //Recibe la ventana padra para bloquearla y que no se pueda modificar
		this.cont=cont;
		
		setTitle("COMPONENTS");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblTitulo = new JLabel("The components in our shop");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTitulo.setBounds(68, 11, 313, 43);
		getContentPane().add(lblTitulo);
		
		list = new JList<String>();
		list.setBounds(10, 64, 416, 168);
		getContentPane().add(list);
		loadComponents();
		
		btnCerrar = new JButton("CERRAR");
		btnCerrar.setBounds(356, 242, 80, 21);
		btnCerrar.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane ().add(btnCerrar);
				
		btnCerrar.addActionListener(this);	
	}

	public void loadComponents() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		components = cont.verifyComponent();
		if(!components.isEmpty()) {
			for (Comp c : components.values()){
				model.addElement(c.getNameC());
			}
		}
		list.setModel(model);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnCerrar) {
			this.dispose();
		}
	}
}