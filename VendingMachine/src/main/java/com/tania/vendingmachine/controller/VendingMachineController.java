
package com.tania.vendingmachine.controller;

import com.tania.vendingmachine.dto.Change;
import com.tania.vendingmachine.dto.Product;
import com.tania.vendingmachine.service.*;
import com.tania.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author Tania
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) throws VendingMachinePersistenceException {
        this.view = view;
        this.service = service;
    }


    public void run() {
        BigDecimal moneyDeposited = new BigDecimal("0");
        Product chosenProduct = null;
        String keepGoing = "yes";
        String input;
        Scanner scan = new Scanner(System.in);
        while (keepGoing.equals("yes")) {
            boolean isEnoughMoney = false;
            try {
                displayHeader();
                do {
                    productMenu();
                    moneyDeposited = userMoneyInput(moneyDeposited);
                    chosenProduct = getChosenProduct();
                    isEnoughMoney = disUserPutSufficientAmountOfMoney(moneyDeposited, chosenProduct);
                    if (toExitVendingMachine(isEnoughMoney)) {
                        return;
                    }

                } while (!isEnoughMoney);
                displayUserMoneyInput(moneyDeposited);
                displayChangeReturnedToUser(moneyDeposited, chosenProduct);
                updateSoldProduct(chosenProduct);
                saveProductList();
            } catch (VendingMachinePersistenceException e) {
                displayErrorMessage(e.getMessage());
            } finally {
                displayFinalMessage();
            }
            view.displayUserResponse();
            keepGoing = scan.nextLine();
        }
    }

    void displayHeader() {

        view.displayVendingMachineWelcome();
    }

    void productMenu() throws VendingMachinePersistenceException {

        try {
            view.displayProductHeader();
            for (Product p : service.loadProductsInStock().values()) {
                view.displayProduct(p);
            }
        } catch (VendingMachineNoItemInventoryException | VendingMachinePersistenceException e) {
            throw new VendingMachinePersistenceException(e.getMessage());
        }
    }

    BigDecimal userMoneyInput(BigDecimal amount) {

        return amount.add(view.promptUserMoneyInput());
    }

    Product getChosenProduct() {
        while (true) {
            String productId = view.promptUserProductChoice();
            try {
                Product product = service.getChosenProduct(productId);
                view.displayUserChoiceOfProduct(product);
                return product;
            } catch (VendingMachineNoItemInventoryException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    boolean disUserPutSufficientAmountOfMoney(BigDecimal userAmount, Product product) {
        try {
            service.checkSufficientMoneyToBuyProduct(userAmount, product);
            return true;
        } catch (VendingMachineInsufficientFundsException e) {
            displayErrorMessage(e.getMessage());
            displayUserMoneyInput(userAmount);
            return false;
        }
    }

    void displayUserMoneyInput(BigDecimal amount) {

        view.displayUserMoneyInput(amount);
    }

    void displayChangeReturnedToUser(BigDecimal amount, Product product) {
        Change change = service.calculateChange(amount, product);
        view.displayChangeDue(change);

    }

    boolean toExitVendingMachine(boolean isEnoughMoney) {
        if (isEnoughMoney) {
            return false;
        } else {
            return view.toExit();
        }
    }

    void displayErrorMessage(String message) {

        view.displayErrorMessage(message);
    }

    void updateSoldProduct(Product product) throws VendingMachinePersistenceException {
        try {
            service.updateProductSale(product);
        } catch (VendingMachineNoItemInventoryException e) {
            throw new VendingMachinePersistenceException(e.getMessage());
        }
    }

    void saveProductList() throws VendingMachinePersistenceException {
        service.saveProductList();

    }

    void displayFinalMessage() {

        view.displayFinalMessage();
    }
}
