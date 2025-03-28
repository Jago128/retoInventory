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

	// PRODUCT
	final String SQLSELECTPRODUCT = "SELECT * FROM product";
	final String SQLSELECTPRODUCTSTOCK = "SELECT * FROM product WHERE STOCKPRODUCT<=50 ORDER BY STOCKPRODUCT";
	final String SQLPRODUCTSTOCK = "SELECT STOCKPRODUCT FROM product WHERE nameP = ?";
	final String SQLINSERTPROD = "INSERT INTO PRODUCT (NAMEP, TYPEP, PRICE, STOCKPRODUCT, CODBRAND) VALUES (?,?,?,?,?)";
	final String SQLDELETEPROD = "DELETE FROM PRODUCT WHERE NAMEP = ?";
	final String SQLSELECTPRODUCTNAMEPRICE = "SELECT nameP, price FROM product WHERE nameP = ?";
	final String SQLPROD = "SELECT PROD FROM PRODUCT WHERE NAMEP = ?";
	final String SQLRESTOCKPRODUCT = "";

	// COMPONENT
	final String SQLSELECTCOMPONENT = "SELECT * FROM component";
	final String SQLSELECTCOMPSTOCK = "SELECT * FROM component WHERE STOCKCOMPONENT<=50 ORDER BY STOCKCOMPONENT";
	final String SQLCOMPSTOCK = "SELECT STOCKCOMPONENT FROM component WHERE nameComp = ?";
	final String SQLINSERTCOMP = "INSERT INTO COMPONENT (NAMECOMP, TYPEC, STOCKCOMPONENT, PRICECOMP, CODBRAND) VALUES (?,?,?,?,?)";
	final String SQLDELETECOMP = "DELETE FROM COMPONENT WHERE NAMECOMP = ?";
	final String SQLSELECTCOMPONENTNAMEPRICE = "SELECT nameComp, priceComp FROM component WHERE nameComp = ?";
	final String SQLRESTOCKCOMPONENT = "";

	// Product and Component related stuff
	final String SQLSELL = "SELECT sellAndSubstract(?,?,?,?,?)";

	// BRAND
	final String SQLSELECTBRAND = "SELECT * FROM brand";
	final String SQLSELECTBRANDCODE = "SELECT CODBRAND FROM BRAND WHERE NAMEBRAND = ?";
	final String SQLSELECTPRODUCTBRAND = "SELECT * FROM product WHERE CODBRAND=(SELECT CODBRAND FROM BRAND WHERE NAMEBRAND=?)";
	final String SQLSELECTCOMPONENTBRAND = "SELECT * FROM component WHERE CODBRAND=(SELECT CODBRAND FROM BRAND WHERE NAMEBRAND=?)";

	// Declare implementation constructor
	public DBImplementation() {
		this.configFile = ResourceBundle.getBundle("model.classConfig");
		this.driverBD = this.configFile.getString("Driver");
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");
	}

	// Method to open a new connection
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

	// Registers a new user
	@Override
	public boolean registerUser(User user) {
		boolean register = false;
		if (!verifyUser(user)) {
			this.openConnection();
			try {
				stmt = con.prepareStatement(SQLINSERTUSER);
				stmt.setString(1, user.getCodU());
				stmt.setString(2, user.getUsername());
				stmt.setString(3, user.getPassword());
				if (stmt.executeUpdate()>0) {
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

	// Verify that the user exists
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

	// Verify that the user and the password exist and matches
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

	// Verify the user type (only used once the user is verified)
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
			if (prod.getTypeP() == TypeP.MOBILE) {
				stmt.setString(2, "Mobile");
			} else {
				stmt.setString(2, "Computer");
			}
			stmt.setDouble(3, prod.getPrice());
			stmt.setInt(4, prod.getStock());
			stmt.setInt(5, prod.getCodBrand());
			// Executes the SQL query. If the insert is executed correctly, check becomes true
			if (stmt.executeUpdate()>0) {
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

	// Verify that the product exists, and show them
	@Override
	public Map<String, Product> verifyProduct() {
		ResultSet rs = null;
		Product product;
		Map<String, Product> products = new TreeMap<>();

		// Opens the connection
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

	// Obtain a product's name and price, based on the name of the product provided
	@Override
	public Product obtainProductNamePrice(String name) {
		ResultSet rs = null;
		Product product = new Product();

		// Opens the connection
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
		boolean check = false;

		// Opens the connection
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLDELETEPROD);
			stmt.setString(1, nom);
			// Executes the SQL query. If the delete is executed correctly, check becomes true
			if (stmt.executeUpdate()>0) {
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

	// Shows products with a stock of 50 or less, ordered by stock
	@Override
	public Map<String, Product> showProdsOrderedByStock() {
		Map<String, Product> prods = new HashMap<>();
		ResultSet rs = null;
		Product product;

		// Opens the connection
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLSELECTPRODUCTSTOCK);
			// Executes the SQL query. If the delete is executed correctly, check becomes true
			rs = stmt.executeQuery();
			while (rs.next()) {
				product = new Product();
				product.setNameP(rs.getString("NAMEP"));
				product.setTypeP(TypeP.valueOf(rs.getString("TYPEP").toUpperCase())); // Needs to set to UpperCase because in the database is in lowercase
				product.setPrice(rs.getDouble("PRICE"));
				product.setStock(rs.getInt("STOCKPRODUCT"));
				product.setCodBrand(rs.getInt("CODBRAND"));
				prods.put(product.getNameP(), product);
			}
			// Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prods;
	}

	// Inserts a new component into the database
	@Override
	public boolean insertComp(Comp comp) {
		// Open connection and declare a boolean to check if the update is properly executed
		boolean check = false;

		// Opens the connection
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLINSERTCOMP);
			stmt.setString(1, comp.getNameC());
			if (comp.getTypeC() == TypeC.GRAPHICS) {
				stmt.setString(2, "Graphics");
			} else if (comp.getTypeC() == TypeC.RAM) {
				stmt.setString(2, "RAM");
			} else if (comp.getTypeC() == TypeC.PROCESSOR) {
				stmt.setString(2, "Processor");
			}
			stmt.setInt(3, comp.getStock());
			stmt.setDouble(4, comp.getPrice());
			stmt.setInt(5, comp.getCodBrand());
			// Executes the SQL query. If the insert is executed correctly, check becomes true
			if (stmt.executeUpdate()>0) {
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

	// Verify that the component exists, and show them
	@Override
	public Map<String, Comp> verifyComponent() {
		ResultSet rs = null;
		Comp component;
		Map<String, Comp> components = new TreeMap<>();

		// Opens the connection
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

	// Obtain choosed component's name and price
	@Override
	public Comp obtainComponentNamePrice(String name) {
		ResultSet rs = null;
		Comp component = new Comp();

		// Opens the connection
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

		// Opens the connection
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLDELETECOMP);
			stmt.setString(1, nom);
			// Executes the SQL query. If the delete is executed correctly, check becomes true
			if (stmt.executeUpdate()>0) {
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

	// Shows components with a stock of 50 or less, ordered by stock
	@Override
	public Map<String, Comp> showCompsOrderedByStock() {
		Map<String, Comp> comps = new HashMap<>();
		ResultSet rs = null;
		Comp comp;

		// Opens the connection
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLSELECTCOMPSTOCK);
			// Executes the SQL query. If the delete is executed correctly, check becomes true
			rs = stmt.executeQuery();
			while (rs.next()) {
				comp = new Comp();
				comp.setNameC(rs.getString("NAMECOMP"));
				comp.setTypeC(TypeC.valueOf(rs.getString("TYPEC").toUpperCase())); // Needs to set to UpperCase because in the database is in lowercase
				comp.setPrice(rs.getDouble("PRICECOMP"));
				comp.setStock(rs.getInt("STOCKCOMPONENT"));
				comp.setCodBrand(rs.getInt("CODBRAND"));
				comps.put(comp.getNameC(), comp);
			}
			// Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comps;
	}

	// Substracts from a item's stock, essentilly selling the product to the user, and makes a new entry in Purchase
	@Override
	public void sellAndSubstract(String codUser, String nomItem, int amount, double price, boolean type) {
		
		// Opens the connection
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLSELL);
			stmt.setString(1, codUser);
			stmt.setString(2, nomItem);
			stmt.setInt(3, amount);
			stmt.setDouble(4, price);
			stmt.setBoolean(5, type);
			stmt.executeQuery();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Checks the stock of a product
	@Override
	public int checkStock(String nomItem, boolean type) {
		int stock = 0;
		ResultSet rs = null;

		this.openConnection();
		try {
			if (type) {
				// Prepares the SQL query
				stmt = con.prepareStatement(SQLPRODUCTSTOCK);
				stmt.setString(1, nomItem);
				rs = stmt.executeQuery();
				if (rs.next()) {
					stock=rs.getInt("STOCKPRODUCT");
				}
			} else {
				// Prepares the SQL query
				stmt = con.prepareStatement(SQLCOMPSTOCK);
				stmt.setString(1, nomItem);
				rs = stmt.executeQuery();
				if (rs.next()) {
					stock=rs.getInt("STOCKCOMPONENT");
				}
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}

	// Verifies that the brand exists, and prepares a map to use later
	@Override
	public Map<String, Brand> verifyBrands() {
		ResultSet rs = null;
		Brand brand;
		Map<String, Brand> brands = new TreeMap<>();

		// Opens the connection
		this.openConnection();
		try {
			// Prepares the SQL query
			stmt = con.prepareStatement(SQLSELECTBRAND);
			rs = stmt.executeQuery();
			while (rs.next()) {
				brand = new Brand();
				brand.setCodB(rs.getInt("CODBRAND"));
				brand.setNameB(rs.getString("NAMEBRAND"));
				brands.put(brand.getNameB(), brand);
			}
			// Closes the connection
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return brands;
	}

	// Get the selected brand's code
	public int getBrandCode(String brandName) {
		ResultSet rs = null;
		int brandCode = 0;

		// Opens the connection
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTBRANDCODE);
			stmt.setString(1, brandName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				brandCode = rs.getInt("CODBRAND");
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return brandCode;
	}

	// Show products of a brand
	@Override
	public Map<String, Product> showProductsBrand(String brand) {
		Map<String, Product> brandProds = new TreeMap<>();
		ResultSet rs = null;
		Product product;

		// Opens the connection
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

		// Opens the connection
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

	// Restocks products and components
	@Override
	public boolean restock(String name, int quantity) {
		// Open connection and declare a boolean to check if the update is properly executed
		boolean check = false;

		return check;
	}
}
