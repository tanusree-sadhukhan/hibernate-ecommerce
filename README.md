# Hibernate Ecommerce

A Java-based Ecommerce application developed using Hibernate ORM and MySQL.
The project demonstrates entity mapping, relationships, CRUD operations, and database persistence using Hibernate.

## Technologies Used

* Java 17
* Hibernate ORM 6.5.2
* Jakarta Persistence API
* MySQL
* Maven
* BCrypt for password hashing
* Eclipse IDE

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
```

## Entity Relationships

### Category

The Category entity represents product categories.

* Primary key: `id`
* ID is automatically generated
* `name` is unique and cannot be null
* Contains a description
* One Category can contain multiple Products

Relationship: **One-to-Many**

Category → Products

### Product

The Product entity represents products available in the ecommerce system.

* Primary key: `id`
* ID is automatically generated
* Product name cannot be null
* Price is stored using `BigDecimal`
* Contains stock quantity
* Each Product belongs to one Category

Relationship: **Many-to-One**

Product → Category

### Users

The Users entity represents customers and administrators.

* Primary key: `id`
* ID is automatically generated
* Username is unique and cannot be null
* Email is unique and cannot be null
* Password is stored using BCrypt hashing
* Role can be `ADMIN` or `CUSTOMER`
* One User can have multiple Orders

Relationship: **One-to-Many**

User → Orders

### Orders

The Orders entity represents customer orders.

* Primary key: `id`
* ID is automatically generated
* Contains order date
* Contains total amount
* Each Order belongs to one User
* One Order can contain multiple OrderDetails

Relationships:

* Many-to-One with Users
* One-to-Many with OrderDetails

### OrderDetails

The OrderDetails entity represents individual products included in an order.

* Primary key: `id`
* ID is automatically generated
* Contains quantity
* Contains unit price
* Each OrderDetail belongs to one Order
* Each OrderDetail refers to one Product

Relationships:

* Many-to-One with Orders
* Many-to-One with Products

## Relationship Overview

The main entity relationship flow is:

**Category → Product → OrderDetails → Orders → Users**

The relationships are implemented using JPA annotations such as `@OneToMany`, `@ManyToOne`, `@JoinColumn`, and `@Enumerated`.

## Database Configuration

The project uses MySQL as the relational database.

Database name:

`ecommerce`

JDBC URL:

`jdbc:mysql://localhost:3306/ecommerce`

Hibernate configuration is stored in:

`src/main/resources/hibernate.cfg.xml`

Hibernate automatically creates or updates the required database tables using the `hibernate.hbm2ddl.auto` property.

The project also contains `schema.sql` with the SQL definitions for the required database tables.

## Maven Dependencies

The project uses Maven for dependency management.

The main dependencies are:

* Hibernate Core
* MySQL Connector/J
* Jakarta Persistence API
* BCrypt
* SLF4J Simple

All dependencies are configured in `pom.xml`.

## Setup Instructions

### 1. Clone the Repository

Clone the project from GitHub:

```bash
git clone https://github.com/tanusree-sadhukhan/hibernate-ecommerce.git
```

Open the project in Eclipse or another Java IDE.

### 2. Configure MySQL

Make sure MySQL is installed and running on port `3306`.

Create the database:

```sql
CREATE DATABASE ecommerce;
```

### 3. Configure Database Credentials

Open:

`src/main/resources/hibernate.cfg.xml`

Update the MySQL username and password according to your local MySQL installation.

Do not commit real database passwords or other sensitive credentials to a public repository.

### 4. Update Maven Project

In Eclipse:

**Right Click Project → Maven → Update Project**

Maven will download the required dependencies specified in `pom.xml`.

### 5. Run the Tests

The test classes are located inside:

`src/main/java/com/ecommerce/test/`

Run each test class as a Java Application.

## CRUD Testing

The project contains separate test classes for CRUD operations.

### Category CRUD

`TestHibernate.java`

This test verifies:

* Create Category
* Read Category
* Update Category
* Delete Category

### Product CRUD

`TestProductCRUD.java`

