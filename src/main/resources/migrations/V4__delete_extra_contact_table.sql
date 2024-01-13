DROP TABLE IF EXISTS telegram_contacts;
DROP TABLE IF EXISTS message_templates;
DROP TABLE IF EXISTS Contacts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role INTEGER NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    tg_chat_id BIGINT,
    CONSTRAINT role_check CHECK (role >= 0 AND role <= 2)
);

CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    contact_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    tg_chat_id BIGINT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE public.message_templates (
    template_id BIGSERIAL PRIMARY KEY,
    template_text VARCHAR(255),
    user_id BIGINT REFERENCES public.users(id)
);

INSERT INTO users (id, fullname, email, role, password, enabled)
VALUES
    (1, 'John Doe', 'john.doe@example.com', 0, '$2a$10$9wZIWkCmQImYFFcQG9jzwuG/C3ceRZgAXxK9sLla5hXR6/TA7.s/m', true),
    (2, 'Alice Smith', 'alice.smith@example.com', 1, '$2a$10$6amiq//ceZY/SY/3THOQSeOGifq35cFHKZPM8uZizf7vEKvN74dQq', true);

--password123
--admin123

INSERT INTO contacts (user_id, contact_name, email)
VALUES
    (1, 'Hahua', '2gagua121@gmail.com'),
    (2, 'Panas', '123tapedope@gmail.com');

-- message templates
INSERT INTO message_templates (template_id, template_text, user_id) VALUES
    (1, 'Hello, {{username}}! i wish you a happy Christmas.', 1),
    (2, 'Welcome, {{username}}! See you later ;D.', 1);

INSERT INTO message_templates (template_id, template_text, user_id) VALUES
    (3, 'Hello, {{username}}! Your account has been successfully created.', 2),
    (4, 'Welcome, {{username}}! We hope you enjoy your time with us.', 2);
