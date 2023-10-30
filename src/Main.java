//Ross Lewerenz
//I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else's work as my own
//Java 20

import java.util.*;

public class Main {
    private static final double SALES_TAX = 1.06;
    private static final String ADMIN_PASSWORD = "MCS3603";

    public static ArrayList<Product> products;

    static {
        try {
            products = new ArrayList<>();
            products.add(new Product(10, "pencil", "a", "normal pencil"));
            products.add(new Product(3, "sharpener", "b", "normal sharpener"));
            products.add(new Product(5, "eraser", "c", "normal eraser"));
            products.add(new Product(20, "pencil case", "d", "normal pencil case"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Product createProduct() throws Exception {
        Scanner scanner = new Scanner(System.in);

        String productName;
        {
            System.out.println("Enter the name of the new product: ");
            productName = scanner.nextLine();
        }

        String productCode;
        while (true)
        {
            System.out.println("Enter the code of the new product: ");
            productCode = scanner.nextLine();
            boolean contains = false;
            for (var product :products) {
                if(product.code.equals(productCode)){
                    System.out.println("Error: " + product.name + " already has a product code of " + product.code);
                    contains = true;
                    break;
                }
            }
            if(contains)
                continue;
            break;
        }
        double productPrice;
        {

            System.out.println("Enter the price of the new product: ");
            productPrice = getDoubleFromScanner(scanner, 0);
        }
        String productDescription;
        {
            System.out.println("Enter the description of the new product: ");
            productDescription = scanner.nextLine();
        }
        return new Product(productPrice, productName, productCode, productDescription);
    }

    private static void adminProductInsertion() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true){
            clearScreen();
            printProducts();
            //Checks if admin wants to add a new product
            {
                boolean shouldContinue = false;
                while (true){
                    System.out.print("Do you want to add other products (y/n): ");
                    var input = scanner.nextLine();
                    if(input.isEmpty())
                    {
                        System.out.println("Invalid input");
                        continue;
                    }
                    if(input.charAt(0) == 'y'){
                        shouldContinue = true;
                        break;
                    }
                    else if (input.charAt(0) == 'n')
                    {
                        break;
                    }
                    System.out.println("Invalid input");
                }
                if(!shouldContinue)
                    break;
            }
            products.add(createProduct());
        }
    }

    private static Cart createCart(){
        Scanner scanner = new Scanner(System.in);
        String firstName;
        {
            System.out.println("Please enter your first name: ");
            firstName = scanner.next();
            scanner.nextLine();
        }
        String lastName;
        {
            System.out.println("Please enter your last name: ");
            lastName = scanner.next();
            scanner.nextLine();
        }
        return new Cart(firstName + " " + lastName);
    }

    public static void main(String[] args) throws Exception {
        if(args.length >= 1 && args[0].equals(ADMIN_PASSWORD)){
            adminProductInsertion();
        }

        var cart = createCart();

        productSelection(cart);

        Scanner scanner = new Scanner(System.in);

        //Checks if they will pay in full

        boolean paidInFull = false;
        {
            while (true){
                System.out.print("Do you want to pay " + String.format("$%.2f", cart.calcTotal(false)) +" in full (y/n): ");
                var input = scanner.nextLine();
                if(input.isEmpty())
                {
                    System.out.println("Invalid input");
                    continue;
                }
                if(input.charAt(0) == 'y'){
                    paidInFull = true;
                    break;
                }
                else if (input.charAt(0) == 'n')
                {
                    break;
                }
                System.out.println("Invalid input");
            }
        }

        //Prints the details of the cart
        cart.printCart(SALES_TAX, paidInFull);
    }

    public static void productSelection(Cart cart){
        Scanner scanner = new Scanner(System.in);
        while(true){
            clearScreen();
            printProducts();
            //Select Product
            {
                while (true){
                    try{
                        System.out.print("Select a product using the product code: ");
                        var code = scanner.nextLine();
                        var product = getProduct(code);
                        System.out.print("Count: ");
                        var count = getIntFromScanner(scanner, 1);
                        cart.addProduct(product, count);
                        break;
                    }catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
            //Checks if user wants to continue
            {
                boolean shouldContinue = false;
                while (true){
                    System.out.print("Do you want to add other products (y/n): ");
                    var input = scanner.nextLine();
                    if(input.isEmpty())
                    {
                        System.out.println("Invalid input");
                        continue;
                    }
                    if(input.charAt(0) == 'y'){
                        shouldContinue = true;
                        break;
                    }
                    else if (input.charAt(0) == 'n')
                    {
                        break;
                    }
                    System.out.println("Invalid input");
                }
                if(!shouldContinue)
                    break;
            }

        }
    }

    public static int getIntFromScanner(Scanner in, int min){
        while (true){
            var val = in.nextInt();
            if(val < min){
                in.nextLine();
                System.out.println("Invalid input, must be above " + min);
                continue;
            }
            in.nextLine();
            return val;
        }
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static Product getProduct(String code) throws Exception {
        for (Product product : products) {
            if (product.code.equals(code)) {
                return product;
            }
        }
        throw new Exception("Unable to find product with specified code");
    }

    private static double getDoubleFromScanner(Scanner in, double min){
        while (true){
            var val = in.nextDouble();
            if(val < min){
                in.nextLine();
                System.out.println("Invalid input, must be above " + min);
                continue;
            }
            in.nextLine();
            return val;
        }
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
        System.out.println(sb);
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
    private final ArrayList<Product> cartProducts;
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
    public void printCart(double salesTax, boolean paidInFull) throws Exception {
        if(salesTax < 0)
            throw new Exception("sales tax must be greater than 0");

        //Corrects if user enters sales tax as 0.06
        if(salesTax < 1)
            salesTax += 1;

        StringBuilder sb = new StringBuilder();
        sb.append(customerName).append("'s order\n");

        var productCount = getProductCounts();

        Enumeration<Product> k = productCount.keys();

        sb.append("-------------------------------------------------------------------------\n");
        sb.append(String.format("%-20s\t%-10s\t%-10s\t%-10s\t%-10s\n", "Name", "Price", "Count", "Sub Total", "Gross Total"));
        while(k.hasMoreElements()){
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
    private Dictionary<Product, Integer> getProductCounts(){
        Dictionary<Product, Integer> productCount = new Hashtable<>();
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
        return productCount;
    }

    /**
     * @param product desires product
     * @param productCount dictionary with products and their count
     * @return total of only specific products
     */
    private double calcProductTotal(Product product, Dictionary<Product, Integer> productCount){
        if(productCount.get(product) >= 10){
            return (product.price * (double) (productCount.get(product) - 1)) + (product.price *= 0.9);
        }
        return product.price * (double) productCount.get(product);
    }

    /**
     * @param paidInFull whether they paid in full
     * @return the total of all items in the cart
     */
    public double calcTotal(boolean paidInFull){
        double total = 0;
        var counts = getProductCounts();
        Enumeration<Product> k = counts.keys();
        while(k.hasMoreElements()) {
            total += calcProductTotal(k.nextElement(), counts);
        }

        if(paidInFull)
            total *= 0.95;

        return total;
    }
}