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
	
	@AfterEach
	void tearDown() throws Exception {
		purch=null;
	}
	
	@Test
	public void testDefaultConstructor() {
		Purchase defaultEmployee = new Purchase();
		assertEquals(0,defaultEmployee.getCodPurchase());
		assertEquals(0, defaultEmployee.getCodProduct());
		assertEquals("",defaultEmployee.getCodUser());
		assertEquals(0,defaultEmployee.getQuantity());
		assertEquals(0,defaultEmployee.getPrice());
		assertEquals(LocalDate.now(),defaultEmployee.getDate());
	}

	@Test
	public void testParameterizedConstructor() {
		assertEquals(1, purch.getCodPurchase());
		assertEquals(1, purch.getCodProduct());
		assertEquals("Xabitxu", purch.getCodUser());
		assertEquals(150, purch.getQuantity());
		assertEquals(0, purch.getPrice());
		assertEquals(LocalDate.now(), purch.getDate());       
	}
	
	@Test
	public void testGetCodPurchase() {
		assertEquals(1, purch.getCodPurchase());
	}


	@Test
	public void testSetCodPurchase() {
		purch.setCodPurchase(1);
		assertEquals(1, purch.getCodPurchase());
	}

	@Test 
	public void testGetCodProduct() {
		assertEquals(1, purch.getCodProduct());
	}


	@Test 
	public void testSetCodProduct() {
		purch.setCodProduct(1);
		assertEquals(1, purch.getCodProduct());
	}

	@Test
	public void testGetCodUser() {
		assertEquals("Xabitxu", purch.getCodUser());
	}

	@Test
	public void testSetCodUser() {
		purch.setCodUser("Xabitxu");
		assertEquals("Xabitxu", purch.getCodUser());
	}

	@Test
	public void testGetQuantity() {
		assertEquals(150, purch.getQuantity());
	}

	@Test
	public void testSetQuantity() {
		purch.setQuantity(150);
		assertEquals(150, purch.getQuantity());
	}

	@Test
	public void testGetPrice() {
		assertEquals(0, purch.getPrice());
	}

	@Test
	public void testSetPrice() {
		purch.setPrice(0);
		assertEquals(0, purch.getPrice());
	}
	
	@Test
	public void testGetDate() {
		assertEquals(LocalDate.now(), purch.getDate());  
	}

	@Test
	public void testSetDate() {
		purch.setDate(LocalDate.now());
		assertEquals(LocalDate.now(), purch.getDate());  
	}
	
}
