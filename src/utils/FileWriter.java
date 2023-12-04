package utils;

import DataObjects.Cart;
import DataObjects.Customer;
import DataObjects.Product;

import java.util.ArrayList;

public class FileWriter {
    public static void writeProductsToFile(ArrayList<Product> products, String filename) throws Exception {
        var writer = FileHelper.getFileWriter(filename);

        for(var item : products){
            writer.write(item.price + ","+item.name +"," + item.code + "," + item.description + "\n");
        }

        writer.close();
    }

    public static void writeCustomersToFile(ArrayList<Customer> customers, String filename) throws Exception {
        var writer = FileHelper.getFileWriter(filename);

        for(var item : customers){
            writer.append(item.customerID + ","+item.firstName +"," + item.lastName + "," + item.lastTransactionDate + "," + item.priceOfLastTransaction + "\n");
        }

        writer.close();
    }

    public static void writeTransactionToFile(Cart cart, String filename) throws Exception {
        var writer = FileHelper.getFileWriter(filename);

        writer.append(cart.customer.customerID + "\n");

        var productCounts = cart.getProductCounts();

        var keys = productCounts.keys();
        while(keys.hasMoreElements()){
            var product = keys.nextElement();
            writer.append(product.code + "," + productCounts.get(product) + "\n");
        }

        writer.close();
    }
}
