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
            if (resultado.next() && resultado.getString(1).equals("Admin")) {
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
