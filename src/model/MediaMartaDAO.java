package model;

import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;

public interface MediaMartaDAO {

	/**[USERS]**/
	
	public boolean verifyUser(User user);

	public boolean verifyUserPassword(User user);

	public boolean verifyUserType(User user);
	
	public User getUser(User user);

	public boolean registerUser(User user);

	/**[PRODUCTS]**/
	
	public Map<String, Product> verifyProduct();

	public Product obtainProduct(String name);
	
	public boolean insertProd(Product prod);

	public boolean deleteProd(String nom);
	
	public int checkStock(String nomItem, boolean type);
	
	public Map<String, Product> showProdsOrderedByStock();
	
	/**[COMPONENTS]**/
	
	public Map<String, Comp> verifyComponent();

	public Comp obtainComponent(String name);
	
	public boolean insertComp(Comp comp);
	
	public boolean deleteComp(String nom);
	
	public Map<String, Comp> showCompsOrderedByStock();
	
	/**[PRODUCTS & COMPONENTS]**/

	public void sellAndSubstract(String codUser, String nomItem, int amount, double price, boolean type);
	
	public boolean restock(int code, int quantity, boolean type);
	
	/**[BRANDS]**/

	public Map<String, Brand> verifyBrands();
	
	public int getBrandCode(String brandName);
	
	public Map<String, Product> showProductsBrand(String brand);

	public Map<String, Comp> showComponentsBrand(String brand);

	/**[PURCHASES & BUYS]**/

	// Get the Purchase list
	public Map<String, Purchase> getPurchases(String codU);
	
	// Get the buy list
	public Map<String, Buy> getBuys(String codU);
}
