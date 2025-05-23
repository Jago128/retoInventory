package model;

import java.sql.Date;
import java.time.LocalDate;

public class Purchase {
	// Declare variables
	private int codPurchase;
	private int codProduct;
	private String codUser;
	private int quantity;
	private double price;
	private Date date;

	// Declare empty constructor
	public Purchase() {
		this.codPurchase = 0;
		this.codProduct = 0;
		this.codUser = "";
		this.quantity = 0;
		this.price = 0;
		this.date = Date.valueOf(LocalDate.now());
	}

	// Declare parametrized constructor
	public Purchase(int codPurchase, int codProduct, String codUser, int quantity, double price, Date date) {
		this.codPurchase = codPurchase;
		this.codProduct = codProduct;
		this.codUser = codUser;
		this.quantity = quantity;
		this.price = price;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Method to show name and price
	public String allData() {
		return "[Code: "+codPurchase+"] You bought "+quantity+" unit(s) of product "+codProduct+" at a total price of "+price+"€ on "+date;
	}

	// Declare toString
	@Override
	public String toString() {
		return "Purchase [Code: "+codPurchase+", Product Code: "+codProduct+", User Code: "+codUser+", Quantity: "+quantity+", Date: "+date+"]";
	}
}
