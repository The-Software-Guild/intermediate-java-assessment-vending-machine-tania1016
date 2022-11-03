
package com.mattu.vendingmachine.service;

import com.tania.vendingmachine.dao.VendingMachineAuditDao;
import com.tania.vendingmachine.dao.VendingMachineDao;
import com.tania.vendingmachine.dto.Change;
import com.tania.vendingmachine.dto.Product;
import com.tania.vendingmachine.service.*;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Tania
 */
public class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer testSevice;


    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        testSevice = new VendingMachineServiceLayerImpl(dao, auditDao);

    }

    @Test
    public void testLoadProducts(){
        try{
            System.out.println("Load products in stock ");
            BigDecimal bd = new BigDecimal("0.99");
            Product p1 = new Product("1", "Snickers Bar", bd, 12);
            bd = new BigDecimal("1.99");
            Product p2 = new Product("2", "CocaCola", bd, 14);
            Map<String, Product> expResult = new TreeMap<>();
            expResult.put("1", p1);
            expResult.put("2", p2);

            Map<String, Product > result = testSevice.loadProductsInStock();

            assertEquals(expResult, result, "TestProducts should load from file");
        } catch (VendingMachineNoItemInventoryException | VendingMachinePersistenceException e){
            fail("Product was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testGetChosenProduct() throws VendingMachineNoItemInventoryException{
        System.out.println("getChosenProduct");
        BigDecimal bd = new BigDecimal("0.99");

        Product expResult = new Product("1", "Snickers Bar", bd, 12);

        Product result = testSevice.getChosenProduct("1");

        assertEquals(expResult, result, "Check both products are equal");
        assertEquals(expResult.getProductName(), result.getProductName(), "Check if both products have same name.");
    }

    @Test
    public void testCheckSufficientMoneyToBuyProduct_Exception() throws
            VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException, VendingMachinePersistenceException{
        System.out.println("checkSufficientFundstoBuyProductException");
        BigDecimal inputAmount = new BigDecimal(".50");
        Product product = testSevice.getChosenProduct("1");

        Exception exception = assertThrows(VendingMachineInsufficientFundsException.class, ()->{
            testSevice.checkSufficientMoneyToBuyProduct(inputAmount,product);
    });
    }

    @Test
    public void testCheckSufficientMoneytoBuyProduct() throws  VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException {
        try{
            System.out.println("CheckSufficientMoneytoBuyProduct");
            BigDecimal inputAmount = new BigDecimal("3.50");
            Product product = testSevice.getChosenProduct("1");
            testSevice.checkSufficientMoneyToBuyProduct(inputAmount, product);

        } catch (VendingMachineInsufficientFundsException e){
            fail("Sufficient Funds to buy product. No exception should be thrown ");
        }
    }
    
@Test
public void testCalculateChange() throws VendingMachineNoItemInventoryException{
        System.out.println("CalculateChange");
        BigDecimal amount = new BigDecimal("5");
        Product product = testSevice.getChosenProduct("1");
        Change result = testSevice.calculateChange(amount, product);

        assertEquals(16, result.getQuarters(), "Check number of quarters.");
        assertEquals(0, result.getDimes(), " Check number of dimes.");
    assertEquals(0, result.getNickels(), "Check number of nickels.");
    assertEquals(1, result.getPennies(), " Check number of pennies.");
}


    @Test
    public void testUpdateProductSale() throws Exception{
        System.out.println("UpdateProductSale");
        Product product = testSevice.getChosenProduct("1");
        assertEquals(12, product.getItemsInStock(), "Check items in stock");

        testSevice.updateProductSale(product);

        Product updatedProduct = testSevice.getChosenProduct("1");
        assertEquals(11, updatedProduct.getItemsInStock(), "Check items in stock");

    }
}
