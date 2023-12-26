-- create sequence hibernate_sequence start with 1 increment by 1;
CREATE TABLE IF NOT EXISTS department
(
    id    SERIAL NOT NULL CONSTRAINT department_pkey PRIMARY KEY,
    name   VARCHAR(80) NOT NULL CONSTRAINT idx_department_name UNIQUE,
    closed BOOLEAN DEFAULT FALSE NOT NULL
);
CREATE TABLE IF NOT EXISTS person
(
    id          SERIAL NOT NULL CONSTRAINT person_pkey PRIMARY KEY,
    first_name  VARCHAR(80) NOT NULL,
    last_name   VARCHAR(80) NOT NULL,
    middle_name VARCHAR(80),
    age         INTEGER,
    department_id INTEGER,
    CONSTRAINT fk_department
        FOREIGN KEY(department_id)
            REFERENCES department(id)
--     department_id INTEGER NOT NULL REFERENCES department(department_id)
);
CREATE TABLE IF NOT EXISTS department_person
(
    person_id SERIAL,
    department_id SERIAL
);