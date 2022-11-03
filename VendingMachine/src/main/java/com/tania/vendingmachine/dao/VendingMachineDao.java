
package com.tania.vendingmachine.dao;

import com.tania.vendingmachine.dto.Product;
import com.tania.vendingmachine.service.VendingMachinePersistenceException;

import java.util.List;
import java.util.Map;

/**
 * @author Tania
 */
public interface VendingMachineDao {
    Product addProduct(String productId, Product product);

    List<Product> getAllProducts();

    List<String> getAllProductIds();

    Product getProduct(String productId);

    Product updateProduct(String productId, Product product);

    Product removeProduct(String productId);

    Map<String, Product> loadProductsFromFile() throws VendingMachinePersistenceException;

    void writeProductsToFile() throws VendingMachinePersistenceException;
}
