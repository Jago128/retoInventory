package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import model.Comp;
import model.TypeC;

class TestComp {
	private Comp comp;
	@BeforeEach
	void setUp() throws Exception {
		comp = new Comp ("Asus GT710",TypeC.GRAPHICS,5,5,81.99);
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
	void test() {
		fail("Not yet implemented");
	}

}
