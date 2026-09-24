# Hibernate Ecommerce

A Java-based Ecommerce application developed using Hibernate ORM and MySQL.
The project demonstrates entity mapping, relationships, CRUD operations, and
database persistence using Hibernate.

## Technologies Used

- Java 17
- Hibernate ORM 6.5.2
- Jakarta Persistence API
- MySQL
- Maven
- BCrypt for password hashing
- Eclipse IDE

## Project Structure

```text
hibernate-ecommerce
│
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── ecommerce
│       │           ├── model
│       │           │   ├── Category.java
│       │           │   ├── Product.java
│       │           │   ├── Role.java
│       │           │   ├── Users.java
│       │           │   ├── Orders.java
│       │           │   └── OrderDetails.java
│       │           │
│       │           ├── test
│       │           │   ├── TestHibernate.java
│       │           │   ├── TestProductCRUD.java
│       │           │   ├── TestUserCRUD.java
│       │           │   ├── TestOrderCRUD.java
│       │           │   ├── TestRelationshipData.java
│       │           │   └── TestRelationships.java
│       │           │
│       │           └── util
│       │               └── HibernateUtil.java
│       │
│       └── resources
│           └── hibernate.cfg.xml
│
├── pom.xml
├── schema.sql
└── README.md
Entity Relationships

The project contains the following entities:

Category
Primary key: id
Auto-generated ID
Unique and non-null name
Description
One-to-Many relationship with Product
Product
Primary key: id
Auto-generated ID
Non-null product name
Decimal price
Stock quantity
Many-to-One relationship with Category
Users
Primary key: id
Auto-generated ID
Unique and non-null username
Unique and non-null email
Hashed password
Role: ADMIN or CUSTOMER
One-to-Many relationship with Orders
Orders
Primary key: id
Auto-generated ID
Order date
Total amount
Many-to-One relationship with Users
One-to-Many relationship with OrderDetails
OrderDetails
Primary key: id
Auto-generated ID
Quantity
Unit price
Many-to-One relationship with Orders
Many-to-One relationship with Product
Relationship Diagram
Category
   │
   │ One-to-Many
   ▼
Product
   │
   │ Many-to-One
   ▼
OrderDetails
   ▲
   │ Many-to-One
   │
Orders
   ▲
   │ Many-to-One
   │
Users
Database Configuration

The project uses MySQL database named:

ecommerce

Default JDBC URL:

jdbc:mysql://localhost:3306/ecommerce

Database configuration is stored in:

src/main/resources/hibernate.cfg.xml

Before running the project, make sure:

MySQL server is running.
The ecommerce database exists.
MySQL username and password are correctly configured in
hibernate.cfg.xml.

The database schema is also provided in:

schema.sql
Maven Dependencies

The project uses the following dependencies:

Hibernate Core
MySQL Connector/J
Jakarta Persistence API
BCrypt
SLF4J Simple

All dependencies are configured in:

pom.xml
How to Run
1. Clone the Repository
git clone <your-github-repository-url>
2. Open the Project in Eclipse

Import the project as an existing Maven project.

3. Configure MySQL

Create the database:

CREATE DATABASE ecommerce;

Update the MySQL username and password in:

src/main/resources/hibernate.cfg.xml
4. Update Maven Project

In Eclipse:

Right Click Project
→ Maven
→ Update Project
5. Run CRUD Tests

The project contains separate test classes for CRUD operations:

TestHibernate.java
TestProductCRUD.java
TestUserCRUD.java
TestOrderCRUD.java

These tests verify Create, Read, Update, and Delete operations.

6. Run Relationship Tests

First run:

TestRelationshipData.java

Then run:

TestRelationships.java

This verifies the relationships between:

Users → Orders
Orders → OrderDetails
OrderDetails → Product
Product → Category
CRUD Operations Tested

The project successfully tests:

Create
Read
Update
Delete

for the following entities:

Category
Product
Users
Orders
OrderDetails
Hibernate Configuration

Hibernate manages the database schema using:

hibernate.hbm2ddl.auto=update

SQL statements are displayed during testing using:

hibernate.show_sql=true

The project uses HibernateUtil.java to create and manage the Hibernate
SessionFactory.

Relationship Testing

The project verifies the following entity relationships:

Users → Orders

One user can have multiple orders.

Orders → Users

Each order belongs to one user.

Orders → OrderDetails

One order can contain multiple order details.

OrderDetails → Product

Each order detail refers to one product.

Product → Category

Each product belongs to one category.

Testing Results

The Hibernate CRUD and relationship tests were successfully executed
using MySQL.

Successful test results include:

Category CRUD testing completed successfully!

Product CRUD testing completed successfully!

User CRUD testing completed successfully!

Order and OrderDetails testing completed successfully!

All entity relationships tested successfully!
Cascade and Fetching

The project uses Hibernate/JPA cascade and fetching strategies for
entity relationships.

Orders use cascading for their OrderDetails, allowing associated
OrderDetails to be removed when an Order is deleted.

The Users → Orders and Orders → OrderDetails collections use lazy
fetching.

Security

User passwords are intended to be stored using BCrypt hashing rather
than plain-text passwords.

For security reasons, database credentials should not be committed to
a public GitHub repository.

Conclusion

This project demonstrates the implementation of an Ecommerce data model
using Hibernate ORM and MySQL.

It covers:

Entity creation
Primary key generation
JPA/Hibernate annotations
One-to-Many relationships
Many-to-One relationships
CRUD operations
Cascade operations
Lazy fetching
MySQL database persistence
Hibernate configuration
Relationship testing