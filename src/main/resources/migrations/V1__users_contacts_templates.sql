create table public.users
(
    id         bigserial
        primary key,
    tg_chat_id bigint,
    email      varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    enabled    boolean      not null,
    fullname   varchar(255) not null,
    password   varchar(255) not null,
    role       smallint     not null
        constraint users_role_check
            check ((role >= 0) AND (role <= 1))
);

alter table public.users
    owner to postgres;

create table public.contacts
(
    id           bigserial
        primary key,
    contact_name varchar(255) not null,
    email        varchar(255) not null
        constraint uk_728mksvqr0n907kujew6p3jc0
            unique,
    user_id      bigint       not null
        constraint fkna8bddygr3l3kq1imghgcskt8
            references public.users
);

alter table public.contacts
    owner to postgres;

create table public.message_templates
(
    template_id   bigserial
        primary key,
    template_text varchar(255),
    user_id       bigint
        constraint fk5206rm5g9f4c1ed6u4vb49k7l
            references public.users
);

alter table public.message_templates
    owner to postgres;

create table public.telegram_contacts
(
    id                bigserial
        primary key,
    tg_chat_id        bigint       not null
        constraint uk_c5lhkydpswhclqsf6cby8galy
            unique,
    telegram_username varchar(255) not null
        constraint uk_soxjfp2ejc9vqh81i7efvveuo
            unique,
    tg_user_id        bigint       not null
        constraint fkdu3ws8gq9f5j7a7n0r34umq8t
            references public.users
);

alter table public.telegram_contacts
    owner to postgres;

