package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import model.*;

class TestUser {
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		user = new User("Joao10","Felix","1234", TypeU.CLIENT);
	}

	@AfterEach
	void tearDown() throws Exception {
		user = null;
	}

	@Test
	public void testDefaultConstructor() {
		User test = new User();
		assertEquals("", test.getCodU());
		assertEquals("", test.getPassword());
		assertEquals(TypeU.UNSET, test.getTypeU());
		assertEquals("", test.getUsername());
	}
	
	@Test
	public void testParametrizedConstrctor() {
		assertEquals("Joao10", user.getCodU());
		assertEquals("1234", user.getPassword());
		assertEquals(TypeU.CLIENT, user.getTypeU());
		assertEquals("Felix", user.getUsername());
	}
	
	@Test
	public void getCodUTest() {
		assertEquals("Joao10", user.getCodU());
	}
	
	@Test
	public void getPasswordTest() {
		assertEquals("1234", user.getPassword());
	}
	
	@Test
	public void getTypeUTest() {
		assertEquals(TypeU.CLIENT, user.getTypeU());
	}
	
	@Test
	public void getUsernameTest() {
		assertEquals("Felix", user.getUsername());
	}
	
	@Test
	public void setCodUTest() {
		user.setCodU("Jago128");
		assertEquals("Jago128", user.getCodU());
	}
	
	@Test
	public void setPasswordTest() {
		user.setPassword("1288");
		assertEquals("1288", user.getPassword());
	}
	
	@Test
	public void setTypeUTest() {
		user.setTypeU(TypeU.ADMIN);
		assertEquals(TypeU.ADMIN, user.getTypeU());
	}
	
	@Test
	public void setUsernameTest() {
		user.setUsername("Jagoba");
		assertEquals("Jagoba", user.getUsername());
	}
}
