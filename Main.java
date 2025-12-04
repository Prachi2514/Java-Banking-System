package BankingSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        BankingOperations bank = new BankingOperations();

        while (true) {
            System.out.println("===== BANK MENU =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.print("PIN: ");
                    String pin = sc.next();
                    System.out.print("Initial Amount: ");
                    double amt = sc.nextDouble();
                    bank.createAccount(name, pin, amt);
                    break;

                case 2:
                    System.out.print("Account No: ");
                    int acc = sc.nextInt();
                    System.out.print("PIN: ");
                    String p = sc.next();

                    if (bank.login(acc, p)) {
                        System.out.println("✔ Login Success!");

                        boolean logged = true;
                        while (logged) {
                            System.out.println("\n--- Account Operations ---");
                            System.out.println("1. Check Balance");
                            System.out.println("2. Deposit");
                            System.out.println("3. Withdraw");
                            System.out.println("4. Transfer");
                            System.out.println("5. Mini Statement");
                            System.out.println("6. Logout");
                            System.out.print("Choice: ");

                            int op = sc.nextInt();
                            switch (op) {
                                case 1:
                                    System.out.println("Balance: " + bank.getBalance(acc));
                                    break;
                                case 2:
                                    System.out.print("Enter amount: ");
                                    bank.deposit(acc, sc.nextDouble());
                                    break;
                                case 3:
                                    System.out.print("Enter amount: ");
                                    bank.withdraw(acc, sc.nextDouble());
                                    break;
                                case 4:
                                    System.out.print("Target Account: ");
                                    int to = sc.nextInt();
                                    System.out.print("Amount: ");
                                    bank.transfer(acc, to, sc.nextDouble());
                                    break;
                                case 5:
                                    bank.miniStatement(acc);
                                    break;
                                case 6:
                                    logged = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("✖ Invalid Account or PIN");
                    }
                    break;

                case 3:
                    System.exit(0);
                    sc.close();
            }
        }
    }
}