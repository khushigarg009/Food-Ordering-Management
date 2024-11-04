package Entity;

public class SelectedDish {
    private String name;
    private double price;
    private int quantity;

    public SelectedDish(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {   // Ensure this method exists
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
