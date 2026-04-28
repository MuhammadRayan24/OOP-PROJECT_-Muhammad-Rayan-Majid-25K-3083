package BankingManagement;

import java.util.Scanner;

/*
 Central class is used so all major operations
 stay in one place instead of spreading logic
 across Main class and other files.

 This keeps project easier to manage.
*/
public class Bank {

    /*
     Generic container is used to clearly show
     custom generics while still storing accounts.
    */
    private RecordBook<Account> accounts = new RecordBook<Account>();

    /*
     Number generator kept separate so id creation
     follows one consistent pattern.
    */
    private String generateAccountNumber() {

        return "ACC" + (1000 + accounts.size());
    }

    /*
     Account creation grouped in one method so
     menu code stays clean and readable.
    */
    public void createAccount(Scanner sc) {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter CNIC: ");
        String cnic = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Set PIN: ");
        int pin = sc.nextInt();

        System.out.println("1. Savings");
        System.out.println("2. Current");
        System.out.print("Choose Type: ");

        int type = sc.nextInt();
        sc.nextLine();

        /*
         Customer object made separately so personal
         data remains independent from balance data.
        */
        Customer c = new Customer(name, cnic, phone);

        String accNo = generateAccountNumber();

        Account acc;

        /*
         Parent reference used so both child types
         can be handled through polymorphism.
        */
        if (type == 1) {

            acc = new SavingsAccount(accNo, c, pin, 0);

        } else {

            acc = new CurrentAccount(accNo, c, pin, 0);
        }

        accounts.add(acc);

        /*
         Immediate save prevents losing new account
         if program closes after creation.
        */
        saveAccounts();

        System.out.println("Account created.");
        System.out.println("Account No: " + accNo);
    }

    /*
     Search method reused in many features like
     deposit, withdraw, transfer and admin panel.
    */
    public Account searchAccount(String id) {

        /*
         Manual loop used because matching object
         can exist anywhere in records.
        */
        for (Account a : accounts.getAll()) {

            if (a.getAccountNumber().equals(id)) {

                return a;
            }
        }

        return null;
    }

