create schema management;
create schema gameplay;
create schema statistics;

create sequence management.identity_employee_sequence;

create table management.employee
(
  id               uuid not null primary key,
  login            text,
  password         text
);

-- password 'admin'
insert into management.employee(id, login, password)
VALUES ('2ff7ed66-62d9-4daf-a3a4-d94ebad8b602', 'admin', '$2a$10$eU4BZV4YHB9bL8tva0ffZ.W1ZL8c5E2wJ1gTfjSlR0mlj/u8rJX4C');
