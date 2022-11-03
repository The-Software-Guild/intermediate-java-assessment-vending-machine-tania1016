
package com.tania.vendingmachine.dto;

import com.tania.vendingmachine.service.VendingMachineInsufficientFundsException;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Tania
 */

public class Change {
    private int quarters;
    private int dimes;
    private int nickels;
    private int pennies;

    public Change(BigDecimal amount) {
        this.quarters = amount.divide(new BigDecimal("25")).intValue();
        amount = amount.remainder(new BigDecimal("25"));
        this.dimes = amount.divide(new BigDecimal("10")).intValue();
        amount = amount.remainder(new BigDecimal("10"));
        this.nickels = amount.divide(new BigDecimal("5")).intValue();
        this.pennies = amount.remainder(new BigDecimal("5")).intValue();
    }

    public int getQuarters() {
        return quarters;
    }

    public int getNickels() {

        return nickels;
    }

    public int getDimes() {
        return dimes;
    }

    public int getPennies() {

        return pennies;
    }

}

