package android.example.com.lamisportif.models;

public class Restaurant {
    private String name;
    private String image;
    private String deliveryPrice;
    private String deliveryTime;


    public Restaurant() {
    }

    public Restaurant(String name, String image, String deliveryPrice, String deliveryTime) {
        this.name = name;
        this.image = image;
        this.deliveryPrice = deliveryPrice;
        this.deliveryTime = deliveryTime;
    }

    public Restaurant(String name, String deliveryPrice, String deliveryTime) {
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.deliveryTime = deliveryTime;
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

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
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
                ", deliveryPrice='" + deliveryPrice + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                '}';
    }
}
