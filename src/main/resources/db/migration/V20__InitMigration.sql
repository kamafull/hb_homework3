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
    )