package BankingManagement;

/*
 Current account is separated because business
 style accounts often allow extra withdrawal
 flexibility through overdraft.
*/
public class CurrentAccount extends Account {

    /*
     Limit stored once here so rules can be changed
     later without touching withdraw logic.
    */
    private double limit = 5000;

    public CurrentAccount(String accountNumber, Customer customer, int pin, double balance) {

        super(accountNumber, customer, pin, balance);
    }

    /*
     Current accounts can go beyond balance within
     safe overdraft range.
    */
    public void withdraw(double amount) {

        if (frozen) {
            System.out.println("Account is frozen.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= balance + limit) {

            balance -= amount;

            /*
             Transaction saved so borrowed usage
             still appears in statement.
            */
            history.add(new Transaction("Withdraw", amount));

        } else {
            System.out.println("Overdraft limit exceeded.");
        }
    }
}