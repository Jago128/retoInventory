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


	// SQL queries for the methods in Java
	final String SQLINSERTPROD = "INSERT INTO PRODUCT (NAMEP, TYPEP, PRICE, STOCK, CODBRAND) VALUES (?, ?, ?, ?, ?)";
	final String SQLINSERTCOMP = "INSERT INTO COMPONENT (NAMECOMP, TYPEC, PRICECOMP, CODBRAND) VALUES (?, ?, ?, ?)";
	final String SQLDELETE = "DELETE FROM PRODUCT WHERE CODPRODUCT=(SELECT CODPRODUCT FROM PRODUCT WHERE NAMEP = ?)";

	//Selects
	final String SQLUSER = "SELECT * FROM user WHERE coduser = ?";
	final String SQLUSERPSW = "SELECT * FROM user WHERE coduser = ? AND psw = ?";
	final String SQLPROD = "SELECT PROD FROM PRODUCT WHERE NAMEP = ?";
	final String SQLTYPE = "SELECT type_u FROM user WHERE coduser = ?";
	final String SQLSELL = "SELECT sellAndSubstract(?,?,?)";

	
	final String SQLSELECTPRODUCT = "SELECT * FROM product";
	final String SQLSELECTCOMPONENT = "SELECT * FROM component";
	final String SQLSELECTBRAND = "SELECT * FROM brand";
	
	/* Queries to use as reference, to be deleted later
	 * final String SQLINSERT = "INSERT INTO user VALUES (?,?)";
	 * final String SQLCONSULTA = "SELECT * FROM user";
	 * final String SQLBORRAR = "DELETE FROM user WHERE coduser=?";
	 * final String SQLMODIFY = "UPDATE user SET psw=? WHERE coduser=?" */
	
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
			System.out.println("Error when attempting to open the DB.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Verify that the user exists
	@Override
	public boolean verifyUser(User user) {
		//Open connection and declare a boolean to check if the user exists
		boolean exists=false;
		this.openConnection();

		try {
			//Prepares the SQL query
			stmt = con.prepareStatement(SQLUSER);
			stmt.setString(1, user.getCodU());
			//Executes the SQL query
			ResultSet rs = stmt.executeQuery();
			//If there is any result, the user exists
			if (rs.next()) {
				exists = true;
			}
			//Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("[Error]" + e.getMessage());
		}
		return exists;
	}

	//Verify that the user and the password exist and matches
	@Override
	public boolean verifyUserPassword(User user) {
		//Open connection and declare a boolean to check if the password exists and matches
		boolean exists=false;
		this.openConnection();

		try {
			//Prepares the SQL query
			stmt = con.prepareStatement(SQLUSERPSW);
			stmt.setString(1, user.getCodU());
			stmt.setString(2, user.getPassword());
			//Executes the SQL query
			ResultSet rs = stmt.executeQuery();
			//If there is any result, the password exists and matches properly
			if (rs.next()) {
				exists = true;
			}
			//Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("[Error]" + e.getMessage());
		}
		return exists;
	}

	//Verify the user type (only used once the user is verified)
	@Override
	public boolean verifyUserType(User user) {
		//Open connection and declare a boolean to check if the user is an admin
		boolean admin=false;
		this.openConnection();

		try {
			//Prepares the SQL query
			stmt = con.prepareStatement(SQLTYPE);
			stmt.setString(1, user.getCodU());
			//Executes the SQL query
			ResultSet rs = stmt.executeQuery();
			//If there is any result, the user exists, and they are an admin
			if (rs.next() && rs.getString(1).equals("Admin")) {
            	admin = true;
            }
			//Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("[Error]" + e.getMessage());
		}
		return admin;
	}	
	
	//Verify that the product exists
	@Override
	public Map<String, Product> verifyProduct() {		
		ResultSet rs = null;
		Product product;
		Map<String, Product> products = new TreeMap<>();
		
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTPRODUCT);

			rs = stmt.executeQuery();

			while (rs.next()) {
				product = new Product();
				product.setNameP(rs.getString("namep"));
				product.setPrice(rs.getDouble("price"));
				products.put(product.getNameP(), product);
			}
			rs.close();
            stmt.close();
            con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return products;
	}
	
	//Verify that the component exists 
	@Override
	public Map<String, Comp> verifyComponent(){
		ResultSet rs = null;
		Comp component;
		Map<String, Comp> components = new TreeMap<>();
		
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTCOMPONENT);

			rs = stmt.executeQuery();

			while (rs.next()) {
				component = new Comp();
				component.setNameC(rs.getString("namecomp"));
				component.setPrice(rs.getDouble("pricecomp"));
				components.put(component.getNameC(), component);
			}
			rs.close();
            stmt.close();
            con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		
		return components;
	}
	
	//Verify that the brand exists 
	public Map<String, Brand> verifyBrandsAndItsItems(){
		ResultSet rs = null;
		Brand brand;
		Map<String, Brand> brands = new TreeMap<>();
		try {
			//Prepares the SQL query
			stmt = con.prepareStatement(SQLSELECTBRAND);
			rs = stmt.executeQuery();
			while (rs.next()) {
				brand = new Brand();
				brand.setNameB("NAMEBRAND");
				brands.put(brand.getNameB(), brand);
			}
			//Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return brands;
	}
	
	//Inserts a new product
	@Override
	public boolean insertProd(Product prod) {
		//Open connection and declare a boolean to check if the update is properly executed
		boolean check=false;
		this.openConnection();

		try {
			//Prepares the SQL query
			stmt = con.prepareStatement(SQLINSERTCOMP);
			stmt.setString(1, prod.getNameP());
			stmt.setObject(2, prod.getTypeP());
			stmt.setDouble(3, prod.getPrice());
			stmt.setInt(4, prod.getStock());
			stmt.setInt(5, prod.getCodBrand());
			//Executes the SQL query. If the insert is executed correctly, check becomes true
			if (stmt.executeUpdate()>0) {
				check=true;
			}
			//Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	// Insert new Components to the database.
	@Override
	public boolean insertComp(Comp comp) {
		//Open connection and declare a boolean to check if the update is properly executed
		boolean check=false;

		this.openConnection();
		try {
			//Prepares the SQL query
			stmt = con.prepareStatement(SQLINSERTPROD);
			stmt.setString(1, comp.getNameC());
			stmt.setObject(2, comp.getTypeC());
			stmt.setDouble(3, comp.getPrice());
			stmt.setInt(4, comp.getCodBrand());
			//Executes the SQL query. If the insert is executed correctly, check becomes true
			if (stmt.executeUpdate()>0) {
				check=true;
			}
			//Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	//Substracts from a product's stock, essentilly selling the product to the user
	@Override
	public boolean sellAndSubstract(String codUser, String nomProd, int amount) {
		//Open connection and declare a boolean to check if the update is properly executed
		boolean check=false;
		
		this.openConnection();
		try {
			//Prepares the SQL query to get the product		
			stmt = con.prepareStatement(SQLSELL);
			stmt.setString(1, codUser);
			stmt.setString(2, nomProd);
			stmt.setInt(3, amount);
			ResultSet rs = stmt.executeQuery();
			//Get the errorcheck boolean to see if there was a problem during the update or not
			check=rs.getBoolean(1);
			//Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	@Override
	public void showProdsOrderedByStock() {
		
	}

	@Override
	public boolean deleteProd(String nom) {
		//Open connection and declare a boolean to check if the update is properly executed
		boolean check=false;
		this.openConnection();
		try {
			//Prepares the SQL query
			stmt=con.prepareStatement(SQLDELETE);
			stmt.setString(1, nom);
			//Executes the SQL query. If the delete is executed correctly, check becomes true
			if (stmt.executeUpdate()>0) {
				check=true;
			}
			//Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
}
