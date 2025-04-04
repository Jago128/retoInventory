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

	/**[SQL QUERIES]**/

	// USERS
	final String SQLUSER = "SELECT * FROM user WHERE codUser = ?";
	final String SQLUSERPSW = "SELECT * FROM user WHERE codUser = ? AND psw = ?";
	final String SQLTYPE = "SELECT type_u FROM user WHERE codUser = ?";
	final String SQLINSERTUSER = "INSERT INTO user VALUES (?,?,?,'Client')";

	// PRODUCTS
	final String SQLSELECTPRODUCT = "SELECT * FROM product";
	final String SQLSELECTPRODUCTNAMEPRICE = "SELECT * FROM product WHERE nameP = ?";
	final String SQLINSERTPROD = "INSERT INTO product (nameP, typeP, price, stockProduct, codBrand) VALUES (?,?,?,?,?)";
	final String SQLDELETEPROD = "DELETE FROM product WHERE nameP = ?";
	final String SQLPRODUCTSTOCK = "SELECT stockProduct FROM product WHERE nameP = ?";	
	final String SQLSELECTPRODUCTSTOCK = "SELECT * FROM product WHERE stockProduct <= 50 ORDER BY stockProduct";	
	final String SQLPROD = "SELECT PROD FROM PRODUCT WHERE NAMEP = ?";

	// COMPONENTS
	final String SQLSELECTCOMPONENT = "SELECT * FROM component";
	final String SQLSELECTCOMPONENTNAMEPRICE = "SELECT * FROM component WHERE nameComp = ?";
	final String SQLINSERTCOMP = "INSERT INTO component (nameComp, typeC, stockComponent, priceComp, codBrand) VALUES (?,?,?,?,?)";
	final String SQLDELETECOMP = "DELETE FROM component WHERE nameComp = ?";
	final String SQLCOMPSTOCK = "SELECT stockComponent FROM component WHERE nameComp = ?";
	final String SQLSELECTCOMPSTOCK = "SELECT * FROM component WHERE stockcomponent <= 50 ORDER BY stockComponent";	

	// PRODUCTS & COMPONENTS
	final String SQLSELL = "CALL sellAndSubstract(?,?,?,?,?)";
	final String SQLRESTOCKPRODUCT = "UPDATE product SET stockProduct = ? WHERE codProduct = ?";
	final String SQLRESTOCKCOMPONENT = "UPDATE component SET stockComponent = ? WHERE codComponent = ?";

	// BRANDS
	final String SQLSELECTBRAND = "SELECT * FROM brand";
	final String SQLSELECTBRANDCODE = "SELECT codBrand FROM brand WHERE nameBrand = ?";
	final String SQLSELECTPRODUCTBRAND = "SELECT * FROM product WHERE codBrand =(SELECT codBrand FROM brand WHERE nameBrand = ?)";
	final String SQLSELECTCOMPONENTBRAND = "SELECT * FROM component WHERE codBrand=(SELECT codBrand FROM brand WHERE nameBrand = ?)";

	// PURCHASES AND BUYS
	final String SQLSELECTPURCHASE = "SELECT * FROM purchase WHERE codUser = ?";
	final String SQLSELECTBUY = "SELECT * FROM buy WHERE codUser = ?";

	/**[DATABASE]**/

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

	/**[USERS]**/

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

	// Verify that the user and the password matches
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

	// Gets users's information
	public User getUser(User user) {
		ResultSet rs = null;

		// Opens the connection
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLUSER);
			stmt.setString(1, user.getCodU());
			rs = stmt.executeQuery();
			while (rs.next()) {				
				user.setCodU(rs.getString("codUser"));
				user.setPassword(rs.getString("psw"));
				user.setUsername(rs.getString("username"));

				if (verifyUserType(user)) {
					user.setTypeU(TypeU.ADMIN);
				} else { 
					user.setTypeU(TypeU.CLIENT);
				}
			}
			rs.close();			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return user;
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

	/**[PRODUCTS]**/

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
				product.setNameP(rs.getString("nameP"));
				product.setPrice(rs.getDouble("price"));
				switch(rs.getString("typeP")) {
				case "Mobile":
					product.setTypeP(TypeP.MOBILE);
					break;
				case "Computer":
					product.setTypeP(TypeP.COMPUTER);
					break;
				}			
				product.setCodP(rs.getInt("codProduct"));
				product.setCodBrand(rs.getInt("codBrand"));
				product.setStock(rs.getInt("stockProduct"));	
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
	public Product obtainProduct(String name) {
		ResultSet rs = null;
		Product product = new Product();

		// Opens the connection
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTPRODUCTNAMEPRICE);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				product.setCodP(rs.getInt("codProduct"));
				product.setNameP(rs.getString("nameP"));
				product.setPrice(rs.getDouble("price"));
				//product.setTypeP(rs.getDouble("priceComp"));
				product.setCodBrand(rs.getInt("codBrand"));
				product.setStock(rs.getInt("stockProduct"));
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

			switch (prod.getTypeP()) {
			case MOBILE:
				stmt.setString(2, "Mobile");
				break;
			case COMPUTER:
				stmt.setString(2, "Computer");
				break;
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

	/**[COMPONENTS]**/

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
				component.setNameC(rs.getString("nameComp"));
				component.setPrice(rs.getDouble("priceComp"));
				switch(rs.getString("typeC")) {
				case "Graphics":
					component.setTypeC(TypeC.GRAPHICS);
					break;
				case "RAM":
					component.setTypeC(TypeC.RAM);
					break;
				case "Processor":
					component.setTypeC(TypeC.PROCESSOR);
					break;
				}
				component.setCodC(rs.getInt("codComponent"));
				component.setCodBrand(rs.getInt("codBrand"));
				component.setStock(rs.getInt("stockComponent"));
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
	public Comp obtainComponent(String name) {
		ResultSet rs = null;
		Comp component = new Comp();

		// Opens the connection
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTCOMPONENTNAMEPRICE);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				component.setCodC(rs.getInt("codComponent"));
				component.setNameC(rs.getString("nameComp"));
				component.setPrice(rs.getDouble("priceComp"));
				//component.setTypeC(rs.getDouble("priceComp"));
				component.setCodBrand(rs.getInt("codBrand"));
				component.setStock(rs.getInt("stockComponent"));
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

			switch (comp.getTypeC()) {
			case GRAPHICS:
				stmt.setString(2, "Graphics");
				break;

			case RAM:
				stmt.setString(2, "RAM");
				break;

			case PROCESSOR:
				stmt.setString(2, "Processor");
				break;
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

	// Delete a component
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

	/**[PRODUCTS & COMPONENTS]**/

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

	// Restocks products and components
	@Override
	public boolean restock(int code, int quantity, boolean type) {
		// Open connection and declare a boolean to check if the update is properly executed
		boolean check = false;

		this.openConnection();
		try {
			if (type) {
				stmt = con.prepareStatement(SQLRESTOCKPRODUCT);
				stmt.setInt(1, quantity);
				stmt.setInt(2, code);
				stmt.executeUpdate();

			} else {
				stmt = con.prepareStatement(SQLRESTOCKCOMPONENT);
				stmt.setInt(1, quantity);
				stmt.setInt(2, code);
				stmt.executeUpdate();
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return check;
	}

	/**[BRANDS]**/

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
				product.setNameP(rs.getString("nameP"));
				product.setPrice(rs.getDouble("price"));
				product.setStock(rs.getInt("stockProduct"));
				product.setCodP(rs.getInt("codProduct"));
				product.setCodBrand(rs.getInt("codBrand"));
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
				component.setNameC(rs.getString("nameComp"));
				component.setPrice(rs.getDouble("priceComp"));
				component.setStock(rs.getInt("stockComponent"));
				component.setCodC(rs.getInt("codComponent"));
				component.setCodBrand(rs.getInt("codBrand"));
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

	/**[PURCHASES & BUYS]**/

	// Get the Purchase list
	public Map<Integer, Purchase> getPurchases(String codU) {
		ResultSet rs = null;
		Purchase purchase;
		Map<Integer, Purchase> purchases = new TreeMap<>();

		// Opens the connection
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTPURCHASE);
			stmt.setString(1, codU);
			rs = stmt.executeQuery();
			while (rs.next()) {
				purchase = new Purchase();
				purchase.setCodPurchase(rs.getInt("codPurchase"));
				purchase.setCodProduct(rs.getInt("codProduct"));			
				purchase.setCodUser(rs.getString("codUser"));
				purchase.setQuantity(rs.getInt("quantity"));
				purchase.setPrice(rs.getDouble("totalPrice"));	
				purchase.setDate(rs.getDate("dateP"));	
				purchases.put(purchase.getCodPurchase(), purchase);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return purchases;
	}

	// Get the buy list
	public Map<Integer, Buy> getBuys(String codU) {
		ResultSet rs = null;
		Buy buy;
		Map<Integer, Buy> buys = new TreeMap<>();
		// Opens the connection
		this.openConnection();
		try {
			stmt = con.prepareStatement(SQLSELECTBUY);
			stmt.setString(1, codU);
			rs = stmt.executeQuery();
			while (rs.next()) {
				buy = new Buy();
				buy.setCodBuy(rs.getInt("codBuy"));
				buy.setCodComponent(rs.getInt("codComponent"));			
				buy.setCodUser(rs.getString("codUser"));
				buy.setQuantity(rs.getInt("quantity"));
				buy.setPrice(rs.getDouble("totalPrice"));	
				buy.setDate(rs.getDate("dateB"));	
				buys.put(buy.getCodBuy(), buy);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		return buys;
	}
}
