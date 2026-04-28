package BankingManagement;

/*
 Separate interface is used because loan support
 may apply to some account types now or later.

 This keeps design open for extension.
*/
public interface Loanable
{
    /*
     Method declared here so loan feature can be
     guaranteed wherever this interface is used.
    */
    void requestLoan();
}