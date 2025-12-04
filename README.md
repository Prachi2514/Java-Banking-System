Java Banking System (Console-Based)
A console-based Banking Management System built using Core Java, JDBC, and MySQL.
The project includes secure account creation, login, deposit, withdrawal, fund transfer, and transaction history features.

🚀 Features
✅ 1. Create Account
Auto-generated account number
Stores name, PIN, opening balance
Data stored securely in MySQL database

✅ 2. Login System
Login using Account Number + PIN
Validates credentials using JDBC PreparedStatements

✅ 3. Deposit Money
Adds money to user account
Updates balance & transaction table

✅ 4. Withdraw Money
Withdraws only if balance is sufficient
Validates minimum balance
Updates MySQL records

✅ 5. Fund Transfer
Transfer money from one account to another
Validates both accounts
Uses commit/rollback to ensure safe transaction

✅ 6. Transaction History
Shows last transactions of the account
Stores all deposits, withdrawals, transfers

✅ 7. Error Handling
Invalid account number
Wrong PIN
Insufficient balance
Empty ResultSet checks

## 🚀 How to Run

1. **Add MySQL JDBC Driver**
   - Place `mysql-connector-j-9.5.0.jar` inside the `lib/` folder.
   - In VS Code → left panel → *JAVA PROJECTS* → **Referenced Libraries → Add JAR**.

2. **Setup MySQL Database**
   ```sql
   CREATE DATABASE bankdb;
   USE bankdb;

   CREATE TABLE accounts (
       acc_no INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100),
       pin VARCHAR(10),
       balance DOUBLE
   );

   CREATE TABLE transactions (
       id INT PRIMARY KEY AUTO_INCREMENT,
       acc_no INT,
       type VARCHAR(20),
       amount DOUBLE,
       target_acc INT,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   
3.**Update DB Password**
-In DBConnection.java, enter your MySQL password:
-DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb","root","YOUR_PASSWORD");

4.**Run the Project**
-Open Main.java → Click Run
OR
-javac Main.java
-java Main
