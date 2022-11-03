
package com.tania.vendingmachine.dao;

import com.tania.vendingmachine.service.VendingMachinePersistenceException;

/**
 * @author Tania
 */
public interface VendingMachineAuditDao {
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
