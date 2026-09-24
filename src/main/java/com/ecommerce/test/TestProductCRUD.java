package com.ecommerce.test;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class TestProductCRUD {

    public static void main(String[] args) {

        Long categoryId;
        Long productId;

        // ==========================================
        // CREATE CATEGORY FOR PRODUCT
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = new Category();
            category.setName("Electronics");
            category.setDescription("Electronic products");

            session.persist(category);

            transaction.commit();

            categoryId = category.getId();

            System.out.println("Category created for Product test.");
        }

        // ==========================================
        // CREATE PRODUCT
        // ==========================================

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

            productId = product.getId();

            System.out.println("CREATE: Product created successfully!");
            System.out.println("Product ID: " + productId);
        }

        // ==========================================
        // READ PRODUCT
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Product product = session.find(Product.class, productId);

            System.out.println("READ: Product fetched successfully!");
            System.out.println("Name: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Stock: " + product.getStockQuantity());
            System.out.println("Category: " + product.getCategory().getName());
        }

        // ==========================================
        // UPDATE PRODUCT
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Product product = session.find(Product.class, productId);

            product.setPrice(new BigDecimal("70000.00"));
            product.setStockQuantity(15);

            transaction.commit();

            System.out.println("UPDATE: Product updated successfully!");
        }

        // ==========================================
        // DELETE PRODUCT
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Product product = session.find(Product.class, productId);

            session.remove(product);

            transaction.commit();

            System.out.println("DELETE: Product deleted successfully!");
        }

        // ==========================================
        // DELETE TEST CATEGORY
        // ==========================================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = session.find(Category.class, categoryId);

            session.remove(category);

            transaction.commit();

            System.out.println("Test category deleted successfully!");
        }

        HibernateUtil.shutdown();

        System.out.println();
        System.out.println("Product CRUD testing completed successfully!");
    }
}
