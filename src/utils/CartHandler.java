package utils;

import DataObjects.Cart;
import DataObjects.Customer;

import java.util.Date;

public class CartHandler {

    private Cart cart = new Cart(new Customer(-1, "invaid", "", new Date(), -1));

    public void setCustomer(Customer customer){
        cart = new Cart(customer);
    }

    public Cart getCart(){
        return cart;
    }
}
