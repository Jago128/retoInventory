package model;

import java.time.LocalDate;

public class Purchase {
	// Declare variables
	private int codPurchase;
	private int codProduct;
	private String codUser;
	private int quantity;
	private LocalDate date;

	// Declare empty constructor
	public Purchase() {
		this.codPurchase = 0;
		this.codProduct = 0;
		this.codUser = "";
		this.quantity = 0;
		this.date = LocalDate.now();
	}

	// Declare parametrized constructor
	public Purchase(int codPurchase, int codProduct, String codUser, int quantity, LocalDate date) {
		this.codPurchase = codPurchase;
		this.codProduct = codProduct;
		this.codUser = codUser;
		this.quantity = quantity;
		this.date = date;
	}

	// Declare getters and setters
	public int getCodPurchase() {
		return codPurchase;
	}

	public void setCodPurchase(int codPurchase) {
		this.codPurchase = codPurchase;
	}

	public int getCodProduct() {
		return codProduct;
	}

	public void setCodProduct(int codProduct) {
		this.codProduct = codProduct;
	}

	public String getCodUser() {
		return codUser;
	}

	public void setCodUser(String codUser) {
		this.codUser = codUser;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	// Declare toString
	@Override
	public String toString() {
		return "Purchase [Code: "+codPurchase+", Product Code: "+codProduct+", User Code: "+codUser+", Quantity: "+quantity+", Date: "+date+"]";
	}
}
