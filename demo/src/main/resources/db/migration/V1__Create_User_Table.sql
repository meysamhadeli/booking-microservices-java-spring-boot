-- src/main/resources/db/migration/V1__Create_Student_Table.sql

CREATE TABLE student (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         age INTEGER NOT NULL,
                         birthday DATE,

    -- Adding an index on email since it's unique
                         CONSTRAINT uk_student_email UNIQUE (email)
);