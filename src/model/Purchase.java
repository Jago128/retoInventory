package model;

import java.time.LocalDate;

public class Purchase {
	//Declare variables
	private int codProduct;
	private int codUser;
	private int quantity;
	private LocalDate date;
	
	//Declare empty constructor
	public Purchase() {
		this.codProduct = 0;
		this.codUser = 0;
		this.quantity = 0;
		this.date = LocalDate.now();
	}
	
	//Declare parametrized constructor
	public Purchase(int codProduct, int codUser, int quantity, LocalDate date) {
		this.codProduct = codProduct;
		this.codUser = codUser;
		this.quantity = quantity;
		this.date = date;
	}

	//Declare getters and setters
	public int getCodProduct() {
		return codProduct;
	}

	public void setCodProduct(int codProduct) {
		this.codProduct = codProduct;
	}

	public int getCodUser() {
		return codUser;
	}

	public void setCodUser(int codUser) {
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

	//Declare toString
	@Override
	public String toString() {
		return "Purchase [codProduct=" + codProduct + ", codUser=" + codUser + ", quantity=" + quantity + ", date=" + date + "]";
	}
}
