package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import model.*;

class TestUser {
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		user = new User("", "", "", TypeU.valueOf("Client"));
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
