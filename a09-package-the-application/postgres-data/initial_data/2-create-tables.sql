CREATE TABLE course_system.users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE course_system.roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE course_system.users_roles
(
    user_id  INT NOT NULL,
    roles_id INT NOT NULL,
    PRIMARY KEY (user_id, roles_id),
    FOREIGN KEY (user_id) REFERENCES course_system.users (id) ON DELETE CASCADE,
    FOREIGN KEY (roles_id) REFERENCES course_system.roles (id) ON DELETE CASCADE
);

