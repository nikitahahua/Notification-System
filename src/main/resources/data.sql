-- Пример вставки пользователей в таблицу Users
INSERT INTO users (id, fullname, email, role, phone_number, password, chat_id)
VALUES
    (1, 'John Doe', 'john.doe@example.com', 0, '+123456789', '$2a$10$9wZIWkCmQImYFFcQG9jzwuG/C3ceRZgAXxK9sLla5hXR6/TA7.s/m', 123456),
    (2, 'Alice Smith', 'alice.smith@example.com', 1, '+987654321', '$2a$10$6amiq//ceZY/SY/3THOQSeOGifq35cFHKZPM8uZizf7vEKvN74dQq', 789012);
--password123
--admin123
-- Пример вставки контактов для пользователей в таблицу Contacts
INSERT INTO Contacts (id, user_id, contact_name, email, phone_number, chat_id)
VALUES
    (1, 1, 'Hahua', '2gagua121@gmail.com', '+111111111', 1111),
    (2, 2, 'Panas', '123tapedope@gmail.com', '+222222222', 2222);

SELECT * from users

SELECT * from contacts


UPDATE users
SET password = '$2a$10$9wZIWkCmQImYFFcQG9jzwuG/C3ceRZgAXxK9sLla5hXR6/TA7.s/m'
WHERE id = 1;



UPDATE users
SET password = '$2a$10$6amiq//ceZY/SY/3THOQSeOGifq35cFHKZPM8uZizf7vEKvN74dQq'
WHERE id = 2;


UPDATE users
SET email = 'john.doe@example.com'
WHERE id = 1;

UPDATE users
SET email = 'alice.smith@example.com'
WHERE id = 2;

UPDATE contacts
SET email = '2gagua121@gmail.com'
WHERE id = 1;

UPDATE contacts
SET email = '123tapedope@gmail.com'
WHERE id = 2;