INSERT INTO course_system.users (id, username, password, enabled)
VALUES (1, 'regular', '{bcrypt}$2a$10$w7ghGQ48dBxkJMvBLCwX8e9h2TGuZPyOcWgf01QiFm0JDIdJarr42', TRUE),
       (2, 'regular-and-admin', '{bcrypt}$2a$10$w7ghGQ48dBxkJMvBLCwX8e9h2TGuZPyOcWgf01QiFm0JDIdJarr42', TRUE),
       (3, 'admin-only', '{bcrypt}$2a$10$w7ghGQ48dBxkJMvBLCwX8e9h2TGuZPyOcWgf01QiFm0JDIdJarr42', TRUE);

INSERT INTO course_system.roles (id, name)
VALUES (100, 'ROLE_USER'),
       (101, 'ROLE_ADMIN');

INSERT INTO course_system.users_roles (user_id, roles_id)
VALUES (1, 100), -- regular -> USER
       (2, 100), -- regular-and-admin -> USER
       (2, 101), -- regular-and-admin -> ADMIN
       (3, 101); -- admin-only -> ADMIN