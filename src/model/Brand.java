package model;

public class Brand {
	// Declare variables
	private int codB;
	private String nameB;

	// Declare empty constructor
	public Brand() {
		this.codB = 0;
		this.nameB = "";
	}

	// Declare parametrized constructor
	public Brand(int codB, String nameB) {
		this.codB = codB;
		this.nameB = nameB;
	}

	// Declare getters and setters
	public int getCodB() {
		return codB;
	}

	public void setCodB(int codB) {
		this.codB = codB;
	}

	public String getNameB() {
		return nameB;
	}

	public void setNameB(String nameB) {
		this.nameB = nameB;
	}

	// Declare toString
	@Override
	public String toString() {
		return "Brand [codB=" + codB + ", nameB=" + nameB + "]";
	}
}
