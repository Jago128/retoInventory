package model;

public class User {
	// Declare variables
	private String codU;
	private String username;
	private String password;
	private TypeU typeU;

	// Declare empty constructor
	public User() {
		this.codU = "";
		this.username = "";
		this.password = "";
		this.typeU = TypeU.NULL;
	}

	// Declare parametrized constructors
	public User(String codU, String password) {
		this.codU = codU;
		this.username = "";
		this.password = password;
		this.typeU = TypeU.CLIENT;
	}

	public User(String codU, String username, String password) {
		this.codU = codU;
		this.username = "";
		this.password = password;
		this.typeU = TypeU.CLIENT;
	}

	public User(String codU, String username, String password, TypeU typeU) {
		this.codU = codU;
		this.username = username;
		this.password = password;
		this.typeU = typeU;
	}

	// Declare getters and setters
	public String getCodU() {
		return codU;
	}

	public void setCodU(String codU) {
		this.codU = codU;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TypeU getTypeU() {
		return typeU;
	}

	public void setTypeU(TypeU typeU) {
		this.typeU = typeU;
	}

	// Declare toString
	@Override
	public String toString() {
		return "User [codU=" + codU + ", username=" + username + ", password=" + password + ", typeU=" + typeU + "]";
	}
}
