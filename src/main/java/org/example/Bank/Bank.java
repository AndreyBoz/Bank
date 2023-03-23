package org.example.Bank;

import org.example.BankAccount.AccountType;
import org.example.BankAccount.BankAccount;
import org.example.BankAccount.TransitionType;
import org.example.DBPackage.DBWorker;
import java.sql.SQLException;
import java.util.Scanner;

public class Bank {
    DBWorker  dbWorker;
    public Bank() throws SQLException {
        dbWorker = new DBWorker();
    }
    public int mainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Menu:\n1. Create new account.\n2. Log in\n3. Exit\nChoose:");
        try {
            return scanner.nextInt();
        }catch (TypeNotPresentException exception){
            System.out.println("Your choose is not correct.");
            return 0;
        }catch (Exception exception) {
            System.out.println("Error: "+exception);
            return 0;
        }
    }
    private void menuAccount(BankAccount account) throws SQLException {
        int choose = 0;
        Scanner scanner = new Scanner(System.in);
        while(choose == 0) {
            System.out.print("Menu account:\n1. Deposit\n2. With draw\n3. Transfer\nChoose:");
            choose = scanner.nextInt();
            switch(choose){
                case 1 -> dbWorker.updateBalance(deposit(account), TransitionType.Deposit);
                case 2 -> dbWorker.updateBalance(withdraw(account),TransitionType.Withdraw);
                case 3 -> dbWorker.updateBalance(transfer(account), TransitionType.Transfer);
            }
        }

    }
    public void createAccount() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter your old: ");
            int age = scanner.nextInt();
            String password = BankAccount.newPassword();
            System.out.println("Your new password: " + password);
            String newCardId = BankAccount.newIdCard();
            System.out.println("Your id card: "+newCardId);
            dbWorker.insertNew(new BankAccount(firstName, lastName, password, age, 0, newCardId, AccountType.USER));
        }catch (Exception exception){
            System.out.println("Error input data.");
        }
    }
    public void logIn() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your card id: ");
        String carId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        BankAccount bankAccount = dbWorker.searchAccount(carId, password);
        if(bankAccount==null) {
            System.out.println("Your account is not found.");
            return;
        }
        System.out.println("Your account:");
        System.out.println(bankAccount);
        menuAccount(bankAccount);
    }
    private BankAccount deposit(BankAccount account){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        return account;
    }
    private BankAccount withdraw(BankAccount account){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
        return account;
    }
    private BankAccount transfer(BankAccount account) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id card of account to transfer: ");
        String cardId = scanner.nextLine();
        BankAccount trAccount = dbWorker.searchAccount(cardId);
        if(trAccount == null){
            System.out.println("Account is not found");
            return account;
        }
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();
        if(account.withdraw(amount)) {
            trAccount.deposit(amount);
            dbWorker.updateBalance(trAccount, TransitionType.Transfer);
        }
        return account;
    }
}