This test verifies:

* Create Product
* Read Product
* Update Product
* Delete Product

The Product is also associated with a Category during the test.

### User CRUD

`TestUserCRUD.java`

This test verifies:

* Create User
* Read User
* Update User
* Delete User

It also verifies BCrypt password hashing.

### Order and OrderDetails CRUD

`TestOrderCRUD.java`

This test verifies:

* Create User
* Create Category
* Create Products
* Create Order
* Create multiple OrderDetails
* Read Order
* Read related User and Products
* Update Order
* Delete Order
* Cascade deletion of OrderDetails
* Cleanup of temporary test data

## Relationship Testing

Relationship testing is performed using two test classes:

* `TestRelationshipData.java`
* `TestRelationships.java`

`TestRelationshipData.java` creates persistent test data consisting of:

* One Category
* Two Products
* One User
* One Order
* Two OrderDetails

`TestRelationships.java` retrieves the stored data and verifies the entity relationships.

The test verifies that:

* An Order is connected to a User
* An Order contains multiple OrderDetails
* Each OrderDetail is connected to a Product
* Each Product is connected to a Category
* Product quantity is correctly persisted
* Product unit price is correctly persisted

## Hibernate Configuration

Hibernate session management is handled by:

`HibernateUtil.java`

The utility class creates and manages the Hibernate `SessionFactory`.

The project uses:

* Hibernate ORM
* MySQL database connection
* JPA entity annotations
* Automatic schema update
* SQL logging
* Entity mapping through `hibernate.cfg.xml`

## Cascade and Fetching

The project uses Hibernate/JPA cascade operations and lazy fetching where appropriate.

The User-to-Orders and Order-to-OrderDetails relationships use cascade and lazy fetching.

This allows related records to be managed efficiently while maintaining the entity relationships.

## Security

User passwords are not stored as plain text.

The project uses BCrypt to hash passwords before storing them in the database.

Example:

```java
String hashedPassword = BCrypt.hashpw(
    "password123",
    BCrypt.gensalt()
);
```

Only the generated BCrypt hash is stored in the database.

## Database Tables

Hibernate creates the following tables:

* `categories`
* `products`
* `users`
* `orders`
* `order_details`

Foreign-key relationships are maintained between the related tables.

## Testing Results

The following tests were successfully executed:

| Test                                | Result |
| ----------------------------------- | ------ |
| Category CRUD                       | Passed |
| Product CRUD                        | Passed |
| User CRUD                           | Passed |
| Order CRUD                          | Passed |
| OrderDetails CRUD                   | Passed |
| Category → Product relationship     | Passed |
| User → Orders relationship          | Passed |
| Order → OrderDetails relationship   | Passed |
| OrderDetails → Product relationship | Passed |
| Product → Category relationship     | Passed |
| BCrypt password hashing             | Passed |

The Hibernate `SessionFactory` was successfully created and the required database tables were generated successfully.

## Important Files

| File                 | Purpose                                |
| -------------------- | -------------------------------------- |
| `Category.java`      | Category entity                        |
| `Product.java`       | Product entity                         |
| `Users.java`         | User entity                            |
| `Orders.java`        | Order entity                           |
| `OrderDetails.java`  | Order details entity                   |
| `Role.java`          | User role enumeration                  |
| `HibernateUtil.java` | Hibernate SessionFactory configuration |
| `hibernate.cfg.xml`  | Hibernate and database configuration   |
| `schema.sql`         | Database schema                        |
| `pom.xml`            | Maven dependencies                     |
| `README.md`          | Project documentation                  |

## Conclusion

This project demonstrates a complete Hibernate-based Ecommerce persistence layer using Java, JPA annotations, MySQL, and Maven.

The project implements:

* Entity mapping
* Primary keys and automatically generated IDs
* One-to-Many relationships
* Many-to-One relationships
* CRUD operations
* Order and OrderDetails management
* Cascade operations
* Lazy fetching
* BCrypt password hashing
* MySQL database persistence
* Relationship testing

The project is structured for easy setup, testing, and further development.
