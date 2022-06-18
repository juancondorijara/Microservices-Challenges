DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  product_id INT(250) NOT NULL,
  price float NOT NULL
);

INSERT INTO products (product_id, price) VALUES
  (1, 100.00),
  (2, 200.00),
  (3, 300.00),
  (4, 400.00),
  (5, 500.00),
  (6, 600.00),
  (7, 700.00),
  (8, 800.00),
  (9, 900.00),
  (10, 1000.00);
