-- Добавляем тестового юзера (пароль: password123)
INSERT INTO users (username, password, role)
VALUES ('testuser', '$2a$10$EPluYyRj.UFcOEfmPKcBqu7dbBpoZE8OOPQyWUyqH66NSyZXxf9G2', 'USER');

-- Добавляем пару задач
INSERT INTO tasks (title, description, status)
VALUES
    ('Разработать API', 'Реализовать эндпоинты для учета времени', 'IN_PROGRESS'),
    ('Написать тесты', 'Покрыть сервис модульными тестами', 'NEW');

-- Добавляем запись времени для testuser (id=1) по задаче (id=1)
INSERT INTO time_records (employee_id, task_id, start_time, end_time, work_description)
VALUES (1, 1, NOW() - INTERVAL '2 hours', NOW(), 'Реализация контроллеров и мапперов');