package model;

public interface MediaMartaDAO {
	//Prepare methods to be inherited by DBImplementation
	public boolean insertProd(Product p);
	public boolean insertComp(Component c);
	public boolean sellAndSubstract();
	public void showProdsOrderedByStockLowerThan5AndRestock();
	public boolean deleteProd(int cod);
	public void showProd();
	public void showBrandsAndItsItems();
	public void showComponents();
}
