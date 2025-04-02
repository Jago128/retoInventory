package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;

import model.Buy;

class TestBuy {
	private Buy buy;
	private DateTimeFormatter format;
	private LocalDate dateL;
	private Date date;
	private String dateStr;
	
	@BeforeEach
	void setUp() throws Exception {
		format=DateTimeFormatter.ofPattern("yyyy/MM/dd");
		dateStr="2025/01/04";
		dateL=LocalDate.parse(dateStr, format);
		date = Date.valueOf(dateL);
		buy = new Buy(1,4,"Jago128",2, 60, date);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		buy=null;
	}
	
	@Test
	public void testDefaultConstructor() {
		Buy test = new Buy();
		assertEquals(0,test.getCodBuy());
		assertEquals(0, test.getCodComponent());
		assertEquals("",test.getCodUser());
		assertEquals(0,test.getQuantity());
		assertEquals(0,test.getPrice());
		assertEquals(Date.valueOf(LocalDate.now()),test.getDate());
	}

	@Test
	public void testParameterizedConstructor() {
		assertEquals(1, buy.getCodBuy());
		assertEquals(4, buy.getCodComponent());
		assertEquals("Jago128", buy.getCodUser());
		assertEquals(2, buy.getQuantity());
		assertEquals(60, buy.getPrice());
		assertEquals(date, buy.getDate());       
	}
	
	@Test
	public void testGetCodPurchase() {
		assertEquals(1, buy.getCodBuy());
	}


	@Test
	public void testSetCodPurchase() {
		buy.setCodBuy(1);
		assertEquals(1, buy.getCodBuy());
	}

	@Test 
	public void testGetCodProduct() {
		assertEquals(4, buy.getCodComponent());
	}


	@Test 
	public void testSetCodProduct() {
		buy.setCodComponent(1);
		assertEquals(1, buy.getCodComponent());
	}

	@Test
	public void testGetCodUser() {
		assertEquals("Jago128", buy.getCodUser());
	}

	@Test
	public void testSetCodUser() {
		buy.setCodUser("Xabitxu");
		assertEquals("Xabitxu", buy.getCodUser());
	}

	@Test
	public void testGetQuantity() {
		assertEquals(2, buy.getQuantity());
	}

	@Test
	public void testSetQuantity() {
		buy.setQuantity(150);
		assertEquals(150, buy.getQuantity());
	}

	@Test
	public void testGetPrice() {
		assertEquals(60, buy.getPrice());
	}

	@Test
	public void testSetPrice() {
		buy.setPrice(2);
		assertEquals(2, buy.getPrice());
	}
	
	@Test
	public void testGetDate() {
		String dateStr="2025/01/04";
		LocalDate dateL=null;
		dateL=LocalDate.parse(dateStr, format);
		Date date = Date.valueOf(dateL);
		assertEquals(date, buy.getDate());  
	}

	@Test
	public void testSetDate() {
		dateStr="2025/02/20";
		dateL=LocalDate.parse(dateStr, format);
		date = Date.valueOf(dateL);
		buy.setDate(date);
		assertEquals(date, buy.getDate());  
	}
}
