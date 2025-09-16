-- ===================================
-- Initialize ShopKart database
-- ===================================
DROP DATABASE IF EXISTS shopkart_db;
CREATE DATABASE shopkart_db;
USE shopkart_db;

-- ===================================
-- Table: admin
-- ===================================
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    security_key VARCHAR(255)
);

-- Insert default admin
-- Assume SHA-256 hash of 'admin123'
INSERT INTO admin (username, email, password, security_key)
VALUES (
  'admin',
  'admin@gmail.com',
  '$2b$12$vn39zZ5EjCplSM0LBFKqgeql7WJegcwEIRI88yqJXNkr8vj.dRmpa',
  'admin@123'
);
-- ===================================
-- Table: customer
-- ===================================
CREATE TABLE customer (
    cust_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    last_login_date DATETIME
);

-- Insert sample customers
INSERT INTO customer (name, email, password, last_login_date)
VALUES
('John Doe', 'john@example.com', 'john123', NOW()),
('Jane Smith', 'jane@example.com', 'jane123', NOW());

-- Insert Lalitha as a customer (with all fields specified)
INSERT INTO customer (cust_id, name, email, password, last_login_date, address, phone, profile_image, created_at)
VALUES (4, 'Lalitha', 'lalithaveni16@gmail.com', '$2a$10$cdUyRSTIWXCnFTaKWCaAkOUJK/IPmEvtNr8j6DWzeXTRRwCJQiWOq', '2025-09-16 20:24:35', '101,sri,gunadala,vij', '8247429297', NULL, '2025-09-16 20:24:19');

-- ===================================
-- Table: product
-- ===================================
CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    brand VARCHAR(50),
    price DECIMAL(10,2),
    image VARCHAR(255),
    stock INT,
    discount DECIMAL(5,2)
);

-- Insert sample products
INSERT INTO product (name, category, brand, price, image, stock, discount)
VALUES
('iPhone 14', 'Mobiles', 'Apple', 999.99, 'iphone14.jpg', 50, 5),
('Galaxy S23', 'Mobiles', 'Samsung', 899.99, 'galaxyS23.jpg', 40, 10),
('MacBook Air', 'Laptops', 'Apple', 1299.99, 'macbookair.jpg', 30, 7),
('Dell XPS 13', 'Laptops', 'Dell', 1199.99, 'dellxps13.jpg', 25, 8),
('Sony WH-1000XM5', 'Headphones', 'Sony', 349.99, 'sonywh1000xm5.jpg', 60, 15);

-- ===================================
-- Table: orders
-- ===================================
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    cust_id INT NOT NULL,
    order_date DATETIME NOT NULL,
    total DECIMAL(10,2),
    status VARCHAR(50),
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id)
);

-- Insert sample orders
INSERT INTO orders (cust_id, order_date, total, status)
VALUES
(1, NOW(), 1999.98, 'Delivered'),
(2, NOW(), 899.99, 'Pending'),
(3, NOW(), 1649.98, 'Shipped'),
(1, NOW(), 349.99, 'Pending');
