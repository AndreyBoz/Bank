package org.example.BankAccount;
import jdk.jfr.TransitionTo;

import java.sql.Date;
public class BankTransition {
    private int id;
    private int idAccount;
    private Date date;
    private double amount;
    private TransitionType type;
    BankTransition(TransitionType type, double amount){
        this.type = type;
        this.amount = amount;
        date = new Date(System.currentTimeMillis());
    }
    public Date getDate(){
        return date;
    }
    public double getAmount() {
        return amount;
    }
    public String getType(){
        return type.toString();
    }
}
