package utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileWriter;

public class FileHelper
{

    public static final String PRODUCTS_PATH = "data/products.items";
    public static final String CUSTOMERS_PATH = "data/customers.items";


    static Scanner getFileScanner(String filename) throws Exception {
        File file;
        //Generates entire path if needed
        {
            var path = Paths.get(filename).toAbsolutePath();
            file = path.toFile();
        }
        //Check if file exists
        {
            if(!file.exists()){
                throw new Exception("unable to find file");
            }
            return new Scanner(file);
        }
    }

    static FileWriter getFileWriter(String filename) throws Exception {
        File file;
        //Generates entire path if needed
        {
            var path = Paths.get(filename).toAbsolutePath();
            file = path.toFile();
        }
        //Check if file exists
        {
            return new FileWriter(file, false);
        }
    }

    public static String getFileExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();

        // Find the last dot in the file name
        int dotIndex = fileName.lastIndexOf('.');

        // Check if a dot is found and it is not the first or last character
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            // Extract the substring after the last dot
            return fileName.substring(dotIndex + 1);
        } else {
            // No valid extension found
            return "";
        }
    }
}
