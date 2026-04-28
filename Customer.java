package BankingManagement;

/*
 Personal information is kept in a separate class
 so account classes stay focused on banking tasks.

 This also makes future expansion easier if one
 customer needs more details later.
*/
public class Customer {

    private String name;
    private String cnic;
    private String phone;

    /*
     Constructor used so object is complete at
     the moment it is created.
     This avoids half-filled customer objects.
    */
    public Customer(String name, String cnic, String phone) {

        this.name = name;
        this.cnic = cnic;
        this.phone = phone;
    }

    /*
     Getters are used instead of direct access
     to protect data and follow encapsulation.
    */
    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }

    public String getPhone() {
        return phone;
    }
}