package android.example.com.lamisportif.models;

public class Meal {
    private String image;
    private String price;
    private String description;
    private String designation;
    private String category;


    public Meal() {
    }

    public Meal(String image, String price, String description, String designation, String category) {
        this.image = image;
        this.price = price;
        this.description = description;
        this.designation = designation;
        this.category = category;
    }

    public Meal(String price, String description, String designation) {
        this.price = price;
        this.description = description;
        this.designation = designation;
    }

    public Meal(String price, String description, String designation, String category) {
        this.price = price;
        this.description = description;
        this.designation = designation;
        this.category = category;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "image='" + image + '\'' +
                ", price='" + price + '\'' +
                ", name='" + description + '\'' +
                '}';
    }
}
