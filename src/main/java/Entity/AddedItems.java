package Entity;

import javax.persistence.*;

@Entity
@Table(name = "AddedItems")
public class AddedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "dishName")
    private String dishName;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    public AddedItems() {
    }

    public AddedItems(String dishName, int price, int quantity) {
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-10d | %-10d |", id, dishName, price, quantity);
    }

}
