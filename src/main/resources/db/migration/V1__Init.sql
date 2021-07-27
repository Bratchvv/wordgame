create schema management;
create schema gameplay;
create schema statistics;

create sequence management.hibernate_sequence;
create sequence gameplay.hibernate_sequence;
create sequence statistics.hibernate_sequence;

CREATE TABLE management.employee
(
    id serial NOT NULL,
    enabled boolean NOT NULL,
    password character varying(128) COLLATE pg_catalog."default" NOT NULL,
    login character varying(36) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT employee_pkey PRIMARY KEY (id),
    CONSTRAINT uk_hafqwjqe2e9bcpgyj6evm52ap UNIQUE (login)
);

CREATE TABLE management.role
(
    role_id serial NOT NULL,
    role_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (role_id),
    CONSTRAINT uk_iubw515ff0ugtm28p8g3myt0h UNIQUE (role_name)
);

CREATE TABLE management.employee_role
(
    id           serial NOT NULL,
    employee_id  bigint NOT NULL,
    role_role_id bigint NOT NULL,
    CONSTRAINT employee_role_pkey PRIMARY KEY (id),
    CONSTRAINT fkmtte6n0pky6bm79pibjd26tup FOREIGN KEY (role_role_id)
        REFERENCES management.role (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fko7rvk7ejtx29vru9cyhf7o68a FOREIGN KEY (employee_id)
        REFERENCES management.employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- password 'admin'
insert into management.employee(id, login, password, enabled)
VALUES (1, 'admin', '$2a$10$eU4BZV4YHB9bL8tva0ffZ.W1ZL8c5E2wJ1gTfjSlR0mlj/u8rJX4C', true);

INSERT INTO management.role( role_id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO management.role( role_id, role_name) VALUES (2, 'ROLE_USER');

INSERT INTO management.employee_role(id, employee_id, role_role_id) VALUES (1, 1, 1);