
package com.mattu.vendingmachine.dao;

import com.tania.vendingmachine.dao.VendingMachineDao;
import com.tania.vendingmachine.dao.VendingMachineDaoImpl;
import com.tania.vendingmachine.dto.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Tania
 */
public class VendingMachineDaoImplTest {

    String testFile = "testitems.txt";
    VendingMachineDao testDao = new VendingMachineDaoImpl(testFile);

    public VendingMachineDaoImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void testLoadProductsFromFile() throws Exception {
        System.out.println("loadProductFromFile");
        BigDecimal bd = new BigDecimal("0.99");
        Product p1 = new Product("1", "Snickers Bar", bd, 12);
        bd = new BigDecimal("1.99");
        Product p2 = new Product("2", "CocaCola", bd, 14);
        Map<String, Product> result = testDao.loadProductsFromFile();
        Map<String, Product> expResult = new TreeMap<>();
        expResult.put("1", p1);
        expResult.put("2", p2);

        assertEquals(expResult, result, "Test Products loaded from file");
    }

    @Test
    public void testAddGetProduct() {
        System.out.println("addProduct");
        BigDecimal bd = new BigDecimal("0.99");
        Product p1 = new Product("1", "Snickers Bar", bd, 12);
        testDao.addProduct(p1.getProductId(), p1);

        Product result = testDao.getProduct(p1.getProductId());
        Product expResult = new Product("1", "Snickers Bar", bd, 12);

        assertEquals(expResult.getProductId(), result.getProductId(), "Checking Product Id");
        assertEquals(expResult.getProductName(), result.getProductName(), " Checking Product Name");
        assertEquals(expResult.getPrice(), result.getPrice(), " Checking Product Price");
        assertEquals(expResult.getItemsInStock(), result.getItemsInStock(), "Checking Items in Stock");

    }

    @Test
    public void testGetAllProducts() {
        System.out.println("getAllProducts");
        BigDecimal bd = new BigDecimal("0.99");
        Product p1 = new Product("1", "Snickers Bar", bd, 12);
        bd = new BigDecimal("1.99");
        Product p2 = new Product("2", "CocaCola", bd, 14);
        testDao.addProduct(p1.getProductId(), p1);
        testDao.addProduct(p2.getProductId(), p2);

        List<Product> result = testDao.getAllProducts();

        List<Product> expResult = new ArrayList<>();
        expResult.add(p1);
        expResult.add(p2);

        assertNotNull(result, "The list of products must not be null");
        assertEquals(2, result.size(), "List of products must have 2");
        assertTrue(result.contains(p1), "The List should contain ,Snickers Bar ");
        assertTrue(result.contains(p2), " The List should contain CocaCola");
        assertEquals(expResult, result, "List of products should be the same");

    }

    @Test
    public void testRemoveProduct() {
        System.out.println("removeProduct");
        BigDecimal bd = new BigDecimal("0.99");
        Product p1 = new Product("1", "Snickers Bar", bd, 12);
        bd = new BigDecimal("1.99");
        Product p2 = new Product("2", "CocaCola", bd, 14);
        testDao.addProduct(p1.getProductId(), p1);
        testDao.addProduct(p2.getProductId(), p2);
        Product removedProduct = testDao.removeProduct(p1.getProductId());
        assertEquals(removedProduct, p1, "The removed product should be Snickers Bar");

        List<Product> result = testDao.getAllProducts();
        assertNotNull(result, "The list of products should not be null");
        assertEquals(1, result.size(), "List of products should have 1 product.");
        removedProduct = testDao.removeProduct(p2.getProductId());
        assertEquals(removedProduct, p2, " The removed product should be CocaCola");

        result = testDao.getAllProducts();
        assertEquals(0, result.size(), "List of products should be empty.");

        Product retrievedProduct = testDao.getProduct(p1.getProductId());
        assertNull(retrievedProduct, "Snickers Bar was removed, should be null");
        retrievedProduct = testDao.getProduct(p2.getProductId());
        assertNull(retrievedProduct, "CocaCola was removed, should be null");
    }

    @Test
    public void testGetAllProductIds() {
        System.out.println("testGetALlProductIds");
        BigDecimal bd = new BigDecimal("0.99");
        Product p1 = new Product("1", "Snickers Bar", bd, 12);
        bd = new BigDecimal("1.99");
        Product p2 = new Product("2", "CocaCola", bd, 14);
        testDao.addProduct(p1.getProductId(), p1);
        testDao.addProduct(p2.getProductId(), p2);
        List<String> result = testDao.getAllProductIds();

        List<String> expResult = new ArrayList<>();
        expResult.add("1");
        expResult.add("2");

        assertNotNull(result, "The list of products should not be null ");
        assertEquals(2, result.size(), " The List of products should have 2 entries.");
        assertTrue(result.contains(p1.getProductId()), "The list should contain Snickers Bar Id");
        assertTrue(result.contains(p2.getProductId()), "The List should contain CocaCola Id");
        assertEquals(expResult, result, " The  2 lists of product IDs should be equal.");

    }


    @Test
    public void testUpdateProduct() {
        System.out.println("testUpdateProduct");
        BigDecimal bd = new BigDecimal("0.99");
        Product p1 = new Product("1", "Snickers Bar", bd, 12);
        bd = new BigDecimal("2.99");
        testDao.addProduct(p1.getProductId(), p1);
        p1.setProductName("Pepsi");
        p1.setPrice(bd);
        p1.setItemsInStock(20);
        testDao.updateProduct(p1.getProductId(), p1);
        Product result = testDao.updateProduct(p1.getProductId(), p1);
        Product expResult = new Product("1", "Pepsi", bd, 20);

        assertEquals(expResult, result, "Updated product is Pepsi");
        assertEquals("1", result.getProductId(), "Updated Id should be 1.");
        assertEquals("Pepsi", result.getProductName(), " Updated Product Name should be Pepsi");
        assertEquals(bd, result.getPrice(), "Updated price is 2.99");
        assertEquals(20, result.getItemsInStock(), "Updated product items is 20. ");

    }
}








