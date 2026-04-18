--liquibase formatted sql
--changeset nikolay:2
CREATE TABLE IF NOT EXISTS time_records (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    work_description TEXT,
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
    CONSTRAINT check_time_order CHECK (end_time > start_time)
);

-- Индекс для ускорения поиска записей сотрудника за период
CREATE INDEX idx_time_records_employee_period ON time_records (employee_id, start_time, end_time);

--rollback DROP TABLE time_records;