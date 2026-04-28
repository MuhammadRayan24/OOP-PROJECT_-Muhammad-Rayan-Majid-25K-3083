package BankingManagement;

/*
 Savings account uses simpler withdrawal rules.

 This child class exists so account types are
 separated realistically instead of treating all
 users the same.
*/
public class SavingsAccount extends Account {

    /*
     Constructor sends common values upward
     because parent class already knows how to
     store shared account information.
    */
    public SavingsAccount(String accountNumber, Customer customer, int pin, double balance) {

        super(accountNumber, customer, pin, balance);
    }

    /*
     Savings users should only withdraw available
     balance, so no overdraft is allowed here.
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

        if (amount <= balance) {

            balance -= amount;

            /*
             History updated immediately so later
             statement remains trustworthy.
            */
            history.add(new Transaction("Withdraw", amount));

        } else {
            System.out.println("Insufficient balance.");
        }
    }

    /*
     Extra method added because savings accounts
     usually reward stored money with interest.
    */
    public void addInterest() {

        double interest = balance * 0.05;

        balance += interest;

        /*
         Interest is also recorded so user can see
         why balance increased.
        */
        history.add(new Transaction("Interest Added", interest));
    }
}