package android.example.com.lamisportif.models;

public class Order {

    private String clientID;
    private String orderID;
    private String restaurantID;
    private String date;
    private String address;
    private int nbProducts;
    private String status;
    private String logoRestaurant;
    private double price_delivery;
    private double price_products;
    private double total;
    private String content;


    public static final String PENDING = "pending";
    public static final String ACCEPTED = "accepted";
    public static final String DISPATCHED = "dispatched";
    public static final String DELIVERED = "delivered";

    public Order(String clientID, String orderID, String date,
                 String address, int nbProducts, String status,
                 double price_delivery, double price_products, double total,
                 String logoRestaurant, String restaurantID,String content) {
        this.clientID = clientID;
        this.orderID = orderID;
        this.date = date;
        this.address = address;
        this.nbProducts = nbProducts;
        this.status = status;
        this.price_delivery = price_delivery;
        this.price_products = price_products;
        this.total = total;
        this.logoRestaurant = logoRestaurant;
        this.restaurantID = restaurantID;
        this.content = content;
    }

    public Order(String restaurantID, String status, String logoRestaurant,
                 double total, String content) {
        this.restaurantID = restaurantID;
        this.status = status;
        this.logoRestaurant = logoRestaurant;
        this.total = total;
        this.content = content;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNbProducts() {
        return nbProducts;
    }

    public void setNbProducts(int nbProducts) {
        this.nbProducts = nbProducts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice_delivery() {
        return price_delivery;
    }

    public void setPrice_delivery(double price_delivery) {
        this.price_delivery = price_delivery;
    }

    public double getPrice_products() {
        return price_products;
    }

    public void setPrice_products(double price_produts) {
        this.price_products = price_produts;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getLogoRestaurant() {
        return logoRestaurant;
    }

    public void setLogoRestaurant(String logoRestaurant) {
        this.logoRestaurant = logoRestaurant;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
