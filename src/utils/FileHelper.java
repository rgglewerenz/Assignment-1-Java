package utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileWriter;

class FileHelper
{
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
            if(!file.exists()){
                throw new Exception("unable to find file");
            }
            return new FileWriter(file, false);
        }
    }
}
