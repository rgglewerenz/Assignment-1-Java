package DataObjects;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Cart {
    public final ArrayList<Product> cartProducts;
    public final Customer customer;

    public Cart(Customer customer) {
        cartProducts = new ArrayList<>();
        this.customer = customer;
    }

    public Customer getCustomer(){
        return customer;
    }


    /**
     * @param val Product to be added to the cart
     * @param amt Amount of the item that should be added to the cart,
     * @throws Exception throws an exception if amt is 0 or negative
     */
    public void addProduct(Product val, int amt) throws Exception {
        if (amt < 0)
            throw new Exception("amt can not be negative");

        for (int i = 0; i < amt; i++) {
            cartProducts.add(val);
        }
    }

    public void removeProduct(Product val, int amt) throws Exception {
        if (amt < 0)
            throw new Exception("amt can not be negative");


        for (int i = 0; i < amt; i++) {
            var index = cartProducts.indexOf(val);
            if(index == -1)
                return;
            cartProducts.remove(val);
        }
    }

    /**
     * Prints all items in the cart, along with the customer's name
     *
     * @param salesTax sales tax to be added to the Sub Total as a percentage i.e. 0.06, or 1.06
     * @throws Exception Throws exception if sales tax is 0 or negative
     */
    public void printCart(double salesTax, boolean paidInFull) throws Exception {
        if (salesTax < 0)
            throw new Exception("sales tax must be greater than 0");

        //Corrects if user enters sales tax as 0.06
        if (salesTax < 1)
            salesTax += 1;

        StringBuilder sb = new StringBuilder();
        sb.append(customer.getFullName()).append("'s order\n");

        var productCount = getProductCounts();

        Enumeration<Product> k = productCount.keys();

        sb.append("-------------------------------------------------------------------------\n");
        sb.append(String.format("%-20s\t%-10s\t%-10s\t%-10s\t%-10s\n", "Name", "Price", "Count", "Sub Total", "Gross Total"));
        while (k.hasMoreElements()) {
            var product = k.nextElement();
            sb.append(String.format("%-20s\t", product.name))
                    .append(String.format("%-10s\t", String.format("$%.2f", product.price)))
                    .append(String.format("%-10s\t", String.format("%-10s", productCount.get(product))))
                    .append(String.format("%-10s\n", String.format("$%.2f", calcProductTotal(product, productCount))));
        }
        sb.append(String.format("%-20s\t", "Total"))
                .append(String.format("%-10s\t", ""))
                .append(String.format("%-10s\t", ""))
                .append(String.format("%-10s\t", String.format("$%.2f", calcTotal(paidInFull))))
                .append(String.format("%-10s\n", String.format("$%.2f", calcTotal(paidInFull) * salesTax)));
        sb.append("-------------------------------------------------------------------------\n");


        System.out.println(sb);
    }


    /**
     * @return returns a dictionary with the products as keys and the count as the value
     */
    public Dictionary<Product, Integer> getProductCounts() {
        Dictionary<Product, Integer> productCount = new Hashtable<>();
        for (var product : cartProducts) {
            if (productCount.get(product) != null) {
                Integer val = productCount.get(product);
                productCount.remove(product);
                productCount.put(product, ++val);
            } else {
                productCount.put(product, 1);
            }
        }
        return productCount;
    }

    /**
     * @param product      desires product
     * @param productCount dictionary with products and their count
     * @return total of only specific products
     */
    public double calcProductTotal(Product product, Dictionary<Product, Integer> productCount) {
        if (productCount.get(product) >= 10) {
            return (product.price * (double) (productCount.get(product) - 1)) + (product.price *= 0.9);
        }
        return product.price * (double) productCount.get(product);
    }

    /**
     * @param paidInFull whether they paid in full
     * @return the total of all items in the cart
     */
    public double calcTotal(boolean paidInFull) {
        double total = 0;
        var counts = getProductCounts();
        Enumeration<Product> k = counts.keys();
        while (k.hasMoreElements()) {
            total += calcProductTotal(k.nextElement(), counts);
        }

        if (paidInFull)
            total *= 0.95;

        return total;
    }
}
