package model;

public interface MediaMartaDAO {
	
	//Prepare methods to be inherited by DBImplementation
	public boolean verifyUser(User user);
	
	public boolean verifyUserPassword(User user);

	public boolean verifyUserType(User user);
	
	public boolean verifyProduct(Product product);
	
	public boolean verifyComponent(Component component);
	
	public boolean verifyBrand(Brand brand);
	
	public boolean insertProd(Product prod);
	
	public boolean insertComp(Component comp);
	
	public boolean sellAndSubstract(double amount, String nom);
	
	public void showProdsOrderedByStock();
	
	public boolean deleteProd(String nom);
	
	public void showProd();
	
	public void showBrandsAndItsItems();
	
	public void showComponents();
}
