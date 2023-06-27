INSERT INTO users (username,password,enabled) VALUES (
                                                         'admin', '{bcrypt}$2a$10$1VeFJ6PoPyrfI.pqxdaIJeFaZFpuueDsILcSbrJg8x8iHaKXOObES', 1
                                                     );
INSERT INTO authorities (username,authority) VALUES ('admin','ROLE_ADMIN');
