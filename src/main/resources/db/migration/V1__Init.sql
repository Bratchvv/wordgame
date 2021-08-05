SET NAMES 'utf8';
SET CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS employee
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    login    VARCHAR(255) NOT NULL UNIQUE,
    enabled  boolean
);

CREATE TABLE IF NOT EXISTS role
(
    role_id   INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS employee_role
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    employee_id  INT,
    role_role_id INT,
    FOREIGN KEY (role_role_id)
        REFERENCES role (role_id)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    FOREIGN KEY (employee_id)
        REFERENCES employee (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

-- password 'admin'
INSERT employee(id, login, password, enabled)
VALUES (1, 'admin', '$2a$10$eU4BZV4YHB9bL8tva0ffZ.W1ZL8c5E2wJ1gTfjSlR0mlj/u8rJX4C', true);

INSERT role (role_id, role_name)
VALUES (1, 'ROLE_ADMIN');
INSERT role (role_id, role_name)
VALUES (2, 'ROLE_USER');

INSERT employee_role(id, employee_id, role_role_id)
VALUES (1, 1, 1);
