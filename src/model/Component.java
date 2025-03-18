package model;

public class Component {
	private int codC;
	private String nameC;
	private TypeC typeC;
	private int codBrand;
	public Component(int codC, String nameC, TypeC typeC, int codBrand) {
		this.codC = codC;
		this.nameC = nameC;
		this.typeC = typeC;
		this.codBrand = codBrand;
	}
	
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

	@Override
	public String toString() {
		return "Component [codC=" + codC + ", nameC=" + nameC + ", typeC=" + typeC + ", codBrand=" + codBrand + "]";
	}
}
