CREATE TABLE department
(
    id     SERIAL NOT NULL CONSTRAINT department_pkey PRIMARY KEY,
    name   VARCHAR(80) NOT NULL CONSTRAINT idx_department_name UNIQUE,
    closed BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE person
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
);