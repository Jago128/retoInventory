package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;

import model.Buy;

class TestBuy {
	private Buy buy;
	private DateTimeFormatter format;
	
	@BeforeEach
	void setUp() throws Exception {
		format=DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String dateStr="2025/01/04";
		LocalDate date=null;
		date=LocalDate.parse(dateStr, format);
		buy = new Buy(1,4,"Jago128",2, 60, date);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		buy=null;
	}
	
	@Test
	public void testDefaultConstructor() {
		Buy test = new Buy();
		assertEquals(0,test.getCodPurchase());
		assertEquals(0, test.getCodComponent());
		assertEquals("",test.getCodUser());
		assertEquals(0,test.getQuantity());
		assertEquals(0,test.getPrice());
		assertEquals(LocalDate.now(),test.getDate());
	}

	@Test
	public void testParameterizedConstructor() {
		String dateStr="2025/01/04";
		LocalDate date=null;
		date=LocalDate.parse(dateStr, format);
		assertEquals(1, buy.getCodPurchase());
		assertEquals(4, buy.getCodComponent());
		assertEquals("Jago128", buy.getCodUser());
		assertEquals(2, buy.getQuantity());
		assertEquals(60, buy.getPrice());
		assertEquals(date, buy.getDate());       
	}
	
	@Test
	public void testGetCodPurchase() {
		assertEquals(1, buy.getCodPurchase());
	}


	@Test
	public void testSetCodPurchase() {
		buy.setCodPurchase(1);
		assertEquals(1, buy.getCodPurchase());
	}

	@Test 
	public void testGetCodProduct() {
		assertEquals(1, buy.getCodComponent());
	}


	@Test 
	public void testSetCodProduct() {
		buy.setCodComponent(1);
		assertEquals(1, buy.getCodComponent());
	}

	@Test
	public void testGetCodUser() {
		assertEquals("Xabitxu", buy.getCodUser());
	}

	@Test
	public void testSetCodUser() {
		buy.setCodUser("Xabitxu");
		assertEquals("Xabitxu", buy.getCodUser());
	}

	@Test
	public void testGetQuantity() {
		assertEquals(150, buy.getQuantity());
	}

	@Test
	public void testSetQuantity() {
		buy.setQuantity(150);
		assertEquals(150, buy.getQuantity());
	}

	@Test
	public void testGetPrice() {
		assertEquals(0, buy.getPrice());
	}

	@Test
	public void testSetPrice() {
		buy.setPrice(0);
		assertEquals(0, buy.getPrice());
	}
	
	@Test
	public void testGetDate() {
		assertEquals(LocalDate.now(), buy.getDate());  
	}

	@Test
	public void testSetDate() {
		String dateStr="2025/02/20";
		LocalDate date=null;
		date=LocalDate.parse(dateStr, format);
		buy.setDate(date);
		assertEquals(date, buy.getDate());  
	}
}
