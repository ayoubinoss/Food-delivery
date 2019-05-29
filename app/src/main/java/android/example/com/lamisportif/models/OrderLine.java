package android.example.com.lamisportif.models;

public class OrderLine {

    private String designation;
    private int quantity;
    private double price;
    private double total;
    private String fields;

    public OrderLine() {
    }

    public OrderLine(String designation, int quantity, double price, double total) {
        this.designation = designation;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public OrderLine(String designation, int quantity, double price) {
        this.designation = designation;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
