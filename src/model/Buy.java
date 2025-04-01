package model;

import java.time.LocalDate;

public class Buy {
	// Declare variables
	private int codPurchase;
	private int codComponent;
	private String codUser;
	private int quantity;
	private double price;
	private LocalDate date;

	// Declare empty constructor
	public Buy() {
		this.codPurchase = 0;
		this.codComponent = 0;
		this.codUser = "";
		this.quantity = 0;
		this.date = LocalDate.now();
	}

	// Declare parametrized constructor
	public Buy(int codPurchase, int codComponent, String codUser, int quantity, LocalDate date) {
		this.codPurchase = codPurchase;
		this.codComponent = codComponent;
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

	public int getCodComponent() {
		return codComponent;
	}

	public void setCodComponent(int codComponent) {
		this.codComponent = codComponent;
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

	// Method to show name and price
	public String allData() {
		return "["+codPurchase+"] "+codComponent+" "+price+" €"+" date";
	}

	// Declare toString
	@Override
	public String toString() {
		return "Purchase [Code: "+codPurchase+", Component Code: "+codComponent+", User Code: "+codUser+", Quantity: "+quantity+", Date: "+date+"]";
	}
}
