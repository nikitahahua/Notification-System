-- Пример вставки пользователей в таблицу Users
INSERT INTO users (id, fullname, email, role, password, enabled)
VALUES
    (1, 'John Doe', 'john.doe@example.com', 0, '$2a$10$9wZIWkCmQImYFFcQG9jzwuG/C3ceRZgAXxK9sLla5hXR6/TA7.s/m', true),
    (2, 'Alice Smith', 'alice.smith@example.com', 1, '$2a$10$6amiq//ceZY/SY/3THOQSeOGifq35cFHKZPM8uZizf7vEKvN74dQq', true);

--password123
--admin123

INSERT INTO Contacts (id, user_id, contact_name, email)
VALUES
    (1, 1, 'Hahua', '2gagua121@gmail.com'),
    (2, 2, 'Panas', '123tapedope@gmail.com');
--telegran-contacts
INSERT INTO telegram_contacts (tg_user_id, name, email)
VALUES
    (1,'sheikh', '2gagua121@gmail.com');

    INSERT INTO telegram_contacts (tg_user_id, name, email)
VALUES
    (1, 'Mis. ', '123tapedope@gmail.com');

-- message templates
INSERT INTO message_templates (template_id, template_text, user_id) VALUES
    (1, 'Hello, {{username}}! i wish you a happy Christmas.', 1),
    (2, 'Welcome, {{username}}! See you later ;D.', 1);

INSERT INTO message_templates (template_id, template_text, user_id) VALUES
    (3, 'Hello, {{username}}! Your account has been successfully created.', 2),
    (4, 'Welcome, {{username}}! We hope you enjoy your time with us.', 2);



SELECT * from users

SELECT * from contacts

SELECT * from message_templates

SELECT * from telegram_contacts

SELECT * from flyway_schema_history

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

