package model;

import java.util.Map;

public interface MediaMartaDAO {

	/**[USERS]**/
	
	public boolean verifyUser(User user);

	public boolean verifyUserPassword(User user);

	public boolean verifyUserType(User user);

	public boolean registerUser(User user);

	/**[PRODUCTS]**/
	
	public Map<String, Product> verifyProduct();

	public Product obtainProductNamePrice(String name);
	
	public boolean insertProd(Product prod);

	public boolean deleteProd(String nom);
	
	public int checkStock(String nomItem, boolean type);
	
	public Map<String, Product> showProdsOrderedByStock();
	
	/**[COMPONENTS]**/
	
	public Map<String, Comp> verifyComponent();

	public Comp obtainComponentNamePrice(String name);
	
	public boolean insertComp(Comp comp);
	
	public boolean deleteComp(String nom);
	
	public Map<String, Comp> showCompsOrderedByStock();
	
	/**[PRODUCTS & COMPONENTS]**/

	public void sellAndSubstract(String codUser, String nomProd, int amount, double price, boolean comp);
	
	public boolean restock(String name, int quantity, boolean type);
	
	/**[BRANDS]**/

	public Map<String, Brand> verifyBrands();
	
	public int getBrandCode(String brandName);
	
	public Map<String, Product> showProductsBrand(String brand);

	public Map<String, Comp> showComponentsBrand(String brand);
}
