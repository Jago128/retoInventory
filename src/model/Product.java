package model;

public class Product {
	private int codP;
	private String nameP;
	private TypeP typeP;
	private double price;
	private int stock;
	private int codBrand;
	
	public Product(int codP, String nameP, TypeP typeP, double price, int stock, int codBrand) {
		this.codP = codP;
		this.nameP = nameP;
		this.typeP = typeP;
		this.price = price;
		this.stock = stock;
		this.codBrand = codBrand;
	}

	public int getCodP() {
		return codP;
	}

	public void setCodP(int codP) {
		this.codP = codP;
	}

	public String getNameP() {
		return nameP;
	}

	public void setNameP(String nameP) {
		this.nameP = nameP;
	}

	public TypeP getTypeP() {
		return typeP;
	}

	public void setTypeP(TypeP typeP) {
		this.typeP = typeP;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getCodBrand() {
		return codBrand;
	}

	public void setCodBrand(int codBrand) {
		this.codBrand = codBrand;
	}

	@Override
	public String toString() {
		return "Product [codP=" + codP + ", nameP=" + nameP + ", typeP=" + typeP + ", price=" + price + ", stock="
				+ stock + ", codBrand=" + codBrand + "]";
	}
}
