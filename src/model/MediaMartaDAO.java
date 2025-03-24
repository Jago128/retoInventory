package model;

import java.util.Map;

public interface MediaMartaDAO {

	// Prepare methods to be inherited by DBImplementation

	// User related methods
	public boolean registerUser(User user);

	public boolean verifyUser(User user);

	public boolean verifyUserPassword(User user);

	public boolean verifyUserType(User user);

	// Product, Comp and Brand methods
	public String sellAndSubstractProduct(String codUser, String nomProd, int amount, double price);
	
	public String sellAndSubstractComponent(String codUser, String nomProd, int amount, double price);
	
	// PRODUCTS
	public boolean insertProd(Product prod);

	public Map<String, Product> verifyProduct();
	
	public Product obtainProductNamePrice(String name);

	public boolean deleteProd(String nom);
	
	public Map<Integer, Product> showProdsOrderedByStock();
	
	// COMPONENTS
	public boolean insertComp(Comp comp);

	public Map<String, Comp> verifyComponent();
	
	public Comp obtainComponentNamePrice(String name);
	
	public boolean deleteComp(String nom);

	public Map<Integer, Comp> showCompsOrderedByStock();
		
	// BRANDS
	public Map<String, Brand> verifyBrands();

	public Map<String, Product> showProductsBrand(String brand);

	public Map<String, Comp> showComponentsBrand(String brand);

}
