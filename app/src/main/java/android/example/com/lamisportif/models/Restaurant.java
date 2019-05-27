package android.example.com.lamisportif.models;

public class Restaurant {
    private String name;
    private String image;
    private double deliveryPrice;
    private String deliveryTime;
    private String restaurantID;


    public Restaurant() {
    }

    public Restaurant(String name, String image, double deliveryPrice, String deliveryTime, String restaurantID) {
        this.name = name;
        this.image = image;
        this.deliveryPrice = deliveryPrice;
        this.deliveryTime = deliveryTime;
        this.restaurantID = restaurantID;
    }

    public Restaurant(String name, String image, double deliveryPrice, String deliveryTime) {
        this.name = name;
        this.image = image;
        this.deliveryPrice = deliveryPrice;
        this.deliveryTime = deliveryTime;
    }

    public Restaurant(String name, double deliveryPrice, String deliveryTime) {
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.deliveryTime = deliveryTime;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", restaurantID='" + restaurantID + '\'' +
                '}';
    }
}
