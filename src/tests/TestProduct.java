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
        assertEquals(TypeP.MOBILE, product.getTypeP());
        assertEquals(1000, product.getPrice());
        assertEquals(100, product.getStock());
        assertEquals(1, product.getCodBrand());
    }

	@Test
    public void testGetCodP() {
        assertEquals(1, product.getCodP());
    }
	
    @Test
    public void testSetCodP() {
        product.setCodP(1);
        assertEquals(1, product.getCodP());
    }

    @Test
    public void testGetNameP() {
        assertEquals("Iphone X", product.getNameP());
    }

    @Test
    public void testSetNameP() {
        product.setNameP("Iphone X");
        assertEquals("Iphone X", product.getNameP());
    }
    
    @Test
    public void testGetTypeP() {
        assertEquals(TypeP.MOBILE, product.getTypeP());
    }
	
    @Test
    public void testSetTypeP() {
        product.setTypeP(TypeP.MOBILE);
        assertEquals(TypeP.MOBILE, product.getTypeP());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1000, product.getPrice());
    }
	
    @Test
    public void testSetPrice() {
        product.setPrice(1000);
        assertEquals(1000, product.getPrice());
    }

    @Test
    public void testGetStock() {
        assertEquals(100, product.getStock());
    }
	
    @Test
    public void testSetStock() {
        product.setStock(100);
        assertEquals(100, product.getStock());
    }

    @Test
    public void testGetCodBrand() {
        assertEquals(1, product.getCodBrand());
    }
	
    @Test
    public void testSetCodBrand() {
        product.setCodBrand(1);
        assertEquals(1, product.getCodBrand());
    }
    
    @Test
	public void testNameAndPrice() {
		assertEquals("[Product Code "+"1"+"] "+"Iphone X"+" "+"1000.0"+" €",product.nameAndPrice());
	}
	
	@Test
	public void testToString() {
		assertEquals("Product [Code: "+"1"+", Name: "+"Iphone X"+", Type: "+"MOBILE"+", Price: "+"1000.0"+ ", Stock: "+"100"+", Brand Code: "+"1]",product.toString()); 
	}
}