    /*
     Deposit kept separate because same action may
     come from different menus later.
    */
    public void deposit(Scanner sc) {

        System.out.print("Enter Account No: ");

        String id = sc.nextLine();

        Account a = searchAccount(id);

        /*
         Null check avoids crash if user enters
         wrong account number.
        */
        if (a == null) {

            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter Amount: ");

        double amount;

        try {

            amount = sc.nextDouble();
            sc.nextLine();

        } catch (Exception e) {

            // clearing wrong input so scanner doesn't loop infinitely
            sc.nextLine();

            System.out.println("Invalid amount. Please enter numbers only.");
            return;
        }

        a.deposit(amount);

        saveAccounts();
    }

    /*
     Withdrawal method kept here so menu remains
     simple and account rules stay in child class.
    */
    public void withdraw(Scanner sc) {

        System.out.print("Enter Account No: ");

        String id = sc.nextLine();

        Account a = searchAccount(id);

        if (a == null) {

            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter Amount: ");

        double amount = sc.nextDouble();

        sc.nextLine();

        /*
         Runtime chooses correct child version of
         withdraw because of polymorphism.
        */
        a.withdraw(amount);

        saveAccounts();
    }

    /*
     Transfer grouped separately because it uses
     two accounts and more checks than deposit.
    */
    public void transferFunds(Scanner sc) {

        System.out.print("Sender No: ");

        String from = sc.nextLine();

        System.out.print("Receiver No: ");

        String to = sc.nextLine();

        Account sender = searchAccount(from);

        Account receiver = searchAccount(to);

        if (sender == null || receiver == null) {

            System.out.println("Invalid account.");
            return;
        }

        System.out.print("Enter Amount: ");

        double amount = sc.nextDouble();

        sc.nextLine();

        sender.transfer(receiver, amount);

        saveAccounts();
    }

        /*
         Customer panel is separated so normal users
         only see their own options, not admin tools.
        */
        public void customerLogin(Scanner sc) {

            System.out.print("Enter Account No: ");

            String id = sc.nextLine();

            Account a = searchAccount(id);

        /*
         Wrong id should stop process early to
         avoid unnecessary pin checking.
        */
            if (a == null) {

                System.out.println("Account not found.");
                return;
            }

            System.out.print("Enter PIN: ");

            int pin = sc.nextInt();

            sc.nextLine();

        /*
         Login check kept inside Account class so
         security logic stays reusable.
        */
            if (!a.login(pin)) {

                System.out.println("Wrong PIN.");
                return;
            }

            int choice;

            do {

                System.out.println("\n--- Customer Panel ---");

                System.out.println("1. Check Balance");

                System.out.println("2. View Statement");

                System.out.println("3. Change PIN");

                System.out.println("4. Request Loan");

                System.out.println("5. Logout");

                System.out.print("Choose: ");

                choice = sc.nextInt();

                sc.nextLine();

                switch (choice) {

                    case 1:

                        System.out.printf("Balance: Rs. %.2f%n", a.getBalance());
                        break;

                    case 2:

                    /*
                     Statement uses saved records
                     instead of recalculating past
                     transactions.
                    */
                        a.showStatement();
                        break;

                    case 3:

                        System.out.print("Enter New PIN: ");

                        int newPin = sc.nextInt();

                        sc.nextLine();

                        a.changePin(newPin);

                        saveAccounts();

                        System.out.println("PIN changed.");
                        break;

                    case 4:

                    /*
                     Request only marks pending.
                     Approval belongs to admin.
                    */
                        a.requestLoan();

                        saveAccounts();
                        break;

                    case 5:

                        System.out.println("Logged out.");
                        break;

                    default:

                        System.out.println("Invalid choice.");
                }

            } while (choice != 5);
        }

        /*
         Admin panel separated because staff actions
         should not mix with customer options.
        */
        public void adminPanel(Scanner sc) {

            System.out.print("Enter Admin Password: ");

            String pass = sc.nextLine();

        /*
         Using Simple password here because
         project focus is OOP, not encryption.
        */
            if (!pass.equals("admin123")) {

                System.out.println("Wrong password.");
                return;
            }

            int choice;

            do {

                System.out.println("\n--- Admin Panel ---");

                System.out.println("1. View Accounts");

                System.out.println("2. Search Account");

                System.out.println("3. Freeze Account");

                System.out.println("4. Unfreeze Account");

                System.out.println("5. Approve Loan");

                System.out.println("6. Delete Account");

                System.out.println("7. Total Funds");

                System.out.println("8. Exit");

                System.out.print("Choose: ");

                choice = sc.nextInt();

                sc.nextLine();

                switch (choice) {

                    case 1:
                        showAllAccounts();
                        break;

                    case 2:
                        adminSearch(sc);
                        break;

                    case 3:
                        freezeAccount(sc, true);
                        break;

                    case 4:
                        freezeAccount(sc, false);
                        break;

                    case 5:
                        approveLoan(sc);
                        break;

                    case 6:
                        deleteAccount(sc);
                        break;

                    case 7:
                        totalFunds();
                        break;

                    case 8:

                        System.out.println("Admin logout.");
                        break;

                    default:

                        System.out.println("Invalid choice.");
                }

            } while (choice != 8);
        }

        /*
         Showing all records helps admin inspect
         current users quickly.
        */
        public void showAllAccounts() {

            for (Account a : accounts.getAll()) {

                a.displayDetails();
            }
        }

        /*
         Separate search option saves admin time when
         only one record is needed.
        */
        public void adminSearch(Scanner sc) {

            System.out.print("Enter Account No: ");

            String id = sc.nextLine();

            Account a = searchAccount(id);

            if (a == null) {

                System.out.println("Account not found.");

            }
            else {
                a.displayDetails();
            }
        }

        /*
         Same method handles freeze and unfreeze so
         duplicate code is avoided.
        */
        public void freezeAccount(
                Scanner sc, boolean status) {

            System.out.print("Enter Account No: ");

            String id = sc.nextLine();

            Account a = searchAccount(id);

            if (a == null) {

                System.out.println("Account not found.");
                return;
            }

            a.setFrozen(status);

            saveAccounts();

            if (status) {

                System.out.println("Account frozen.");

            } else {

                System.out.println("Account unfrozen.");
            }
        }

        /*
         Loan approval is separated because it is an
         admin level decision, not customer action.
        */
        public void approveLoan(Scanner sc) {

            System.out.print("Enter Account No: ");

            String id = sc.nextLine();

            Account a = searchAccount(id);

            if (a == null) {

                System.out.println("Account not found.");
                return;
            }

        /*
         Approval only works if customer actually
         requested loan before.
        */
            if (a.isLoanRequested()) {

            /*
             using Fixed amount to keep project simple and
             avoids complex calculations.
            */
                a.deposit(5000);

                a.setLoanRequested(false);

                saveAccounts();

                System.out.println("Loan approved.");

            }
            else {
                System.out.println("No pending request.");
            }
        }

        /*
         Delete feature added so admin can remove
         unused or incorrect records.
        */
        public void deleteAccount(Scanner sc) {

            System.out.print("Enter Account No: ");

            String id = sc.nextLine();

            Account a = searchAccount(id);

            if (a == null) {
                System.out.println("Account not found.");
                return;
            }

            accounts.getAll().remove(a);

        /*
         Saving after delete is important because
         removed records should stay removed.
        */
            saveAccounts();

            System.out.println("Account deleted.");
        }

        /*
         Combined funds report gives admin quick view
         of money currently held in system.
        */
        public void totalFunds() {

            double sum = 0;

            for (Account a : accounts.getAll()) {

                sum += a.getBalance();
            }

            System.out.println("Total Funds: Rs." + sum);
        }

        /*
         Full data saved instead of only balance so
         system can rebuild complete accounts later.
        */
        public void saveAccounts() {

            String data = "";

            for (Account a : accounts.getAll()) {

                // information printing for the machine on console and storing it
                data += a.getAccountNumber() + ","
                        + a.getCustomer().getName() + ","
                        + a.getCustomer().getCnic() + ","
                        + a.getCustomer().getPhone() + ","
                        + a.getPin() + ","
                        + a.getClass().getSimpleName() + ","
                        + a.getBalance() + ","
                        + a.isFrozen() + ","
                        + a.isLoanRequested()
                        + "\n";

                // user friendly printing format with proper headings
                data += "----------------------------------\n";
                data += "Account Number: " + a.getAccountNumber() + "\n";
                data += "Name          : " + a.getCustomer().getName() + "\n";
                data += "CNIC          : " + a.getCustomer().getCnic() + "\n";
                data += "Phone         : " + a.getCustomer().getPhone() + "\n";
                data += "PIN           : " + a.getPin() + "\n";
                data += "Type          : " + a.getClass().getSimpleName() + "\n";
                data += String.format("Balance       : %.2f\n", a.getBalance());
                data += "Frozen        : " + a.isFrozen() + "\n";
                data += "Loan Requested: " + a.isLoanRequested() + "\n";
                data += "----------------------------------\n\n";
            }

            FileHandler.saveData(data);
        }

        /*
         Load method rebuilds objects so saved file
         becomes working accounts again on startup.
        */
        public void loadAccounts() {

            String data = FileHandler.loadData();

            if (data.equals("")) {
                return;
            }

            String lines[] = data.split("\n");

            for (String line : lines) {
                if (!line.contains(",")) {
                    continue; // skip readable lines
                }
                String parts[] = line.split(",");

            /*
             Length check avoids crash if any line
             is incomplete or damaged.
            */
                if (parts.length == 9) {

                    String accNo = parts[0];
                    String name = parts[1];
                    String cnic = parts[2];
                    String phone = parts[3];

                    int pin = Integer.parseInt(parts[4]);

                    String type = parts[5];

                    double balance = Double.parseDouble(parts[6]);

                    boolean frozen = Boolean.parseBoolean(parts[7]);

                    boolean loan = Boolean.parseBoolean(parts[8]);

                    Customer c = new Customer(name, cnic, phone);

                    Account a;

                /*
                 Type saved in file helps recreate
                 original child object correctly.
                */
                    if (type.equals("SavingsAccount")) {

                        a = new SavingsAccount(accNo, c, pin, balance);

                    } else {
                        a = new CurrentAccount(accNo, c, pin, balance);
                    }

                    a.setFrozen(frozen);
                    a.setLoanRequested(loan);

                    accounts.add(a);
                }
            }
        }
    }