package model;

public class Brand {
	private int codB;
	private String nameB;

	public Brand(int codB, String nameB) {
		this.codB = codB;
		this.nameB = nameB;
	}

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

	@Override
	public String toString() {
		return "Brand [codB=" + codB + ", nameB=" + nameB + "]";
	}
}
