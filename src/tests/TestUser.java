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
		assertNull(test.getCodU());
		assertNull(test.getPassword());
		assertNull(test.getTypeU());
		assertNull(test.getUsername());
	}
	
	@Test
	public void testParametrizedConstrctor() {
		assertEquals("Joao10", user.getCodU());
		assertEquals("1234", user.getPassword());
		assertEquals(TypeU.CLIENT, user.getTypeU());
		assertEquals("Felix", user.getUsername());
	}
}
