
package com.tania.vendingmachine.ui;

import com.tania.vendingmachine.dto.Change;
import com.tania.vendingmachine.dto.CoinValue;
import com.tania.vendingmachine.dto.Product;

import java.math.BigDecimal;

/**
 *
 * @author Tania
 */
public class VendingMachineView {
    private UserIO io;


    public VendingMachineView(UserIO io) {

        this.io = io;
    }
    public void displayVendingMachineWelcome(){
        io.print("Welcome to Tania's Vending Machine");

    }

    public void displayProductHeader(){
        io.print("No\t\tProduct\t\t\t\tPrice");
        io.print("----------------------------------------------");
    }

    public void displayProduct(Product product ){
        io.print(product.getProductId()+ "\t\t"+ product.getProductName()+ "\t\t\t"+product.getPrice());
    }

    public BigDecimal promptUserMoneyInput(){
        return io.readBigDecimal("\nPlease insert money: ");

    }

    public String promptUserProductChoice(){
        return io.readString("Please enter the product you want to buy(Enter a number):");
    }
    public void displayUserChoiceOfProduct(Product product){
        io.print("You have chosen " + product.getProductName()+".");

    }

    public void displayUserMoneyInput(BigDecimal amount){
        io.print("You have deposited $"+ amount + ".");

    }
    public void displayChangeDue(Change change){
        io.print("The change due: ");
        io.print(CoinValue.QUARTERS + ":"+ change.getQuarters());
        io.print(CoinValue.DIMES + ":"+ change.getDimes());
        io.print(CoinValue.NICKELS + ":"+ change.getNickels());
        io.print(CoinValue.PENNIES + ":"+ change.getPennies());

    }
    public void displayErrorMessage(String message)
    {
        io.print("======Error======");
        io.readString(message);
    }

    public boolean toExit(){
        String answer = io.readString("Do you want to exit the Vending Machine? (y/n) ").toLowerCase();
    if ( answer.startsWith("y")){
        return true;
    } else {
        return false;
    }
    }

    public void displayFinalMessage() {
        io.print("Thank you for using the vending machine!");
    }

    public void displayUserResponse(){
        io.print("Do you want to make another selection ? ");
    }


}
