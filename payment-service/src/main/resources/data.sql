DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT(250) NOT NULL,
  balance float NOT NULL
);

INSERT INTO users (user_id, balance) VALUES
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