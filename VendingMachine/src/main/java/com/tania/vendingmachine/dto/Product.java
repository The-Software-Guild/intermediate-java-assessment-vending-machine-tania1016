
package com.tania.vendingmachine.dto;

import com.tania.vendingmachine.service.VendingMachinePersistenceException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

/**
 * @author Tania
 */
public class Product {


    private String productId;
    private String productName;
    private BigDecimal price;
    private int itemsInStock;

    private final String DELIMITER = ",";

    public Product(String productId, String productName, BigDecimal price, int itemsInStock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.itemsInStock = itemsInStock;
    }

    public Product(String productAsText) throws VendingMachinePersistenceException {
        try {
            String[] fields = productAsText.split(DELIMITER); // Pattern Syntax Exception
            this.productId = fields[0];
            this.productName = fields[1];
            this.price = new BigDecimal(fields[2]);
            this.itemsInStock = Integer.parseInt(fields[3]);// Null Point Exception
        } catch (PatternSyntaxException e) {
            throw new VendingMachinePersistenceException(e.getMessage());
        } catch (NullPointerException | NumberFormatException e) {
            throw new VendingMachinePersistenceException(e.getMessage());
        }

    }

    public int getItemsInStock() {

        return itemsInStock;
    }

    public void setItemsInStock(int itemsInStock) {

        this.itemsInStock = itemsInStock;
    }

    public String getProductId() {

        return productId;
    }

    public void setProductId(String productId) {

        this.productId = productId;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(String productName) {

        this.productName = productName;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }


    @Override
    public String toString() {
        return "Item{" + "name=" + productName + ", cost=" + price + ", numInventoryItems=" + itemsInStock + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.productId);
        hash = 79 * hash + Objects.hashCode(this.productName);
        hash = 79 * hash + Objects.hashCode(this.price);
        hash = 79 * hash + this.itemsInStock;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.itemsInStock != other.itemsInStock) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }


    public String marshalProductAsText() {

       String productAsText = this.productId+DELIMITER+
               this.productName + DELIMITER
                + this.price + DELIMITER +
                this.itemsInStock;
        return productAsText;
    }
}


