package model;

import java.sql.*;
import java.util.*;

public class DBImplementation implements MediaMartaDAO {
	// Prepare statement variables
	private Connection con;
	private PreparedStatement stmt;

	// Prepare SQL Connection variables (with a supressed warning, as it's kind of annoying to see)
	private ResourceBundle configFile;
	@SuppressWarnings("unused")
	private String driverBD;
	private String urlBD;
	private String userBD;
	private String passwordBD;

	// SQL queries for the methods in Java
	// User related stuff
	final String SQLUSER = "SELECT * FROM user WHERE coduser = ?";
	final String SQLUSERPSW = "SELECT * FROM user WHERE coduser = ? AND psw = ?";	
	final String SQLTYPE = "SELECT type_u FROM user WHERE coduser = ?";
	final String SQLINSERTUSER = "INSERT INTO user VALUES (?,?,?,'Client')";

	// Product, Component, and Brand related stuff
	final String SQLSELL = "SELECT sellAndSubstract(?,?,?,?,?)";
	
	// PRODUCT
	final String SQLSELECTPRODUCT = "SELECT * FROM product";
	final String SQLINSERTPROD = "INSERT INTO PRODUCT (NAMEP, TYPEP, PRICE, STOCK, CODBRAND) VALUES (?, ?, ?, ?, ?)";
	final String SQLDELETEPROD = "CALL deleteProduct(?)";	
	final String SQLSELECTPRODUCTNAMEPRICE = "SELECT nameP, price FROM product WHERE nameP = ?";
	final String SQLPROD = "SELECT PROD FROM PRODUCT WHERE NAMEP = ?";

	// COMPONENT
	final String SQLSELECTCOMPONENT = "SELECT * FROM component";
	final String SQLINSERTCOMP = "INSERT INTO COMPONENT (NAMECOMP, TYPEC, PRICECOMP, CODBRAND) VALUES (?, ?, ?, ?)";	
	final String SQLDELETECOMP = "DELETE FROM component WHERE codComponent=(SELECT codComponent FROM component WHERE nameComp = ?)";	
	final String SQLSELECTCOMPONENTNAMEPRICE = "SELECT nameComp, priceComp FROM component WHERE nameComp = ?";

	// BRAND
	final String SQLSELECTBRAND = "SELECT * FROM brand";
	final String SQLSELECTPRODUCTBRAND = "SELECT * FROM product WHERE CODBRAND=(SELECT CODBRAND FROM BRAND WHERE NAMEBRAND=?)";
	final String SQLSELECTCOMPONENTBRAND = "SELECT * FROM component WHERE CODBRAND=(SELECT CODBRAND FROM BRAND WHERE NAMEBRAND=?)";

	// [Declare implementation constructor]
	public DBImplementation() {
		this.configFile = ResourceBundle.getBundle("model.classConfig");
		this.driverBD = this.configFile.getString("Driver");
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");
	}

