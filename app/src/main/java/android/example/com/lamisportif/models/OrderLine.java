package android.example.com.lamisportif.models;

import java.util.Map;

public class OrderLine {

    private String designation;
    private int quantity;
    private double price;
    private double total;
    private Map<String,String> mapAnswer;

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

    public Map<String, String> getMapAnswer() {
        return mapAnswer;
    }

    public void setMapAnswer(Map<String, String> mapAnswer) {
        this.mapAnswer = mapAnswer;
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

    @Override
    public String toString() {
        return "OrderLine{" +
                "designation='" + designation + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                ", mapAnswer=" + mapAnswer +
                '}';
    }
}
