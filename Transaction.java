package BankingManagement;

import java.time.LocalDateTime;

/*
 Each money action is saved as separate object
 so statement history remains organized.
 and this is cleaner than storing plain strings.
*/
public class Transaction {

    private String type;
    private double amount;
    private String dateTime;

    /*
     Time is captured automatically because users
     usually remember amount, not exact timing.
     System stores timing for them.
    */
    public Transaction(String type, double amount) {

        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now().toString();
    }

    /*
     Display method stays inside class so format
     remains same wherever history is shown.
    */
    public void show() {

        System.out.println(type + " | Rs." + amount + " | " + dateTime);
    }
}