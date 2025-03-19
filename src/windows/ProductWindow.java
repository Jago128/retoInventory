package windows;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.LoginController;
import model.User;

// SHOW PRODUCT WINDOW  
// Go to->(CheckOutWindow, NewItemWindow, VerificationWindow)
// Back to->(MenuWindow)
public class ProductWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnCerrar;
	private JList<String> list;
	private Map<String, User> usuarios;
	private JLabel lblTitulo;
	private final JPanel contentPanel = new JPanel();
	private LoginController cont;
	private boolean admin;

	public ProductWindow(JFrame parent, boolean admin, LoginController cont) {
		super(parent,true); //Recibe la ventana padra para bloquearla y que no se pueda modificar
		this.cont=cont;

		setTitle("MOSTRAR USUARIOS");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblTitulo = new JLabel("Lista de Usuarios");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTitulo.setBounds(68, 11, 313, 43);
		getContentPane().add(lblTitulo);
		
		list = new JList<String>();
		list.setBounds(10, 64, 416, 168);
		getContentPane().add(list);
		cargarUsuarios();
		
		btnCerrar = new JButton("CERRAR");
		btnCerrar.setBounds(356, 242, 80, 21);
		btnCerrar.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		getContentPane ().add(btnCerrar);
				
		btnCerrar.addActionListener(this);	
	}

	public void cargarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		
		usuarios = cont.consultaUsuarios();
		if(!usuarios.isEmpty()) {
			for (Usuario u : usuarios.values()){
				modelo.addElement(u.getNombre());
			}
		}
		list.setModel(modelo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnCerrar) {
			this.dispose();
		}
	}
}