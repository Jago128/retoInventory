package model;

import java.sql.Date;
import java.time.LocalDate;

public class Buy {
	// Declare variables
	private int codBuy;
	private int codComponent;
	private String codUser;
	private int quantity;
	private double price;
	private Date date;

	// Declare empty constructor
	public Buy() {
		this.codBuy = 0;
		this.codComponent = 0;
		this.codUser = "";
		this.quantity = 0;
		this.price = 0;
		this.date = Date.valueOf(LocalDate.now());
	}

	// Declare parametrized constructor
	public Buy(int codBuy, int codComponent, String codUser, int quantity, double price, Date date) {
		this.codBuy = codBuy;
		this.codComponent = codComponent;
		this.codUser = codUser;
		this.quantity = quantity;
		this.price = price;
		this.date = date;
	}

	// Declare getters and setters
	public int getCodBuy() {
		return codBuy;
	}

	public void setCodBuy(int codBuy) {
		this.codBuy = codBuy;
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
		return "["+codBuy+"] "+codComponent+" "+price+" €"+" date";
	}

	// Declare toString
	@Override
	public String toString() {
		return "Purchase [Code: "+codBuy+", Component Code: "+codComponent+", User Code: "+codUser+", Quantity: "+quantity+", Date: "+date+"]";
	}
}
