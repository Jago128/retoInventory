package model;

public class Comp {
	// Declare variables
	private int codC;
	private String nameC;
	private TypeC typeC;
	private int codBrand;
	private int stock;
	private double price;

	// Declare empty constructor
	public Comp() {
		this.codC = 0;
		this.nameC = "";
		this.typeC = TypeC.GRAPHICS;
		this.codBrand = 0;
		this.stock = 0;
		this.price = 0;
	}

	// Declare parametrized constructors
	public Comp(String nameC, TypeC typeC, int codBrand, int stock, double price) {
		this.codC = 0;
		this.nameC = nameC;
		this.typeC = typeC;
		this.codBrand = codBrand;
		this.stock = stock;
		this.price = price;
	}
	
	public Comp(int codC, String nameC, TypeC typeC, int codBrand, int stock, double price) {
		this.codC = codC;
		this.nameC = nameC;
		this.typeC = typeC;
		this.codBrand = codBrand;
		this.stock = stock;
		this.price = price;
	}

	// Declare getters and setters
	public int getCodC() {
		return codC;
	}

	public void setCodC(int codC) {
		this.codC = codC;
	}

	public String getNameC() {
		return nameC;
	}

	public void setNameC(String nameC) {
		this.nameC = nameC;
	}

	public TypeC getTypeC() {
		return typeC;
	}

	public void setTypeC(TypeC typeC) {
		this.typeC = typeC;
	}

	public int getCodBrand() {
		return codBrand;
	}

	public void setCodBrand(int codBrand) {
		this.codBrand = codBrand;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// Method to show name and price
		public String nameAndPrice() {
			return "[Product "+codC+"] "+nameC+" "+price+" €";
		}
	
	// Declare toString
	@Override
	public String toString() {
		return "Comp [Code: "+codC+", Name: "+nameC+", Type: "+typeC+", Brand Code: "+codBrand+", Stock: "+stock+", Price: "+price+"]";
	}
}
