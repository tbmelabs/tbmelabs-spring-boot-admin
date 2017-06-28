CREATE DATABASE tbmelabstv;
USE tbmelabstv;

-- Please change the password in your productive environment!
CREATE USER 'tbmelabstv_flyway'@'localhost' IDENTIFIED BY 'flyway';
GRANT ALL ON * . * TO 'tbmelabstv_flyway'@'localhost';

-- Please change the password in your productive environment!
CREATE USER 'tbmelabstv_hibernate'@'localhost' IDENTIFIED BY 'hibernate';
GRANT SELECT, INSERT, UPDATE, DELETE ON * . * TO 'tbmelabstv_hibernate'@'localhost';