package model;

import java.util.Map;

public interface MediaMartaDAO {

	// Prepare methods to be inherited by DBImplementation

	// User related methods
	public boolean registerUser(User user);

	public boolean verifyUser(User user);

	public boolean verifyUserPassword(User user);

	public boolean verifyUserType(User user);

	// PRODUCTS
	public boolean insertProd(Product prod);

	public Map<String, Product> verifyProduct();
	
	public Product obtainProductNamePrice(String name);

	public boolean deleteProd(String nom);
	
	public Map<String, Product> showProdsOrderedByStock();
	
	// COMPONENTS
	public boolean insertComp(Comp comp);

	public Map<String, Comp> verifyComponent();
	
	public Comp obtainComponentNamePrice(String name);
	
	public boolean deleteComp(String nom);

	public Map<String, Comp> showCompsOrderedByStock();
	
	// Product, Comp and Brand methods
	public boolean sellAndSubstract(String codUser, String nomProd, int amount, double price, boolean comp);
	
	// BRANDS
	public Map<String, Brand> verifyBrands();
	
	public int getBrandCode(String brandName);
	
	public Map<String, Product> showProductsBrand(String brand);

	public Map<String, Comp> showComponentsBrand(String brand);
}
