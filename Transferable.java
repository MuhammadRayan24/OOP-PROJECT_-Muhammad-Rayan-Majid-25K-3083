package BankingManagement;
/*
 Interface is used here to show that transfer
 is a capability, not identity.

 Any future class that can send money may also
 implement this without forcing inheritance.
*/
public interface Transferable {

    /*
     Method kept in interface so transfer rules
     can stay flexible while contract remains same.
    */
    void transfer(Account receiver, double amount);
}