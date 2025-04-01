package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


import model.Product;
import model.TypeP;

class TestProduct {
	
	private Product product;

	@BeforeEach
    public void setUp() {
        // Create an instance of Employee before each test
        product = new Product(1, "Iphone X", TypeP.MOBILE, 1000, 100, 1);
    }

	@AfterEach
	void tearDown() throws Exception {
		product = null;
	}

	@Test
    public void testGetCodP() {
        assertEquals(1, product.getCodP());
    }
	
    @Test
    public void testSetCodP() {
        product.setCodP(2);
        assertEquals(2, product.getCodP());
    }

    @Test
    public void testGetNameP() {
        assertEquals("Iphone X", product.getNameP());
    }

    @Test
    public void testSetNameP() {
        product.setNameP("Samsung S24");
        assertEquals("Samsung S24", product.getNameP());
    }
    
    @Test
    public void testGetTypeP() {
        assertEquals(TypeP.MOBILE, product.getTypeP());
    }
	
    @Test
    public void testSetTypeP() {
        product.setTypeP(TypeP.COMPUTER);
        assertEquals(TypeP.COMPUTER, product.getTypeP());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1000, product.getPrice());
    }
	
    @Test
    public void testSetPrice() {
        product.setPrice(2);
        assertEquals(2, product.getPrice());
    }

    @Test
    public void testGetStock() {
        assertEquals(100, product.getStock());
    }
	
    @Test
    public void testSetStock() {
        product.setStock(2);
        assertEquals(2, product.getStock());
    }

    @Test
    public void testGetCodBrand() {
        assertEquals(1, product.getCodBrand());
    }
	
    @Test
    public void testSetCodBrand() {
        product.setCodBrand(2);
        assertEquals(2, product.getCodBrand());
    }

    
	@Test
	void testDefaultConstructor() {
		Product defaultProduct=new Product();
		assertEquals(0, defaultProduct.getCodP());
		assertEquals("", defaultProduct.getNameP());
		assertEquals(TypeP.COMPUTER, defaultProduct.getTypeP());
		assertEquals(0, defaultProduct.getPrice());
		assertEquals(0, defaultProduct.getStock());
		assertEquals(0, defaultProduct.getCodBrand());
	}
	
	@Test
    public void testParameterizedConstructor() {
        assertEquals(1, product.getCodP());
        assertEquals("Iphone X", product.getNameP());
    }

}
