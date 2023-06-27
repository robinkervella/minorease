DROP TABLE IF EXISTS `authorities` ;
DROP TABLE IF EXISTS `users` ;
CREATE TABLE users
(
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (username)
);
CREATE TABLE authorities
(
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);