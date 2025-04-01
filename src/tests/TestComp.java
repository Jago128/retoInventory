package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import model.*;

class TestComp {
	private Comp comp;
	@BeforeEach
	void setUp() throws Exception {
		comp = new Comp ("Asus GT710",TypeC.GRAPHICS,5,5,81.99);
	}

	@AfterEach
	void tearDown() throws Exception {
		comp=null;
	}
	
	@Test
	public void testDefaultConstructor() {
		Comp defaultEmployee = new Comp();
		assertNull(defaultEmployee.getCodC());
		assertNull(defaultEmployee.getNameC());
		assertNull(defaultEmployee.getTypeC());
		assertNull(defaultEmployee.getCodBrand());
		assertNull(defaultEmployee.getStock());
		assertNull(defaultEmployee.getPrice());
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
	void test() {
		fail("Not yet implemented");
	}

}
