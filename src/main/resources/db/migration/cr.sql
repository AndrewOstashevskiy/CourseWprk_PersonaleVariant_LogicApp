CREATE SCHEMA IF NOT EXISTS LogicApp.public;

create table LogicApp.message
(
    id uuid not null,
    text text,
    tag text
);

create unique index if not exists message_id_uindex
    on LogicApp.message (id);

alter table LogicApp.message
    add constraint message_pk
        primary key (id);