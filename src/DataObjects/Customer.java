package DataObjects;

import java.util.Date;

public class Customer {
    public final String firstName;
    public final String lastName;
    public final int customerID;
    public final Date lastTransactionDate;
    public final double priceOfLastTransaction;

    public Customer(int customerID, String firstName, String lastName, Date lastTransactionDate, double priceOfLastTransaction) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastTransactionDate = lastTransactionDate;
        this.priceOfLastTransaction = priceOfLastTransaction;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
