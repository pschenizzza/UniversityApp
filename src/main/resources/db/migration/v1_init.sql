CREATE TABLE student_groups (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                number VARCHAR(20) NOT NULL UNIQUE,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          full_name VARCHAR(255) NOT NULL,
                          admission_date DATE NOT NULL,
                          group_id BIGINT NOT NULL,
                          CONSTRAINT fk_students_group FOREIGN KEY (group_id) REFERENCES student_groups(id) ON DELETE CASCADE,
                          INDEX idx_students_group_name (group_id, full_name)
);
