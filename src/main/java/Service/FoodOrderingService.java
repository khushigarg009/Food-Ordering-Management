package Service;

import Entity.*;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FoodOrderingService {
    private final Scanner scanner;
    private final Map<String, SelectedDish> selectedDishes = new HashMap<>(); // Map to store selected dishes

    public FoodOrderingService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void selectDishes() {
        boolean done = false;

        while (!done) {
            System.out.println("Select Cuisine:");
            System.out.println("1. Indian");
            System.out.println("2. Italian");
            System.out.println("3. Korean");
            System.out.print("Choose an option (or 0 to exit): ");
            int cuisineChoice = scanner.nextInt();

            if (cuisineChoice == 0) {
                done = true;
                break;
            }

            switch (cuisineChoice) {
                case 1:
                    selectCuisine(Indian.class);
                    break;
                case 2:
                    selectCuisine(Italian.class);
                    break;
                case 3:
                    selectCuisine(Korean.class);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private <T> void selectCuisine(Class<T> cuisineClass) {
        boolean done = false;

        // Fetch and display all dishes based on the selected cuisine class
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<T> dishes = session.createQuery("FROM " + cuisineClass.getSimpleName(), cuisineClass).list();


                System.out.println("+-------+----------------------+------------+");
                System.out.println("| SNO   | Dish Name           | Price      |");
                System.out.println("+-------+----------------------+------------+");

                // Display each dish
                for (T dish : dishes) {
                    String dishName = getDishName(dish);
                    double price = getDishPrice(dish);
                    int sNo = getDishSNo(dish);
                    System.out.printf("| %-5d | %-20s | $%-10.2f |\n", sNo, dishName, price);
                }
                System.out.println("+-------+----------------------+------------+");
            while (!done) {
                // User input for selection
                System.out.print("Enter the SNO of the dish you want to add (or 0 to finish): ");
                int dishSNo = scanner.nextInt();

                if (dishSNo == 0) { // Exit loop if user enters 0
                    done = true;
                    continue;
                }

                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();

                // Add the selected dish to the map
                T selectedDish = session.get(cuisineClass, dishSNo);
                if (selectedDish != null) {
                    String dishName = getDishName(selectedDish);
                    double price = getDishPrice(selectedDish);

                    // Check if the dish is already in the map and update quantity
                    if (selectedDishes.containsKey(dishName)) {
                        SelectedDish existingDish = selectedDishes.get(dishName);
                        existingDish.setQuantity(existingDish.getQuantity() + quantity); // Update quantity
                    } else {
                        // Add new dish to the map
                        SelectedDish newSelection = new SelectedDish(dishName, price, quantity);
                        selectedDishes.put(dishName, newSelection);
                    }
                    System.out.println();
                    System.out.println("Dish added! You can add more or enter 0 to finish.");
                    System.out.println();
                } else {
                    System.out.println("Dish not found.");
                }
            }
        }
    }


    private String getDishName(Object dish) {
        if (dish instanceof Indian) {
            return ((Indian) dish).getdName();
        } else if (dish instanceof Italian) {
            return ((Italian) dish).getdName();
        } else if (dish instanceof Korean) {
            return ((Korean) dish).getdName();
        }
        return "";
    }

    private double getDishPrice(Object dish) {
        if (dish instanceof Indian) {
            return ((Indian) dish).getPrice();
        } else if (dish instanceof Italian) {
            return ((Italian) dish).getPrice();
        } else if (dish instanceof Korean) {
            return ((Korean) dish).getPrice();
        }
        return 0.0;
    }
    private int getDishSNo(Object dish) {
        if (dish instanceof Indian) {
            return ((Indian) dish).getsNo();
        } else if (dish instanceof Italian) {
            return ((Italian) dish).getsNo();
        } else if (dish instanceof Korean) {
            return ((Korean) dish).getsNo();
        }
        return 0; // Default if no SNO found
    }


    public void displaySelectedDishes() {
        System.out.println("\n------------------YOUR SELECTED DISHES------------------");
        System.out.println("+----------------------+------------+------------+");
        System.out.println("| Dish Name           | Price      | Quantity   |");
        System.out.println("+----------------------+------------+------------+");

        double total = 0.0;

        for (SelectedDish dish : selectedDishes.values()) {
            System.out.printf("| %-20s | $%-10.2f | %-10d |\n", dish.getName(), dish.getPrice(), dish.getQuantity());
            total += dish.getPrice() * dish.getQuantity();
        }

        System.out.println("+----------------------+------------+------------+");
        System.out.printf("Total: $%.2f\n", total);
    }

    public void clearCart() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM AddedItems").executeUpdate();
            session.createNativeQuery("ALTER TABLE AddedItems AUTO_INCREMENT = 1").executeUpdate();
            transaction.commit();
            System.out.println("Cart cleared.");
        } catch (Exception e) {
            System.out.println("Error clearing cart: " + e.getMessage());
        }
    }
}