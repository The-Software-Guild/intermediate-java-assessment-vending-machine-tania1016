
package com.tania.vendingmachine;

import com.tania.vendingmachine.controller.VendingMachineController;
import com.tania.vendingmachine.dao.VendingMachineAuditDao;
import com.tania.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.tania.vendingmachine.dao.VendingMachineDao;
import com.tania.vendingmachine.dao.VendingMachineDaoImpl;
import com.tania.vendingmachine.service.VendingMachinePersistenceException;
import com.tania.vendingmachine.service.VendingMachineServiceLayer;
import com.tania.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.tania.vendingmachine.ui.UserIO;
import com.tania.vendingmachine.ui.UserIOConsoleImpl;
import com.tania.vendingmachine.ui.VendingMachineView;

/**
 * @author Tania
 */
public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException {
        UserIO myIO = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIO);
        VendingMachineDao myDao = new VendingMachineDaoImpl();
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoImpl();
        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
        VendingMachineController myController = new VendingMachineController(myView, myService);
        myController.run();

    }
}
