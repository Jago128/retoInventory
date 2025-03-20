package model;

import java.util.Map;

public interface MediaMartaDAO {
	
	//Prepare methods to be inherited by DBImplementation
	public boolean verifyUser(User user);
	
	public boolean verifyUserPassword(User user);

	public boolean verifyUserType(User user);
	
	public Map<String, Product> verifyProduct();
	
	public Map<String, Brand> verifyBrandsAndItsItems();
	
	public Map<String, Comp> verifyComponent();
	
	public boolean insertProd(Product prod);
	
	public boolean insertComp(Comp comp);
	
	public boolean sellAndSubstract(double amount, String nom, String codUser);
	
	public void showProdsOrderedByStock();
	
	public boolean deleteProd(String nom);
}
