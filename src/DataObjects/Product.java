package DataObjects;

public class Product {
    public double price;
    public String name;
    public String code;
    public String description;

    public Product(double price, String name, String code, String description) throws Exception {
        if (price < 0)
            throw new Exception("price can not be negative");
        this.price = price;
        this.name = name;
        this.code = code;
        this.description = description;
    }
}
