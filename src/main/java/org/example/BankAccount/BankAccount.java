package org.example.BankAccount;

import java.util.LinkedList;
import java.util.Queue;

import java.security.SecureRandom;

public class BankAccount {
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    private double balance;
    private  String cardId;
    private int idBankTransition;
    private Queue<BankTransition> transition = new LinkedList<>();
    AccountType type;
    public BankAccount(String firstName, String lastName, int age, double balance, String cardId, AccountType type, int idBankTransions){
        firstName = "undefined";
        this.lastName = "undefined";
        this.age = 0;
        this.balance = 0;
    }
    public BankAccount(String firstName,String lastName, String password,int age,double balance,String cardId,AccountType type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.balance = balance;
        this.password = password;
        this.cardId = cardId;
        this.type = type;
    }
    public BankAccount(String firstName,String lastName, String password,int age,double balance,String cardId,AccountType type,int idBankTransition){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.balance = balance;
        this.password = password;
        this.cardId = cardId;
        this.type = type;
        this.idBankTransition = idBankTransition;
    }
    private static final int passwordLength = 10;
    public static String newPassword(){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passwordLength; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
    public static String newIdCard(){
        final String chars = "0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passwordLength; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
    public boolean deposit(double amount) {
        this.balance+=amount;
        transition.add(new BankTransition(TransitionType.Deposit,amount));
        return true;
    }
    public boolean withdraw(double amount) {
        if(amount>balance || amount<0){
            System.out.println("Sorry, your balance is not enough.");
            return false;
        }else {
            balance-=amount;
            transition.add(new BankTransition(TransitionType.Withdraw,amount));
            return true;
        }
    }
    public boolean transfer(BankAccount from, double amount){
        if(from.withdraw(amount)){
            this.deposit(amount);
            transition.add(new BankTransition(TransitionType.Transfer,amount));
            return true;
        }
        return false;
    }
    public Queue<BankTransition> getTransitions() {
        return transition;
    }
    public String getTrasitions() {
        StringBuilder message = new StringBuilder();
        for(var el : transition){
            message.append("Type operation: ").append(el.getType());
            message.append("Date: ").append(el.getDate());
            message.append("Amount: ").append(el.getAmount());
        }
        return message.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getCardId() {
        return cardId;
    }
    public String getType() {
        return type.toString();
    }
    public int getIdBankTransition() {
        return idBankTransition;
    }

    @Override
    public String toString(){
        return String.format("First name: %s\nLast name: %s\nAge: %d\nBalance:%.2f", this.firstName, this.lastName, this.age, this.balance);
    }
}
