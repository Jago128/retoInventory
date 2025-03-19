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
	final String SQLINSERTPROD = "INSERT INTO PRODUCT (NAMEP, TYPEP, PRICE, STOCK, CODBRAND) VALUES (?, ?, ?, ?, ?)";
	final String SQLINSERTCOMP = "INSERT INTO COMPONENT (NAMECOMP, TYPEC, PRICECOMP, CODBRAND) VALUES (?, ?, ?, ?)";
	final String SQLDELETE = "DELETE FROM PRODUCT WHERE CODPRODUCT=(SELECT CODPRODUCT FROM PRODUCT WHERE NAMEP = ?)";
	
	// SQL sentences
	final String SQLUSER = "SELECT * FROM user WHERE coduser = ?";
	final String SQLUSERPSW = "SELECT * FROM user WHERE coduser = ? AND psw = ?";
	final String SQLTYPE = "SELECT type_u FROM user WHERE coduser = ?";
		
	//final String SQLINSERT = "INSERT INTO user VALUES (?,?)";
	//final String SQLCONSULTA = "SELECT * FROM user";
	//final String SQLBORRAR = "DELETE FROM user WHERE coduser=?";
	//final String SQLMODIFY = "UPDATE user SET psw=? WHERE coduser=?";
	
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
	
	// Verify the user exist 
	public boolean verifyUser(User user){
		// Open connection
		boolean existe=false;
		this.openConnection();
		
		try {
			stmt = con.prepareStatement(SQLUSER);
            stmt.setString(1, user.getCodU());
            ResultSet resultado = stmt.executeQuery();

            // If there is any result, the user exists
            if (resultado.next()) {
                existe = true;
            }
            resultado.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("[Error]" + e.getMessage());
        }

        return existe;
    }
	
	// Verify the user and the password exist and matches
	public boolean verifyUserPassword(User user){
		// Open connection
		boolean existe=false;
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLUSERPSW);
            stmt.setString(1, user.getCodU());
            stmt.setString(2, user.getPassword());
            ResultSet resultado = stmt.executeQuery();

            // If there is any result, the user exists
            if (resultado.next()) {
                existe = true;
            }
            resultado.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[Error]" + e.getMessage());
        }
        return existe;
    }

	// Verify the user type (only used once the user is verified)
	public boolean verifyUserType(User user){
		// Open connection
		boolean admin=false;
		this.openConnection();
		
		try {
			stmt = con.prepareStatement(SQLTYPE);
            stmt.setString(1, user.getCodU());
            ResultSet resultado = stmt.executeQuery();

            // If there is any result, the user exists
            if (resultado.next()) {
                admin = true;
            }
            resultado.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("[Error]" + e.getMessage());
        }
        return admin;
    }	
		
	@Override
	public boolean insertProd(Product prod) {
		//[PH]
		boolean check=false;
		this.openConnection();
		
		try {
			stmt = con.prepareStatement(SQLINSERTCOMP);;
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
			stmt = con.prepareStatement(SQLINSERTPROD);
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
	public boolean deleteProd(String nom) {
		boolean check=false;
		this.openConnection();
		try {
			stmt=con.prepareStatement(SQLDELETE);
			stmt.setString(1, nom);
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

	// Shows the products from the Database MediaMarta
	@Override
	public void showProd() {
		
	}

	// Shows the products and components of each brand from the Database MediaMarta
	@Override
	public void showBrandsAndItsItems() {
		
	}

	// Shows the components from the Database MediaMarta
	@Override
	public void showComponents() {
		
	}
}
