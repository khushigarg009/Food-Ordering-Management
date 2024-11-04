package Main;

import Service.*;
import Util.HibernateUtil;

import java.util.Scanner;

public class FoodOrderingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        FoodOrderingService foodService = new FoodOrderingService(scanner); // Initialize FoodOrderingService

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userService.register();
                    break;
                case 2:
                    boolean loggedIn = userService.login();
                    if (loggedIn) {
                        System.out.println("Welcome to the system!");
                        showMainMenu(foodService, scanner); // Call main menu after successful login
                    }
                    break;
                case 3:
                    System.out.println("Exiting the application.");
                    scanner.close();
                    HibernateUtil.shutdown();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Main menu
    private static void showMainMenu(FoodOrderingService food, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Select Cuisine");
            System.out.println("2. View Cart and Bill");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    food.selectDishes();
                    break;
                case 2:
                    food.displaySelectedDishes();
                    break;
                case 3:
                    food.clearCart();
                    System.out.println("THANK YOU FOR ORDERING!!! ");
                    System.out.println("DO VISIT US AGAIN  :-) ");
                    System.out.println("Exiting the system...");
                    HibernateUtil.shutdown();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
