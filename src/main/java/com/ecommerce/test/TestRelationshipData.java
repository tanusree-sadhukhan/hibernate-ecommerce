package com.ecommerce.test;

import com.ecommerce.model.Category;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.Role;
import com.ecommerce.model.Users;
import com.ecommerce.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestRelationshipData {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            // ==========================================
            // 1. CREATE CATEGORY
            // ==========================================

            Category category = new Category();

            category.setName("Relationship Electronics");
            category.setDescription(
                    "Category for relationship testing"
            );

            session.persist(category);

            // ==========================================
            // 2. CREATE PRODUCTS
            // ==========================================

            Product laptop = new Product();

            laptop.setName("Relationship Laptop");
            laptop.setPrice(new BigDecimal("75000.00"));
            laptop.setStockQuantity(10);
            laptop.setCategory(category);

            session.persist(laptop);


            Product mouse = new Product();

            mouse.setName("Relationship Mouse");
            mouse.setPrice(new BigDecimal("1000.00"));
            mouse.setStockQuantity(20);
            mouse.setCategory(category);

            session.persist(mouse);

            // ==========================================
            // 3. CREATE USER
            // ==========================================

            Users user = new Users();

            user.setUsername("relationship_customer");
            user.setPassword("test-password");
            user.setEmail("relationship@example.com");
            user.setRole(Role.CUSTOMER);

            session.persist(user);

            // ==========================================
            // 4. CREATE ORDER
            // ==========================================

            Orders order = new Orders();

            order.setOrderDate(LocalDateTime.now());
            order.setTotalAmount(new BigDecimal("77000.00"));
            order.setUser(user);

            session.persist(order);

            // ==========================================
            // 5. CREATE ORDER DETAIL 1
            // ==========================================

            OrderDetails detail1 = new OrderDetails();

            detail1.setQuantity(1);
            detail1.setUnitPrice(new BigDecimal("75000.00"));
            detail1.setOrder(order);
            detail1.setProduct(laptop);

            session.persist(detail1);

            // ==========================================
            // 6. CREATE ORDER DETAIL 2
            // ==========================================

            OrderDetails detail2 = new OrderDetails();

            detail2.setQuantity(2);
            detail2.setUnitPrice(new BigDecimal("1000.00"));
            detail2.setOrder(order);
            detail2.setProduct(mouse);

            session.persist(detail2);

            // ==========================================
            // COMMIT
            // ==========================================

            transaction.commit();

            System.out.println();
            System.out.println(
                    "Relationship test data created successfully!"
            );

            System.out.println("Category: "
                    + category.getName());

            System.out.println("Products: "
                    + laptop.getName()
                    + ", "
                    + mouse.getName());

            System.out.println("User: "
                    + user.getUsername());

            System.out.println("Order ID: "
                    + order.getId());

            System.out.println("OrderDetails: 2");
        }

        HibernateUtil.shutdown();
    }
}
