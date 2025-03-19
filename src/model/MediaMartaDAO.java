package model;

public interface MediaMartaDAO {
	//Prepare methods to be inherited by DBImplementation
	public boolean insertProd(Product prod);
	public boolean insertComp(Component comp);
	public boolean sellAndSubstract();
	public void showProdsOrderedByStock();
	public boolean deleteProd(String nom);
	public void showProd();
	public void showBrandsAndItsItems();
	public void showComponents();
}
