package model;

public class Product {
	// Declare variables
	private int codP;
	private String nameP;
	private TypeP typeP;
	private double price;
	private int stock;
	private int codBrand;

	// Declare empty constructor
	public Product() {
		this.codP = 0;
		this.nameP = "";
		this.typeP = TypeP.COMPUTER;
		this.price = 0;
		this.stock = 0;
		this.codBrand = 0;
	}

	// Declare parametrized constructors
	public Product(String nameP, TypeP typeP, double price, int stock, int codBrand) {
		this.codP = 0;
		this.nameP = nameP;
		this.typeP = typeP;
		this.price = price;
		this.stock = stock;
		this.codBrand = codBrand;
	}
	
	public Product(int codP, String nameP, TypeP typeP, double price, int stock, int codBrand) {
		this.codP = codP;
		this.nameP = nameP;
		this.typeP = typeP;
		this.price = price;
		this.stock = stock;
		this.codBrand = codBrand;
	}

	// Declare getters and setters
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

	// Method to show name and price
	public String nameAndPrice() {
		return "[Product Code "+codP+"] "+nameP+" "+price+" €";
	}
	
	// Declare toString
	@Override
	public String toString() {
		return "Product [Code: "+codP+", Name: "+nameP+", Type: "+typeP+", Price: "+price+", Stock: "+stock+", Brand Code: "+codBrand+"]";
	}
}
