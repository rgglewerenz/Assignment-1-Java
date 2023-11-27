import utils.ArrayHelper;
import utils.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import DataObjects.*;
import java.text.SimpleDateFormat;

public class Console {
    private static final double SALES_TAX = 1.06;
    private static final String ADMIN_PASSWORD = "MCS3603";

    public static ArrayList<Product> products;

    public static ArrayList<Customer> customers;

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
        String customerIDStr = "";
        boolean invalid = true;
        while(invalid)
        {
            System.out.println("Please enter the customer ID: ");
            customerIDStr = scanner.nextLine();
            try{
                int intValue = Integer.parseInt(customerIDStr);
                if(ArrayHelper.Find(customers, x -> { return x.customerID ==  intValue; }).isEmpty()){
                    System.out.println("Customer does not exist with the id: " + intValue);
                }
                invalid = ArrayHelper.Find(customers, x -> { return x.customerID ==  intValue; }).isEmpty();
            }catch (NumberFormatException e){
                System.out.println("invalid number");
            }
            catch (NullPointerException e){

            }
        }
        int customerID = Integer.parseInt(customerIDStr);

        var customer = ArrayHelper.Find(customers, x -> { return x.customerID ==  customerID; }).get(0);

        System.out.println("Welcome back " + customer.getFullName());
        System.out.println("Your last transaction was " +
                                    (new SimpleDateFormat("MM/dd/yyyy")).format(customer.lastTransactionDate) +
                                    " and the price was "+ customer.priceOfLastTransaction + "\n\n");
        return new Cart(customer);
    }

    public static void main(String[] args) throws Exception {
        products = FileReader.readProductsFromFile("data/products.items");
        customers = FileReader.readCustomersFromFile("data/customers.items");

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
