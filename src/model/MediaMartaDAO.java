package model;

import java.util.Map;

public interface MediaMartaDAO {
	
	//Prepare methods to be inherited by DBImplementation
	
	// User related methods
	public boolean verifyUser(User user);
	
	public boolean verifyUserPassword(User user);

	public boolean verifyUserType(User user);
	
	public boolean registerUser(User user);
	
	// Product, Comp and Brand methods
	public Map<String, Product> verifyProduct();
	
	public Map<String, Comp> verifyComponent();
	
	public Map<String, Brand> verifyBrands();
	
	public Map<String, Product> showProductsBrand();
	
	public Map<String, Comp> showComponentsBrand();
		
	public boolean insertProd(Product prod);
	
	public boolean insertComp(Comp comp);
	
	public boolean sellAndSubstract(String codUser, String nomProd, int amount);
	
	public void showProdsOrderedByStock();
	
	public boolean deleteProd(String nom);
}
