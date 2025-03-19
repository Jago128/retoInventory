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
	
	// SQL queries for the methods in Java.
	final String sqlInsertProd = "INSERT INTO PRODUCT (NAMEP, TYPEP, PRICE, STOCK, CODBRAND) VALUES (?, ?, ?, ?, ?)";
	final String sqlInsertComp = "INSERT INTO COMPONENT (NAMECOMP, TYPEC, PRICECOMP, CODBRAND) VALUES (?, ?, ?, ?)";
	
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
	
	// Inserts New Products to the database.
	@Override
	public boolean insertProd(Product prod) {
		//[PH]
		boolean check=false;
		this.openConnection();
		
		try {
			stmt = con.prepareStatement(sqlInsertProd);;
			stmt.setString(1, prod.getNameP());
			stmt.setObject(2, prod.getTypeP());
			stmt.setDouble(3, prod.getPrice());
			stmt.setInt(4, prod.getStock());
			stmt.setInt(5, prod.getCodBrand());
			if (stmt.executeUpdate()>0) {
				check=true;
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	// Insert New Components to the database.
	@Override
	public boolean insertComp(Component comp) {
		boolean check=false;
		
		this.openConnection();
		try {
			stmt = con.prepareStatement(sqlInsertProd);
			stmt.setString(1, comp.getNameC());
			stmt.setObject(2, comp.getTypeC());
			stmt.setDouble(3, comp.getPrice());
			stmt.setInt(4, comp.getCodBrand());
			if (stmt.executeUpdate()>0) {
				check=true;
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
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
	public void showProd() {
		
	}

	@Override
	public void showBrandsAndItsItems() {
		
	}

	@Override
	public void showComponents() {
		
	}
}
