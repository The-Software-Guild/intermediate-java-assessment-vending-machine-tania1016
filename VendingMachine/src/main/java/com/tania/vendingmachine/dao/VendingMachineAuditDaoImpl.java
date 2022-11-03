
package com.tania.vendingmachine.dao;

import com.tania.vendingmachine.service.VendingMachinePersistenceException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * @author Tania
 */
public class VendingMachineAuditDaoImpl implements VendingMachineAuditDao {

    public static final String AUDIT_FILE = "audit.txt";

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));

        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist audit information ");
        }

        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }

}
