package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;

import model.Purchase;

class TestPurchase {
	private Purchase purch;
	private DateTimeFormatter format;
	
	@BeforeEach
	void setUp() throws Exception {
		format=DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String dateStr="2025/03/02";
		LocalDate dateL = null;
		dateL=LocalDate.parse(dateStr, format);
		Date date=Date.valueOf(dateL);
		purch = new Purchase (1,1,"Xabitxu",150,0, date);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		purch=null;
	}
	
	@Test
	public void testDefaultConstructor() {
		Purchase test = new Purchase();
		assertEquals(0,test.getCodPurchase());
		assertEquals(0, test.getCodProduct());
		assertEquals("",test.getCodUser());
		assertEquals(0,test.getQuantity());
		assertEquals(0,test.getPrice());
		assertEquals(Date.valueOf(purch.getLocalDate()),test.getDate());
	}

	@Test
	public void testParameterizedConstructor() {
		String dateStr="2025/03/02";
		LocalDate date=null;
		date=LocalDate.parse(dateStr, format);
		assertEquals(1, purch.getCodPurchase());
		assertEquals(1, purch.getCodProduct());
		assertEquals("Xabitxu", purch.getCodUser());
		assertEquals(150, purch.getQuantity());
		assertEquals(0, purch.getPrice());
		assertEquals(date, purch.getDate());       
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
		String dateStr="2025/02/20";
		LocalDate dateL = null;
		dateL=LocalDate.parse(dateStr, format);
		Date date = Date.valueOf(dateL);
		purch.setDate(date);
		assertEquals(date, purch.getDate());  
	}
}
