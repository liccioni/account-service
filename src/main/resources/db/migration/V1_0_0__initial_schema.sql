create table persons
(
    pk            int8         not null,
    created_date  timestamp    not null,
    modified_date timestamp,
    version       int8         not null,
    identifier    varchar(255) not null,
    primary key (pk)
);

alter table if exists persons
    add constraint UK_r86njetmqix3klhd6p6k5olob unique (identifier);