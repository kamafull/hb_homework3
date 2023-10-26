-- create sequence hibernate_sequence start with 1 increment by 1;
CREATE TABLE IF NOT EXISTS department
(
    id    SERIAL NOT NULL CONSTRAINT department_pkey PRIMARY KEY,
    name   VARCHAR(80) NOT NULL CONSTRAINT idx_department_name UNIQUE,
    closed BOOLEAN DEFAULT FALSE NOT NULL
)