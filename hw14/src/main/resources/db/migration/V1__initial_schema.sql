create table client
(
    id   bigserial   not null primary key,
    name varchar(50) not null
);

create table address
(
    id        bigserial    not null primary key,
    client_id bigint       not null unique references client (id) on delete cascade,
    street    varchar(100) not null
);

create table phone
(
    id        bigserial   not null primary key,
    client_id bigint      not null references client (id) on delete cascade,
    number    varchar(50) not null
);
create index idx_phone_client_id on phone (client_id);
