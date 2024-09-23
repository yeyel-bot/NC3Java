import java.util.Scanner;

// User class to store user details
class User {
    private int id;
    private int pin;
    private String name;
    private double balance;

    // Constructor
    public User(int id, int pin, String name, double balance) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    // Setters
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method to add money (Cash-in)
    public void cashIn(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Successfully added $" + amount + " to your account.");
        } else {
            System.out.println("Invalid amount. Try again.");
        }
    }

    // Method to transfer money to another user
    public boolean transferMoney(User recipient, double amount) {
        if (this.balance >= amount && amount > 0) {
            this.balance -= amount;
            recipient.setBalance(recipient.getBalance() + amount);
            System.out.println("Successfully transferred $" + amount + " to " + recipient.getName());
            return true;
        } else {
            System.out.println("Insufficient balance or invalid amount.");
            return false;
        }
    }
}

// Main banking app
public class Main {
    static Scanner scanner = new Scanner(System.in);

    // Dummy user data
    static User[] users = {
            new User(412435, 7452, "Chris Sandoval", 32000),
            new User(264863, 1349, "Marc Yim", 1000)
    };

    // Method to login
    public static User login() {
        System.out.print("Enter your User ID: ");
        int enteredId = scanner.nextInt();
        System.out.print("Enter your 4-digit PIN: ");
        int enteredPin = scanner.nextInt();

        for (User user : users) {
            if (user.getId() == enteredId && user.getPin() == enteredPin) {
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                return user;
            }
        }
        System.out.println("Invalid ID or PIN. Try again.");
        return null;
    }

    // Menu to display options for the logged-in user
    public static void showMenu(User loggedUser) {
        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Cash-in");
            System.out.println("3. Money Transfer");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1: // Check balance
                    System.out.println("Your current balance is: $" + loggedUser.getBalance());
                    break;
                case 2: // Cash-in
                    System.out.print("Enter amount to cash-in: ");
                    double amount = scanner.nextDouble();
                    loggedUser.cashIn(amount);
                    break;
                case 3: // Money Transfer
                    System.out.print("Enter recipient's User ID: ");
                    int recipientId = scanner.nextInt();
                    User recipient = findUserById(recipientId);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        loggedUser.transferMoney(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 4: // Logout
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Method to find a user by their ID
    public static User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    // Main method to run the banking app
    public static void main(String[] args) {
        while (true) {
            User loggedUser = login();
            if (loggedUser != null) {
                showMenu(loggedUser);
            }
        }
    }
}