	// [Method to open a new connection]
	private void openConnection() {
		try {
			// Try opening the connection
			con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
		} catch (SQLException e) {
			System.out.println("Error when attempting to open the DB.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// [Registers a new user]
	public boolean registerUser(User user) {
		boolean register = false;
		if (!verifyUser(user)) {
			this.openConnection();
			try {
				stmt = con.prepareStatement(SQLINSERTUSER);
				stmt.setString(1, user.getCodU());
				stmt.setString(2, user.getUsername());
				stmt.setString(3, user.getPassword());
				if (stmt.executeUpdate() > 0) {
					register = true;
				}
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Error" + e.getMessage());
			}
		}
		return register;
	}
	
	// [Verify that the user exists]
	@Override
	public boolean verifyUser(User user) {
		// Open connection and declare a boolean to check if the user exists
		boolean exists = false;
		this.openConnection();

		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLUSER);
			stmt.setString(1, user.getCodU());
			// Executes the SQL query
			ResultSet rs = stmt.executeQuery();
			// If there is any result, the user exists
			if (rs.next()) {
				exists = true;
			}
			// Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("[Error]" + e.getMessage());
		}
		return exists;
	}

	// [Verify that the user and the password exist and matches]
	@Override
	public boolean verifyUserPassword(User user) {
		// Open connection and declare a boolean to check if the password exists and matches
		boolean exists = false;
		this.openConnection();

		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLUSERPSW);
			stmt.setString(1, user.getCodU());
			stmt.setString(2, user.getPassword());
			// Executes the SQL query
			ResultSet rs = stmt.executeQuery();
			// If there is any result, the password exists and matches properly
			if (rs.next()) {
				exists = true;
			}
			// Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("[Error]" + e.getMessage());
		}
		return exists;
	}

	// [Verify the user type (only used once the user is verified)]
	@Override
	public boolean verifyUserType(User user) {
		// Open connection and declare a boolean to check if the user is an admin
		boolean admin = false;
		this.openConnection();

		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLTYPE);
			stmt.setString(1, user.getCodU());
			// Executes the SQL query
			ResultSet rs = stmt.executeQuery();
			// If there is any result, the user exists, and they are an admin
			if (rs.next() && rs.getString(1).equals("Admin")) {
				admin = true;
			}
			// Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("[Error]" + e.getMessage());
		}
		return admin;
	}

	// Substracts from a item's stock, essentilly selling the product to the user, and makes a new entry in Purchase
	@Override
	public String sellAndSubstract(String codUser, String nomItem, int amount, double price, boolean type) { 
		// Open connection and declare a boolean to check if the update is properly executed
		String check = null;

		this.openConnection();
		try {
			// Prepares the SQL query to get the product
			stmt = con.prepareStatement(SQLSELL);
			stmt.setString(1, codUser);
			stmt.setString(2, nomItem);
			stmt.setInt(3, amount);
			stmt.setDouble(4, price);
			stmt.setBoolean(5, type); // true = Product | false = Component
			ResultSet rs = stmt.executeQuery();
			check = rs.getString(1);
			// Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}	
	
	// Inserts a new product
	@Override
	public boolean insertProd(Product prod) {
		// Open connection and declare a boolean to check if the update is properly executed
		boolean check = false;
		this.openConnection();

		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLINSERTPROD);
			stmt.setString(1, prod.getNameP());
			stmt.setObject(2, prod.getTypeP());
			stmt.setDouble(3, prod.getPrice());
			stmt.setInt(4, prod.getStock());
			stmt.setInt(5, prod.getCodBrand());
			// Executes the SQL query. If the insert is executed correctly, check becomes true
			if (stmt.executeUpdate() > 0) {
				check = true;
			}
			// Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	// [Verify that the product exists, and show them]
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

	// [Obtain a product's name and price, based on the name of the product provided]
	public Product obtainProductNamePrice(String name) {
		ResultSet rs = null;
		Product product = new Product();

		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTPRODUCTNAMEPRICE);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				product.setNameP(rs.getString("namep"));
				product.setPrice(rs.getDouble("price"));
			}			
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return product;
	}

	// Delete a product
	@Override
	public boolean deleteProd(String nom) {
		// Open connection and declare a boolean to check if the update is properly executed
		ResultSet rs = null;
		boolean check = false;
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLDELETEPROD);
			stmt.setString(1, nom);
			// Executes the SQL query. If the delete is executed correctly, check becomes true
			rs = stmt.executeQuery();
			if (rs.next()) {
				check = true;
			}
			// Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	// Shows products with a stock of 5 or less, ordered by stock
	@Override
	public Map<Integer, Product> showProdsOrderedByStock() {

		return null;
	}

	// Inserts a new component into the database
	@Override
	public boolean insertComp(Comp comp) {
		// Open connection and declare a boolean to check if the update is properly executed
		boolean check = false;

		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLINSERTCOMP);
			stmt.setString(1, comp.getNameC());
			stmt.setObject(2, comp.getTypeC());
			stmt.setDouble(3, comp.getPrice());
			stmt.setInt(4, comp.getCodBrand());
			// Executes the SQL query. If the insert is executed correctly, check becomes true
			if (stmt.executeUpdate() > 0) {
				check = true;
			}
			// Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	// [Verify that the component exists, and show them]
	@Override
	public Map<String, Comp> verifyComponent() {
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

	// [Obtain choosed component's name and price]
	public Comp obtainComponentNamePrice(String name) {
		ResultSet rs = null;
		Comp component = new Comp();

		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTCOMPONENTNAMEPRICE);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				component.setNameC(rs.getString("nameComp"));
				component.setPrice(rs.getDouble("priceComp"));
			}			
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return component;
	}
	
	// Delete a product
	@Override
	public boolean deleteComp(String nom) {
		// Open connection and declare a boolean to check if the update is properly executed
		boolean check = false;
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLDELETECOMP);
			stmt.setString(1, nom);
			// Executes the SQL query. If the delete is executed correctly, check becomes true
			if (stmt.executeUpdate() > 0) {
				check = true;
			}
			// Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	// Shows components with a stock of 5 or less, ordered by stock
	@Override
	public Map<Integer, Comp> showCompsOrderedByStock() {

		return null;
	}

	// Verify that the brand exists, and prepare a map to use later
	public Map<String, Brand> verifyBrands() {
		ResultSet rs = null;
		Brand brand;
		Map<String, Brand> brands = new TreeMap<>();
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLSELECTBRAND);
			rs = stmt.executeQuery();
			while (rs.next()) {
				brand = new Brand();
				brand.setCodB(rs.getInt("CODBRAND"));
				brand.setNameB("NAMEBRAND");
				brands.put(brand.getNameB(), brand);
			}
			// Closes the connection
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return brands;
	}

	// Show products of a brand
	@Override
	public Map<String, Product> showProductsBrand(String brand) {
		Map<String, Product> brandProds = new TreeMap<>();
		ResultSet rs = null;
		Product product;

		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTPRODUCTBRAND);
			stmt.setString(1, brand);
			rs = stmt.executeQuery();
			while (rs.next()) {
				product = new Product();
				product.setNameP(rs.getString("namep"));
				product.setPrice(rs.getDouble("price"));
				brandProds.put(product.getNameP(), product);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return brandProds;
	}

	// Show components of a brand
	@Override
	public Map<String, Comp> showComponentsBrand(String brand) {
		Map<String, Comp> brandComps = new TreeMap<>();
		ResultSet rs = null;
		Comp component;

		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTCOMPONENTBRAND);
			stmt.setString(1, brand);
			rs = stmt.executeQuery();
			while (rs.next()) {
				component = new Comp();
				component.setNameC(rs.getString("namecomp"));
				component.setPrice(rs.getDouble("pricecomp"));
				brandComps.put(component.getNameC(), component);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return brandComps;
	}
}
