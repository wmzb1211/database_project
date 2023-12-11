CREATE DATABASE cardb;

CREATE USER 'carmaster'@'localhost' IDENTIFIED BY 'carmasterpasswd';

GRANT ALL PRIVILEGES ON cardb.* TO 'carmaster'@'localhost';

FLUSH PRIVILEGES;
