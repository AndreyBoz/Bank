package org.example.DBPackage;

import org.example.Bank.Bank;
import org.example.BankAccount.AccountType;
import org.example.BankAccount.BankAccount;
import org.example.BankAccount.TransitionType;

import java.sql.*;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "voip321";
    static private final String queryAll = "SELECT * FROM accounts;";
    static private final String SEARCH_ACCOUNT = "SELECT * FROM accounts WHERE cardId = ? and password = ?;";
    static private final String SEARCH_ACCOUNT_BY_CARDID = "SELECT * FROM accounts WHERE cardId = ?;";
    static private final String INSERT_NEW = "INSERT INTO accounts (firstName,lastName,password,age,balance,cardId,type) VALUES(?,?,?,?,?,?,?);";
    static private final String UPDATE_BALANCE = "UPDATE accounts SET balance = ? WHERE cardId = ?;";
    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection;
    private Driver driver;
    public DBWorker() {
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);

        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void insertNew(BankAccount account) throws SQLException {
        preparedStatement = connection.prepareStatement(INSERT_NEW);
        preparedStatement.setString(1,account.getFirstName());
        preparedStatement.setString(2,account.getLastName());
        preparedStatement.setString(3,account.getPassword());
        preparedStatement.setInt(4,account.getAge());
        preparedStatement.setDouble(5,account.getBalance());
        preparedStatement.setString(6,account.getCardId());
        preparedStatement.setString(7,account.getType());
        preparedStatement.execute();
    }
    public BankAccount searchAccount(String carId,String passwordAccount) throws SQLException {
        BankAccount bankAccount;
        preparedStatement = connection.prepareStatement(SEARCH_ACCOUNT);
        preparedStatement.setString(1,carId);
        preparedStatement.setString(2,passwordAccount);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            int age = resultSet.getInt("age");
            double balance = resultSet.getDouble("balance");
            String cardId = resultSet.getString("cardId");
            int idBankTransition = resultSet.getInt("idBankTransitions");
            AccountType type = AccountType.valueOf(resultSet.getString("type"));
            bankAccount = new BankAccount(firstName,lastName, password,age,balance,cardId,type,idBankTransition);
            return bankAccount;
        }
        return null;
    }
    public BankAccount searchAccount(String carId) throws SQLException {
        BankAccount bankAccount;
        preparedStatement = connection.prepareStatement(SEARCH_ACCOUNT_BY_CARDID);
        preparedStatement.setString(1,carId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            int age = resultSet.getInt("age");
            double balance = resultSet.getDouble("balance");
            String cardId = resultSet.getString("cardId");
            int idBankTransition = resultSet.getInt("idBankTransitions");
            AccountType type = AccountType.valueOf(resultSet.getString("type"));
            bankAccount = new BankAccount(firstName,lastName, password,age,balance,cardId,type,idBankTransition);
            return bankAccount;
        }
        return null;
    }
    public void updateBalance(BankAccount account, TransitionType type) throws SQLException {
        preparedStatement = connection.prepareStatement(UPDATE_BALANCE);
        preparedStatement.setDouble(1,account.getBalance());
        preparedStatement.setString(2,account.getCardId());
        preparedStatement.execute();
    }
    private void addTransitions(BankAccount account) {

    }
}
