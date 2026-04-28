package BankingManagement;

import java.util.ArrayList;

/*
 Abstract parent class is used because all account
 types share common data like balance, pin and id.

 At the same time, withdraw rules can differ, so
 child classes should decide that behavior.
*/
public abstract class Account implements Transferable, Loanable {

    protected String accountNumber;
    protected double balance;
    protected int pin;

    protected boolean frozen;
    protected boolean loanRequested;

    protected Customer customer;

    /*
     Every account keeps separate history so one
     user's actions never mix with another user.
    */
    protected ArrayList<Transaction> history = new ArrayList<Transaction>();

    /*
     Constructor ensures object starts with all
     important values already assigned.
    */
    public Account(String accountNumber, Customer customer, int pin, double balance) {

        this.accountNumber = accountNumber;
        this.customer = customer;
        this.pin = pin;
        this.balance = balance;

        /*
         New account starts active because there is
         no reason to freeze it initially.
        */
        frozen = false;

        /*
         Loan request starts false so admin only
         sees genuine later requests.
        */
        loanRequested = false;
    }

    /*
     Deposit logic is shared by all account types,
     so keeping it here avoids repeated code.
    */
    public void deposit(double amount) {

        /*
         Frozen accounts are blocked to reflect
         admin restrictions in system.
        */
        if (frozen) {
            System.out.println("Account is frozen.");
            return;
        }

        /*
         Negative or zero values are rejected
         because they make no banking sense.
        */
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        balance += amount;

        /*
         Recording every change helps statement
         feature and increases transparency.
        */
        history.add(new Transaction("Deposit", amount));
    }

    /*
     Child classes must define this because each
     account type can have different limits.
    */
    public abstract void withdraw(double amount);

    /*
     Transfer is placed here because any account
     can send money using same base logic.
    */
    public void transfer(Account receiver, double amount) {

        if (frozen) {
            System.out.println("Account is frozen.");
            return;
        }

        /*
         Null check avoids crash if receiver id
         was entered incorrectly.
        */
        if (receiver == null) {
            System.out.println("Receiver not found.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        /*
         Small fee added to make project feel more
         realistic than simple classroom examples.
        */
        double fee = 10;

        if (balance >= amount + fee) {

            balance = balance - amount - fee;
            receiver.balance = receiver.balance + amount;

            /*
             Both sides are recorded because sender
             and receiver need correct history.
            */
            history.add(new Transaction("Transfer Sent", amount));

            receiver.history.add(new Transaction("Transfer Received", amount));

            System.out.println("Transfer successful.");

        } else {
            System.out.println("Insufficient balance.");
        }
    }

    /*
     Separate login method keeps security check
     reusable from any menu later.
    */
    public boolean login(int enteredPin) {
        return pin == enteredPin;
    }

    /*
     Pin change is wrapped in method so direct
     editing from outside is avoided.
    */
    public void changePin(int newPin) {
        pin = newPin;
    }

    /*
     Loan request only changes a flag because
     approval should remain admin authority.
    */
    public void requestLoan() {

        loanRequested = true;

        System.out.println("Loan request submitted.");
    }

    /*
     Statement method loops through saved records
     instead of recalculating past actions.
    */
    public void showStatement() {

        if (history.size() == 0) {
            System.out.println("No history found.");
            return;
        }

        for (Transaction t : history) {
            t.show();
        }
    }

    /*
     Display kept here because all accounts share
     same basic identity information.
    */
    public void displayDetails() {

        System.out.println("----------------------");

        System.out.println("Account No: " + accountNumber);

        System.out.println("Name      : " + customer.getName());

        System.out.printf("Balance   : %.2f%n", balance);

        System.out.println("Frozen    : " + frozen);
    }

    /*
     Getters used for safe access and to support
     encapsulation principles.
    */
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public int getPin() {
        return pin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean value) {

        /*
         Setter allows admin panel to control
         status without exposing variable directly.
        */
        frozen = value;
    }

    public boolean isLoanRequested() {
        return loanRequested;
    }

    public void setLoanRequested(boolean value) {

        loanRequested = value;
    }
}