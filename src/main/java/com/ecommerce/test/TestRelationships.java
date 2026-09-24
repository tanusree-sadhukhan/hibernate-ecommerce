package com.ecommerce.test;

import com.ecommerce.model.Category;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.Users;
import com.ecommerce.util.HibernateUtil;

import org.hibernate.Session;

import java.util.List;

public class TestRelationships {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // ==========================================
            // 1. FETCH ALL ORDERS
            // ==========================================

            List<Orders> orders = session
                    .createQuery("from Orders", Orders.class)
                    .getResultList();

            System.out.println("Total Orders: " + orders.size());

            for (Orders order : orders) {

                System.out.println();
                System.out.println("Order ID: " + order.getId());

                // ==========================================
                // 2. ORDER -> USER
                // ==========================================

                Users user = order.getUser();

                System.out.println(
                        "Customer: " + user.getUsername()
                );

                // ==========================================
                // 3. ORDER -> ORDER DETAILS
                // ==========================================

                List<OrderDetails> details =
                        order.getOrderDetails();

                System.out.println(
                        "OrderDetails Count: " + details.size()
                );

                for (OrderDetails detail : details) {

                    // ======================================
                    // 4. ORDER DETAILS -> PRODUCT
                    // ======================================

                    Product product = detail.getProduct();

                    System.out.println(
                            "Product: " + product.getName()
                    );

                    System.out.println(
                            "Quantity: " + detail.getQuantity()
                    );

                    System.out.println(
                            "Unit Price: " + detail.getUnitPrice()
                    );

                    // ======================================
                    // 5. PRODUCT -> CATEGORY
                    // ======================================

                    Category category = product.getCategory();

                    System.out.println(
                            "Category: " + category.getName()
                    );
                }
            }

            System.out.println();
            System.out.println(
                    "All entity relationships tested successfully!"
            );
        }

        HibernateUtil.shutdown();
    }
}
