public class Product {
    private String pID;
    private String name;
    private double price;
    private int quantity;
    private String description;

    public Product(String pID, String name, double price, int quantity, String description) {
        this.pID = pID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public String getpID() {return pID;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getQuantity() {return quantity;}
    public String getDescription() {return description;}

    public String toString() {
        return String.format("%-10s %-20s %-15.3f %-15d %-30s", pID, name, price, quantity, description);
    }
}
