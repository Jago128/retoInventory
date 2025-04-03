package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import model.Brand;

class TestBrand {

	private Brand brand;

	@BeforeEach
	public void setUp() {
		// Create an instance of Employee before each test
		brand = new Brand(1, "Samsung");
	}

	@AfterEach
	void tearDown() throws Exception {
		brand = null;
	}

	@Test
	void testDefaultConstructor() {
		Brand defaultBrand=new Brand();
		assertEquals(0, defaultBrand.getCodB());
		assertEquals("", defaultBrand.getNameB());
	}

	@Test
	public void testParameterizedConstructor() {
		assertEquals(1, brand.getCodB());
		assertEquals("Samsung", brand.getNameB());
	}

	@Test
	public void testGetId() {
		assertEquals(1, brand.getCodB());
	}

	@Test
	public void testSetId() {
		brand.setCodB(2);
		assertEquals(2, brand.getCodB());
	}

	@Test
	public void testGetName() {
		assertEquals("Samsung", brand.getNameB());
	}

	@Test
	public void testSetName() {
		brand.setNameB("Apple");
		assertEquals("Apple", brand.getNameB());
	}

	@Test
	public void testToString() {
		assertEquals("Brand [Code: 1, Name: Samsung]", brand.toString());
	}
}
