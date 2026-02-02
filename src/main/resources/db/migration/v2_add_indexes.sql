CREATE INDEX idx_student_groups_created_at ON student_groups(created_at);

CREATE INDEX idx_students_group_full_name ON students(group_id, full_name);