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
import java.util.ArrayList;
import java.util.List;

public class TestOrderCRUD {

    public static void main(String[] args) {

        Long userId;
        Long categoryId;
        Long product1Id;
        Long product2Id;
        Long orderId;

        // =====================================================
        // 1. CREATE USER
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Users user = new Users();

            user.setUsername("order_customer");
            user.setPassword("hashed-password-for-test");
            user.setEmail("order@example.com");
            user.setRole(Role.CUSTOMER);

            session.persist(user);

            transaction.commit();

            userId = user.getId();

            System.out.println("User created for Order test.");
        }

        // =====================================================
        // 2. CREATE CATEGORY
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = new Category();

            category.setName("Order Test Category");
            category.setDescription("Category for order testing");

            session.persist(category);

            transaction.commit();

            categoryId = category.getId();

            System.out.println("Category created for Order test.");
        }

        // =====================================================
        // 3. CREATE PRODUCT 1
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = session.find(Category.class, categoryId);

            Product product = new Product();

            product.setName("Laptop");
            product.setPrice(new BigDecimal("75000.00"));
            product.setStockQuantity(10);
            product.setCategory(category);

            session.persist(product);

            transaction.commit();

            product1Id = product.getId();

            System.out.println("Product 1 created.");
        }

        // =====================================================
        // 4. CREATE PRODUCT 2
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = session.find(Category.class, categoryId);

            Product product = new Product();

            product.setName("Mouse");
            product.setPrice(new BigDecimal("1000.00"));
            product.setStockQuantity(20);
            product.setCategory(category);

            session.persist(product);

            transaction.commit();

            product2Id = product.getId();

            System.out.println("Product 2 created.");
        }

        // =====================================================
        // 5. CREATE ORDER WITH MULTIPLE ORDER DETAILS
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Users user = session.find(Users.class, userId);

            Product product1 = session.find(Product.class, product1Id);
            Product product2 = session.find(Product.class, product2Id);

            // Create Order
            Orders order = new Orders();

            order.setOrderDate(LocalDateTime.now());

            // Laptop: 1 × 75000
            // Mouse: 2 × 1000
            BigDecimal totalAmount =
                    new BigDecimal("75000.00")
                            .add(new BigDecimal("2000.00"));

            order.setTotalAmount(totalAmount);
            order.setUser(user);

            session.persist(order);

            // Order Detail 1
            OrderDetails detail1 = new OrderDetails();

            detail1.setQuantity(1);
            detail1.setUnitPrice(new BigDecimal("75000.00"));
            detail1.setOrder(order);
            detail1.setProduct(product1);

            // Order Detail 2
            OrderDetails detail2 = new OrderDetails();

            detail2.setQuantity(2);
            detail2.setUnitPrice(new BigDecimal("1000.00"));
            detail2.setOrder(order);
            detail2.setProduct(product2);

            session.persist(detail1);
            session.persist(detail2);

            transaction.commit();

            orderId = order.getId();

            System.out.println("CREATE: Order created successfully!");
            System.out.println("Order ID: " + orderId);
            System.out.println("Total Amount: " + order.getTotalAmount());
            System.out.println("Order contains 2 OrderDetails.");
        }

        // =====================================================
        // 6. READ ORDER + USER + PRODUCTS
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Orders order = session.find(Orders.class, orderId);

            System.out.println();
            System.out.println("READ: Order fetched successfully!");
            System.out.println("Order ID: " + order.getId());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Total Amount: " + order.getTotalAmount());

            System.out.println("Customer: "
                    + order.getUser().getUsername());

            List<OrderDetails> details = order.getOrderDetails();

            System.out.println("Number of OrderDetails: "
                    + details.size());

            for (OrderDetails detail : details) {

                System.out.println(
                        "Product: "
                                + detail.getProduct().getName()
                                + " | Quantity: "
                                + detail.getQuantity()
                                + " | Unit Price: "
                                + detail.getUnitPrice()
                );
            }
        }

        // =====================================================
        // 7. UPDATE ORDER
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Orders order = session.find(Orders.class, orderId);

            order.setTotalAmount(new BigDecimal("77000.00"));

            transaction.commit();

            System.out.println();
            System.out.println("UPDATE: Order updated successfully!");
        }

        // =====================================================
        // 8. DELETE ORDER
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Orders order = session.find(Orders.class, orderId);

            session.remove(order);

            transaction.commit();

            System.out.println("DELETE: Order deleted successfully!");
        }

        // =====================================================
        // 9. DELETE PRODUCTS
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Product product1 = session.find(Product.class, product1Id);
            Product product2 = session.find(Product.class, product2Id);

            session.remove(product1);
            session.remove(product2);

            transaction.commit();

            System.out.println("Test products deleted.");
        }

        // =====================================================
        // 10. DELETE CATEGORY
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = session.find(Category.class, categoryId);

            session.remove(category);

            transaction.commit();

            System.out.println("Test category deleted.");
        }

        // =====================================================
        // 11. DELETE USER
        // =====================================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Users user = session.find(Users.class, userId);

            session.remove(user);

            transaction.commit();

            System.out.println("Test user deleted.");
        }

        // =====================================================
        // FINISH
        // =====================================================

        HibernateUtil.shutdown();

        System.out.println();
        System.out.println("Order and OrderDetails testing completed successfully!");
    }
}

