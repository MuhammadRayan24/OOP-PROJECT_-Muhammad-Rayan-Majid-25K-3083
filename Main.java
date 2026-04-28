package BankingManagement;

import java.util.Scanner;

/*
 Program starts here.

 Main class only controls menu flow so business
 logic stays inside Bank class where it belongs.
*/
public class Main {

    public static void main(String[] args) {

        Scanner sc =
                new Scanner(System.in);

        Bank bank = new Bank();

        /*
         Old data loaded first so user continues
         previous records instead of starting fresh
         every time program runs.
        */
        bank.loadAccounts();

        int choice = 0;

        do {

            System.out.println("\n======================");

            System.out.println(" Banking System ");

            System.out.println("======================");

            System.out.println("1. Create Account");

            System.out.println("2. Customer Login");

            System.out.println("3. Deposit");

            System.out.println("4. Withdraw");

            System.out.println("5. Transfer");

            System.out.println("6. Admin Panel");

            System.out.println("7. Show Accounts");

            System.out.println("8. Exit");

            System.out.print("Enter Choice: ");

            try {

                /*
                 Try block used because wrong input
                 like text should not crash project.
                */
                choice = sc.nextInt();

                sc.nextLine();

            } catch (Exception e) {

                System.out.println("Enter numbers only.");

                /*
                 Invalid input cleared so scanner
                 can work properly in next turn.
                */
                sc.nextLine();

                choice = 0;
            }

            switch (choice) {

                case 1:
                    bank.createAccount(sc);
                    break;

                case 2:
                    bank.customerLogin(sc);
                    break;

                case 3:
                    bank.deposit(sc);
                    break;

                case 4:
                    bank.withdraw(sc);
                    break;

                case 5:
                    bank.transferFunds(sc);
                    break;

                case 6:
                    bank.adminPanel(sc);
                    break;

                case 7:
                    bank.showAllAccounts();
                    break;

                case 8:

                    /*
                     Final save on exit gives extra
                     safety if last action changed data.
                    */
                    bank.saveAccounts();

                    System.out.println("Thank You.");
                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 8);

        /*
         Scanner closed at end because no more
         keyboard input is needed.
        */
        sc.close();
    }
}