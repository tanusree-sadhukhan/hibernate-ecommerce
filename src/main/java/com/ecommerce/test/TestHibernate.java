package com.ecommerce.test;

import com.ecommerce.model.Category;
import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestHibernate {

    public static void main(String[] args) {

        // CREATE
        Long categoryId;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = new Category();
            category.setName("Electronics");
            category.setDescription("Electronic products");

            session.persist(category);

            transaction.commit();

            categoryId = category.getId();

            System.out.println("CREATE: Category created successfully!");
            System.out.println("Category ID: " + categoryId);
        }

        // READ
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Category category = session.find(Category.class, categoryId);

            System.out.println("READ: Category fetched successfully!");
            System.out.println("Name: " + category.getName());
            System.out.println("Description: " + category.getDescription());
        }

        // UPDATE
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = session.find(Category.class, categoryId);

            category.setDescription("Electronic and smart technology products");

            transaction.commit();

            System.out.println("UPDATE: Category updated successfully!");
        }

        // DELETE
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Category category = session.find(Category.class, categoryId);

            session.remove(category);

            transaction.commit();

            System.out.println("DELETE: Category deleted successfully!");
        }

        HibernateUtil.shutdown();

        System.out.println("Category CRUD testing completed successfully!");
    }
}
