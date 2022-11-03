
package com.tania.vendingmachine.service;

import com.tania.vendingmachine.dao.VendingMachineAuditDao;
import com.tania.vendingmachine.dao.VendingMachineDao;
import com.tania.vendingmachine.dto.Change;
import com.tania.vendingmachine.dto.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tania
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        //implement
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public Map<String, Product> loadProductsInStock() throws VendingMachinePersistenceException {
        Map<String, Product> productsInStock = new HashMap<>();
        for (Product p : dao.loadProductsFromFile().values()) {
            if (p.getItemsInStock() < 1) {
                auditDao.writeAuditEntry("Product Id:" + p.getProductId() + "quantity in stock is zero. ");
            } else {
                productsInStock.put(p.getProductId(), p);
            }
        }
        return productsInStock;
    }

    @Override
    public void saveProductList() throws VendingMachinePersistenceException {
        dao.writeProductsToFile();
        auditDao.writeAuditEntry("Product list saved to file");
    }


    @Override
    public Product getChosenProduct(String productId) throws VendingMachineNoItemInventoryException {
        validateProductInStock(productId);
        return dao.getProduct(productId);
    }

    @Override
    public void checkSufficientMoneyToBuyProduct(BigDecimal inputAmount, Product product) throws
            VendingMachineInsufficientFundsException {
        if (inputAmount.compareTo(product.getPrice()) < 0) {
            throw new VendingMachineInsufficientFundsException("Insufficient Funds to buy" + product.getProductName());
        }
    }

    @Override
    public Change calculateChange(BigDecimal amount, Product product) {
        BigDecimal change = amount.subtract(product.getPrice()).multiply(new BigDecimal("100"));
        return new Change(change);
    }

    @Override
    public void updateProductSale(Product product) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException {
        if (product.getItemsInStock() > 0) {
            product.setItemsInStock(product.getItemsInStock() - 1);
        } else {
            throw new VendingMachineNoItemInventoryException("Selected product not in stock");
        }
        dao.updateProduct(product.getProductId(), product);
        auditDao.writeAuditEntry("Product Id: " + product.getProductId() + "is updated");
    }

    public void validateProductInStock(String productId) throws VendingMachineNoItemInventoryException {

        List<String> ids = dao.getAllProductIds();
        Product product = dao.getProduct(productId);
        if (!ids.contains(productId) || (product.getItemsInStock() < 1)) {
            throw new VendingMachineNoItemInventoryException("Selected Product is not in stock");
        }
    }
}


