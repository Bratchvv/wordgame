create schema management;
create schema gameplay;
create schema statistics;

create sequence management.identity_employee_sequence;

create table management.employee
(
  id               uuid not null primary key,
  name             text,
  surname          text,
  patronymic       text,
  email            text,
  phone            text,
  login            text unique,
  position         text,
  first_login_date date,
  last_login_date  date,
  is_active        boolean,
  finish_date      date,
  ta_id            integer

);

comment on table management.employee IS 'Сотрудники';
comment on column management.employee.id IS 'Идентификатор';
comment on column management.employee.name IS 'Имя';
comment on column management.employee.surname IS 'Фамилия';
comment on column management.employee.patronymic IS 'Отчество';
comment on column management.employee.email IS 'Адрес электронной почты';
comment on column management.employee.phone IS 'Телефон';
comment on column management.employee.position IS 'Должность';
comment on column management.employee.ta_id IS 'Подразделение';


insert into management.employee(id, name, surname, patronymic, email, phone, login, position, ta_id)
VALUES ('2ff7ed66-62d9-4daf-a3a4-d94ebad8b602', 'Михаил', 'Меладзе', 'Рутович', 'user_30@mail.ru', '+7 965 699-78-64', 'user_30', 'Системный администратор', 415);
