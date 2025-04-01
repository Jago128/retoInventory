package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;

import model.Comp;
import model.Purchase;
import model.TypeC;

class TestPurchase {
	private Purchase purch;
	@BeforeEach
	void setUp() throws Exception {
		purch = new Purchase (1,1,"Xabitxu",150,0, LocalDate.now());
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDefaultConstructor() {
		Comp defaultEmployee = new Comp();
		assertEquals(0,defaultEmployee.getCodC());
		assertEquals("", defaultEmployee.getNameC());
		assertEquals(TypeC.GRAPHICS,defaultEmployee.getTypeC());
		assertEquals(0,defaultEmployee.getCodBrand());
		assertEquals(0,defaultEmployee.getStock());
		assertEquals(0,defaultEmployee.getPrice());
	}

	@Test
	public void testParameterizedConstructor() {
		assertEquals(1, purch.getCodC());
		assertEquals("Asus GT710", purch.getNameC());
		assertEquals(TypeC.GRAPHICS, purch.getTypeC());
		assertEquals(5, purch.getCodBrand());
		assertEquals(5, purch.getStock());
		assertEquals(81.99, purch.getPrice());       
	}
}
