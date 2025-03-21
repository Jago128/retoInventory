package model;

import java.util.Map;

public interface MediaMartaDAO {

	//Prepare methods to be inherited by DBImplementation

	// User related methods
	public boolean registerUser(User user);
	public boolean verifyUser(User user);
	public boolean verifyUserPassword(User user);
	public boolean verifyUserType(User user);

	// Product, Comp and Brand methods
	public boolean insertProd(Product prod);
	public Map<String, Product> verifyProduct();
	public boolean deleteProd(String nom);
	public boolean sellAndSubstract(String codUser, String nomProd, int amount);
	public Map<Integer,Product> showProdsOrderedByStock();
	
	public boolean insertComp(Comp comp);
	public Map<String, Comp> verifyComponent();
	public Map<Integer,Comp> showCompsOrderedByStock();
	
	public Map<String, Brand> verifyBrands();
	public Map<String, Product> showProductsBrand(String brand);
	public Map<String, Comp> showComponentsBrand(String brand);
}
