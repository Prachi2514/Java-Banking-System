package BankingSystem;

import java.sql.*;
//import java.util.*;

public class BankingOperations {

    // Create account
    public void createAccount(String name, String pin, double amount) throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO accounts(name, pin, balance) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, pin);
        ps.setDouble(3, amount);
        ps.executeUpdate();

        System.out.println("✔ Account created successfully!");
    }

    // Login
    public boolean login(int acc, String pin) throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM accounts WHERE acc_no=? AND pin=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, acc);
        ps.setString(2, pin);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    // Check balance
    public double getBalance(int acc) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT balance FROM accounts WHERE acc_no=?");
        ps.setInt(1, acc);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getDouble(1);
    }

    // Deposit
    public void deposit(int acc, double amt) throws Exception {
        double bal = getBalance(acc);
        double newBal = bal + amt;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance=? WHERE acc_no=?");
        ps.setDouble(1, newBal);
        ps.setInt(2, acc);
        ps.executeUpdate();

        addTransaction(acc, "DEPOSIT", amt, 0);

        System.out.println("✔ Amount Deposited!");
    }

    // Withdraw
    public void withdraw(int acc, double amt) throws Exception {
        double bal = getBalance(acc);

        if (bal < amt) {
            System.out.println("✖ Insufficient Balance!");
            return;
        }

        double newBal = bal - amt;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance=? WHERE acc_no=?");
        ps.setDouble(1, newBal);
        ps.setInt(2, acc);
        ps.executeUpdate();

        addTransaction(acc, "WITHDRAW", amt, 0);

        System.out.println("✔ Withdrawal Successful!");
    }

    // Transfer
    public void transfer(int from, int to, double amt) throws Exception {
        double bal = getBalance(from);

        if (bal < amt) {
            System.out.println("✖ Not enough balance to transfer!");
            return;
        }

        // Deduct
        withdraw(from, amt);

        // Add
        deposit(to, amt);

        addTransaction(from, "TRANSFER_OUT", amt, to);
        addTransaction(to, "TRANSFER_IN", amt, from);

        System.out.println("✔ Transfer Successful!");
    }

    // Mini statement (last 5)
    public void miniStatement(int acc) throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM transactions WHERE acc_no=? ORDER BY id DESC LIMIT 5";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, acc);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n----- MINI STATEMENT -----");
        while (rs.next()) {
            System.out.println(rs.getString("type") + " | " +
                    rs.getDouble("amount") + " | TO: " +
                    rs.getInt("target_acc") + " | " +
                    rs.getTimestamp("created_at"));
        }
        System.out.println("---------------------------\n");
    }

    // Add transaction
    private void addTransaction(int acc, String type, double amt, int target) throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO transactions(acc_no, type, amount, target_acc) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, acc);
        ps.setString(2, type);
        ps.setDouble(3, amt);
        ps.setInt(4, target);
        ps.executeUpdate();
    }
}

