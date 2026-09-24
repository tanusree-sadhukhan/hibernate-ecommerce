package com.ecommerce.test;

import com.ecommerce.model.Role;
import com.ecommerce.model.Users;
import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

public class TestUserCRUD {

    public static void main(String[] args) {

        Long userId;

        // ==========================================
        // CREATE USER
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Users user = new Users();

            user.setUsername("john_customer");

            // Hash password using BCrypt
            String hashedPassword = BCrypt.hashpw(
                    "password123",
                    BCrypt.gensalt()
            );

            user.setPassword(hashedPassword);
            user.setEmail("john@example.com");
            user.setRole(Role.CUSTOMER);

            session.persist(user);

            transaction.commit();

            userId = user.getId();

            System.out.println("CREATE: User created successfully!");
            System.out.println("User ID: " + userId);
            System.out.println("Username: " + user.getUsername());
            System.out.println("Role: " + user.getRole());
            System.out.println("Password stored as BCrypt hash.");
        }

        // ==========================================
        // READ USER
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Users user = session.find(Users.class, userId);

            System.out.println();
            System.out.println("READ: User fetched successfully!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Role: " + user.getRole());
            System.out.println("Password Hash: " + user.getPassword());
        }

        // ==========================================
        // UPDATE USER
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Users user = session.find(Users.class, userId);

            user.setEmail("john.updated@example.com");
            user.setRole(Role.ADMIN);

            transaction.commit();

            System.out.println();
            System.out.println("UPDATE: User updated successfully!");
        }

        // ==========================================
        // DELETE USER
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Users user = session.find(Users.class, userId);

            session.remove(user);

            transaction.commit();

            System.out.println("DELETE: User deleted successfully!");
        }

        // ==========================================
        // FINISH
        // ==========================================

        HibernateUtil.shutdown();

        System.out.println();
        System.out.println("User CRUD testing completed successfully!");
    }
}