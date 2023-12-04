package utils;

import DataObjects.Cart;
import DataObjects.Customer;

import java.util.Date;
import java.util.function.Function;

public class CartHandler {
    Function<Cart, Integer> func;

    private Cart cart = new Cart(new Customer(-1, "invaid", "", new Date(), -1));

    public void setCustomer(Customer customer) {
        cart = new Cart(customer);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void onFinalizedCart(Function<Cart, Integer> func){
        this.func = func;
    }


    public void finalizeCart(){
        if(func != null){
            func.apply(this.cart);
        }
    }
}



