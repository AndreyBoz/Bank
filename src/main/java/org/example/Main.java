package org.example;

import org.example.Bank.Bank;
import org.example.BankAccount.BankAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Bank bank = new Bank();
        while(true) {
            int choose = bank.mainMenu();
            while (choose == 0) {
                choose = bank.mainMenu();
            }
            switch (choose) {
                case 1 -> bank.createAccount();
                case 2 -> bank.logIn();
                case 3 -> {
                    return;
                }
            }
        }
    }
}