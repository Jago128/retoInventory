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
	private LocalDate dateL;
	private Date date;
	private String dateStr;
	
	@BeforeEach
	void setUp() throws Exception {
		format=DateTimeFormatter.ofPattern("yyyy/MM/dd");
		dateStr="2025/04/02";
		dateL=LocalDate.parse(dateStr, format);
		date=Date.valueOf(dateL);
		purch = new Purchase (1,1,"Xabitxu",150,100, date);
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
		assertEquals(Date.valueOf(LocalDate.now()),test.getDate());
	}

	@Test
	public void testParameterizedConstructor() {
		assertEquals(1, purch.getCodPurchase());
		assertEquals(1, purch.getCodProduct());
		assertEquals("Xabitxu", purch.getCodUser());
		assertEquals(150, purch.getQuantity());
		assertEquals(100, purch.getPrice());
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
		assertEquals(100, purch.getPrice());
	}

	@Test
	public void testSetPrice() {
		purch.setPrice(10);
		assertEquals(10, purch.getPrice());
	}
	
	@Test
	public void testGetDate() {
		assertEquals(date, purch.getDate());     
	}

	@Test
	public void testSetDate() {
		dateStr="2025/02/20";
		dateL=LocalDate.parse(dateStr, format);
		date = Date.valueOf(dateL);
		purch.setDate(date);
		assertEquals(date, purch.getDate());  
	}
	
	@Test
	public void testAllData() {
		assertEquals("[COD1] 150 units of component 1 at  total price 100.0€ on 2025-04-02",purch.allData());
	}
	
	@Test
	public void testToString() {
		assertEquals("Purchase [Code: 1, Product Code: 1, User Code: Xabitxu, Quantity: 150, Date: 2025-04-02]", purch.toString());
	}
}
