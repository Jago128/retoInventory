package model;

import java.sql.*;
import java.util.*;

public class DBImplementation implements MediaMartaDAO {
	
	private Connection con;
	private PreparedStatement stmt;
	private ResourceBundle configFile;

	@SuppressWarnings("unused")
	private String driverBD;
	private String urlBD;
	private String userBD;
	private String passwordBD;
	
	public DBImplementation() {
		this.configFile = ResourceBundle.getBundle("modelo.classConfig");
		this.driverBD = this.configFile.getString("Driver");
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");
	}
	
	private void openConnection() {
		try {
			con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
		} catch (SQLException e) {
			System.out.println("Error al intentar abrir la BD");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean insertProd(Product p) {
		boolean check=false;
		this.openConnection();
		
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	@Override
	public boolean insertComp(Component c) {
		
		return false;
	}

	@Override
	public boolean sellAndSubstract() {
		
		return false;
	}

	@Override
	public void showProdsOrderedByStockLowerThan5AndRestock() {
		
		
	}

	@Override
	public boolean deleteProd(int cod) {
		
		return false;
	}

	@Override
	public void showProd() {
		
	}

	@Override
	public void showBrandsAndItsItems() {
		
	}

	@Override
	public void showComponents() {
		
	}

}
