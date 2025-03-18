package model;

import java.sql.*;
import java.util.*;

public class DBImplementation implements MediaMartaDAO {
	//Prepare statement variables
	private Connection con;
	private PreparedStatement stmt;
	
	//Prepare SQL Connection variables (with a supressed warning, as it's kind of annoying to see)
	private ResourceBundle configFile;
	@SuppressWarnings("unused")
	private String driverBD;
	private String urlBD;
	private String userBD;
	private String passwordBD;
	
	//Declare implementation constructor
	public DBImplementation() {
		this.configFile = ResourceBundle.getBundle("model.classConfig");
		this.driverBD = this.configFile.getString("Driver");
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");
	}
	
	private void openConnection() {
		try {
			//Try opening the connection
			con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
		} catch (SQLException e) {
			System.out.println("Error al intentar abrir la BD");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean insertProd(Product prod) {
		//[PH]
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
	public boolean insertComp(Component comp) {
		
		return false;
	}

	@Override
	public boolean sellAndSubstract() {
		
		return false;
	}

	@Override
	public void showProdsOrderedByStock() {
		
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
