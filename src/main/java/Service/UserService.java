package Service;

import Entity.*;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Scanner;

public class UserService {
    Scanner scanner = new Scanner(System.in);

    public void register() {
        System.out.print("Enter a new username: ");
        String username = scanner.next();

        System.out.print("Enter a new password: ");
        String password = scanner.next();

        // Save the user in the database
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = new User(username, password);
            session.save(user);
            transaction.commit();
            System.out.println("Registration successful!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Registration failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Login method
    public boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        // Check if the user exists in the database
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            User user = session.get(User.class, username);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        } finally {
            session.close();
        }
    }
}
