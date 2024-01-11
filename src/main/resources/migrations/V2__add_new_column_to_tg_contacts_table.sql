ALTER TABLE telegram_contacts
ADD COLUMN name VARCHAR(255),
ADD COLUMN email VARCHAR(255);

ALTER TABLE public.telegram_contacts
ALTER COLUMN tg_chat_id DROP NOT NULL;

ALTER TABLE public.telegram_contacts
DROP COLUMN telegram_username;
