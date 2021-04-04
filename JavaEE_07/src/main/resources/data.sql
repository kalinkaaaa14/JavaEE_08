INSERT INTO books (id, isbn, title, author)
VALUES (1, '978-5-02-013850-9','Pride and Prejudice','Jane Austen'),
       (2, '978-1-23-456789-7','Da Vinci Code', 'Dan Braun'),
       (3, '978-9-66-923674-6','Harry Potter and the Deathly Hallows', 'J. K. Rowling');

INSERT INTO users (id,username, email, PASSWORD) VALUES

(1, 'kalinka', 'kalinkaaaa14@gmail.com', '$2a$10$Bjld4N.KJoWkiywfx0EKPurZkd6N6B1sxieIg5FLz5tXsSd8zE37q'),
(2, 'admin', 'admin@gmail.com', '$2a$10$oVnPpkSeKJ1U3JsPGtxd5OFuaIcnd4UZQnUNC5amj.UxULcPrUEGC');

INSERT INTO role(id, name) VALUES
(1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users_roles(users_id, roles_id) VALUES
(1, 1),
(2, 2);

