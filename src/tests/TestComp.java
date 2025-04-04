package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import model.Comp;
import model.TypeC;

class TestComp {
	private Comp comp;

	@BeforeEach
	void setUp() throws Exception {
		comp = new Comp(1, "Asus GT710", TypeC.GRAPHICS, 5, 5, 81.99);
	}

	@AfterEach
	void tearDown() throws Exception {
		comp = null;
	}

	@Test
	public void testDefaultConstructor() {
		Comp defaultEmployee = new Comp();
		assertEquals(0, defaultEmployee.getCodC());
		assertEquals("", defaultEmployee.getNameC());
		assertEquals(TypeC.GRAPHICS, defaultEmployee.getTypeC());
		assertEquals(0, defaultEmployee.getCodBrand());
		assertEquals(0, defaultEmployee.getStock());
		assertEquals(0, defaultEmployee.getPrice());
	}

	@Test
	public void testParameterizedConstructor() {
		assertEquals(1, comp.getCodC());
		assertEquals("Asus GT710", comp.getNameC());
		assertEquals(TypeC.GRAPHICS, comp.getTypeC());
		assertEquals(5, comp.getCodBrand());
		assertEquals(5, comp.getStock());
		assertEquals(81.99, comp.getPrice());
	}

	@Test
	public void testGetCodC() {
		assertEquals(1, comp.getCodC());
	}

	@Test
	public void testSetCodC() {
		comp.setCodC(1);
		assertEquals(1, comp.getCodC());
	}

	@Test
	public void testGetNameC() {
		assertEquals("Asus GT710", comp.getNameC());
	}

	@Test
	public void testSetNameC() {
		comp.setNameC("Asus GT710");
		assertEquals("Asus GT710", comp.getNameC());
	}

	@Test
	public void testGetTypeC() {
		assertEquals(TypeC.GRAPHICS, comp.getTypeC());
	}

	@Test
	public void testSetTypeC() {
		comp.setTypeC(TypeC.GRAPHICS);
		assertEquals(TypeC.GRAPHICS, comp.getTypeC());
	}

	@Test
	public void testGetCodBrand() {
		assertEquals(5, comp.getCodBrand());
	}

	@Test
	public void testSetCodBrand() {
		comp.setCodBrand(5);
		assertEquals(5, comp.getCodBrand());
	}

	@Test
	public void testGetStock() {
		assertEquals(5, comp.getStock());
	}

	@Test
	public void testSetStock() {
		comp.setStock(5);
		assertEquals(5, comp.getStock());
	}

	@Test
	public void testGetPrice() {
		assertEquals(81.99, comp.getPrice());
	}

	@Test
	public void testSetPrice() {
		comp.setPrice(81.99);
		assertEquals(81.99, comp.getPrice());
	}

	@Test
	public void testNameAndPrice() {
		assertEquals("[Component Code "+"1"+"] "+"Asus GT710"+" "+"81.99"+" €", comp.nameAndPrice());
	}

	@Test
	public void testToString() {
		assertEquals("Comp [Code: "+"1"+", Name: "+"Asus GT710"+", Type: "+"GRAPHICS"+", Brand Code: "+"5"+", Stock: "+"5"+", Price: "+"81.99]", comp.toString());
	}

}
