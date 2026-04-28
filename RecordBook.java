package BankingManagement;

import java.util.ArrayList;

/*
 Generic class is created to clearly demonstrate
 custom generics instead of relying only on Java's
 built in collections directly.

 Same class can store Accounts today and other
 object types later without rewriting logic.
*/
public class RecordBook<T> {

    private ArrayList<T> list = new ArrayList<T>();

    /*
     Add method is centralized here so storing
     records follows one consistent path.
    */
    public void add(T item) {
        list.add(item);
    }

    /*
     Returning by index is useful when data needs
     direct access during future upgrades.
    */
    public T get(int index) {
        return list.get(index);
    }

    /*
     The size method avoids exposing internal list
     everywhere just to count records.
    */
    public int size() {
        return list.size();
    }

    /*
     Full list is returned when loops or searches
     need to inspect all stored objects.
    */
    public ArrayList<T> getAll() {
        return list;
    }
}