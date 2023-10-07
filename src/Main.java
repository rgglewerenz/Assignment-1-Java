//Ross Lewerenz
//I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else's work as my own
//Java 20

import java.util.*;

public class Main {
    private static final double SALES_TAX = 1.06;

    public static Product[] products;

    static {
        try {
            products = new Product[]{
                    new Product(10, "pencil", "a", "normal pencil"),
                    new Product(3, "sharpener", "b", "normal sharpener"),
                    new Product(5, "eraser", "c", "normal eraser"),
                    new Product(20, "pencil case", "d", "normal pencil case"),
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        var cart = new Cart("Ross");

        //Prints the products
        printProducts();

        //TODO: Adds products into cart


        //Prints the details of the cart
        cart.printCart(SALES_TAX);
    }


    /**
     * Prints all items in the product catalog
     */
    private static void printProducts(){
        StringBuilder sb = new StringBuilder();
        sb.append("Shop items\n");

        sb.append("---------------------------------------------------------------\n");
        sb.append(String.format("%-20s\t%-10s\t%-5s\t%-20s\n", "Name","Price", "Code", "Description"));

        for(var product : products){
            sb.append(String.format("%-20s\t", product.name));
            sb.append(String.format("%-10s\t", String.format("$%.2f", product.price)));
            sb.append(String.format("%-5s\t", product.code));
            sb.append(String.format("%-20s\n", product.description));
        }
        sb.append("---------------------------------------------------------------\n\n");
        System.out.println(sb.toString());
    }
}

class Product{
    public double price;
    public String name;
    public String code;
    public String description;
    public Product(double price, String name, String code, String description) throws Exception {
        if(price < 0)
            throw new Exception("price can not be negative");
        this.price = price;
        this.name = name;
        this.code = code;
        this.description = description;
    }
}

class Cart {
    private ArrayList<Product> cartProducts;
    private final String customerName;

    public Cart(String customerName){
        cartProducts = new ArrayList<>();
        this.customerName = customerName;
    }


    /**
     * @param val Product to be added to the cart
     * @param amt Amount of the item that should be added to the cart,
     * @throws Exception throws an exception if amt is 0 or negative
     */
    public void addProduct(Product val, int amt) throws Exception {
        if(amt < 0)
            throw new Exception("amt can not be negative");

        for(int i = 0; i < amt; i++){
            cartProducts.add(val);
        }
    }

    /**
     * Prints all items in the cart, along with the customer's name
     * @param salesTax sales tax to be added to the Sub Total as a percentage i.e. 0.06, or 1.06
     * @throws Exception Throws exception if sales tax is 0 or negative
     */
    public void printCart(double salesTax) throws Exception {
        if(salesTax < 0)
            throw new Exception("sales tax must be greater than 0");

        //Corrects if user enters sales tax as 0.06
        if(salesTax < 1)
            salesTax += 1;

        Dictionary<Product, Integer> productCount = new Hashtable<>();
        StringBuilder sb = new StringBuilder();
        sb.append(customerName+"'s order\n");

        for(var product : cartProducts){
            if(productCount.get(product) != null){
                Integer val = productCount.get(product);
                productCount.remove(product);
                productCount.put(product, ++val);
            }
            else{
                productCount.put(product, 1);
            }
        }

        Enumeration<Product> k = productCount.keys();

        sb.append("-------------------------------------------------------------------------\n");
        sb.append(String.format("%-20s\t%-10s\t%-10s\t%-10s\t%-10s\n", "Name", "Price", "Count", "Sub Total", "Gross Total"));
        while(k.hasMoreElements()){
            var product = k.nextElement();
            sb.append(String.format("%-20s\t", product.name) +
                      String.format("%-10s\t", String.format("$%.2f", product.price)) +
                      String.format("%-10s\t", String.format("%-10s", productCount.get(product))) +
                      String.format("%-10s\n", String.format("$%.2f", product.price * (double)productCount.get(product)))
            );
        }
        sb.append(String.format("%-20s\t", "Total") +
                        String.format("%-10s\t","") +
                        String.format("%-10s\t","") +
                        String.format("%-10s\t", String.format("$%.2f",calcTotal())) +
                        String.format("%-10s\n", String.format("$%.2f",calcTotal() * salesTax))
        );
        sb.append("-------------------------------------------------------------------------\n");



        System.out.println(sb.toString());
    }

    /**
     * @return the total of all items in the cart
     */
    public double calcTotal(){
        double total = 0;
        for(var product: cartProducts){
            total += product.price;
        }
        return total;
    }
}