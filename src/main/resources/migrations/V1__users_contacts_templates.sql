CREATE TABLE public.users (
    id BIGSERIAL PRIMARY KEY,
    tg_chat_id BIGINT,
    email VARCHAR(255) NOT NULL UNIQUE,
    enabled BOOLEAN NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role SMALLINT NOT NULL CHECK (role >= 0 AND role <= 1)
);

CREATE TABLE public.contacts (
    id BIGSERIAL PRIMARY KEY,
    contact_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL REFERENCES public.users(id)
);

CREATE TABLE public.message_templates (
    template_id BIGSERIAL PRIMARY KEY,
    template_text VARCHAR(255),
    user_id BIGINT REFERENCES public.users(id)
);

CREATE TABLE public.telegram_contacts (
    id BIGSERIAL PRIMARY KEY,
    tg_chat_id BIGINT NOT NULL UNIQUE,
    telegram_username VARCHAR(255) NOT NULL UNIQUE,
    tg_user_id BIGINT NOT NULL REFERENCES public.users(id)
);
