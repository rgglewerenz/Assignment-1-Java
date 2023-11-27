package utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import DataObjects.*;

public class FileReader {
    public static ArrayList<Product> readProductsFromFile(String filename) throws Exception {
        Scanner scanner = FileHelper.getFileScanner(filename);
        var products = new ArrayList<Product>();

        int i = 1;
        while (scanner.hasNext()){
            var line = scanner.nextLine();
            var items = line.split(",");
            if(items.length != 4){
                throw new Exception("Unable to read product on line " + i + "\nReason: Invalid comma count on line.");
            }

            var price = Double.parseDouble(items[0]);
            if(price <= 0){
                throw new Exception("Unable to read product on line " + i + "\nReason: Invalid price in 1st row.");
            }

            var name = items[1].trim();
            var code = items[2].trim();

            if(!ArrayHelper.Find(products, x -> { return x.code.equals(code); }).isEmpty()){
                throw new Exception("Unable to read product on line " + i +
                                    "\nReason: Product code already in use: " + code +
                                    " with product name:" +
                                    ArrayHelper.Find(products, x -> {return x.code.equals(code); }).get(0).name);
            }

            var description = items[3].trim();

            // if valid add product to products
            products.add(new Product(price, name, code, description));

            i++;
        }


        return products;
    }

    public static ArrayList<Customer> readCustomersFromFile(String filename) throws Exception {
        Scanner scanner = FileHelper.getFileScanner(filename);
        var customers = new ArrayList<Customer>();

        int i = 1;
        while (scanner.hasNext()){
            var line = scanner.nextLine();
            var items = line.split(",");

            if(items.length != 5){
                throw new Exception("Unable to read customer on line " + i + "\nReason: Invalid comma count on line.");
            }

            var customerID = 0;

            //validate customerID
            {
                try {
                    customerID = Integer.parseInt(items[0]);
                } catch (NumberFormatException e) {
                    throw new Exception("Unable to read customer on line " + i + "\nReason: invalid customerID: " + items[0]);
                }
                int finalCustomerID = customerID;
                if (!ArrayHelper.Find(customers, x -> x.customerID == finalCustomerID).isEmpty()) {
                    throw new Exception("Unable to read customer on line " + i + "\nReason: duplicate customerID: " + items[0]);
                }
            }

            var date = new Date();
            // validate date: if it fails it sets the date to today
            {
                try{
                    date = new Date(Date.parse(items[3].trim()));
                }catch (IllegalArgumentException e){
                    date = new Date();
                }
            }

            var priceOfLastTransaction = 0.0;
            //validate priceOfLastTransaction
            {
                try {
                    priceOfLastTransaction = Double.parseDouble(items[4]);
                } catch (NumberFormatException e) {
                    throw new Exception("Unable to read customer on line " + i + "\nReason: invalid priceOfLastTransaction: " + items[4]);
                }
            }

            customers.add(new Customer(customerID, items[1].trim(), items[2].trim(), date, priceOfLastTransaction));
            i++;
        }

        return customers;
    }

    public static Cart readInvoiceFromFile(String filename, ArrayList<Product> products) {
        return new Cart(new Customer(0, "", "", new Date(), 0));
    }

}
