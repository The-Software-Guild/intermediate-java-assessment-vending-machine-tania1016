
package com.tania.vendingmachine.service;

import com.tania.vendingmachine.dto.Change;
import com.tania.vendingmachine.dto.Product;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Tania
 */
public interface VendingMachineServiceLayer {
    public Map<String, Product> loadProductsInStock() throws VendingMachinePersistenceException, VendingMachineNoItemInventoryException;

    public void saveProductList() throws VendingMachinePersistenceException;

    public Product getChosenProduct(String productId) throws VendingMachineNoItemInventoryException;

    public void checkSufficientMoneyToBuyProduct(BigDecimal inputAmount, Product product) throws VendingMachineInsufficientFundsException;

    public Change calculateChange(BigDecimal amount, Product product);

    public void updateProductSale(Product product) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException;

}
