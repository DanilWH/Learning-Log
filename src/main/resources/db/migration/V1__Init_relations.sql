create sequence hibernate_sequence start 1 increment 1;

create table entries (
                         id int8 not null,
                         date_time timestamp,
                         text text,
                         topic_id int8 not null,
                         primary key (id)
);

create table entries_uploads (
                                 entry_id int8 not null,
                                 uploads_id int8 not null
);

create table topics (
                        id int8 not null,
                        access int4,
                        description text,
                        entries_number int8,
                        time_creation timestamp,
                        title varchar(255),
                        user_id int8,
                        primary key (id)
);

create table uploads (
                         id int8 not null,
                         bytes bytea,
                         filename varchar(255),
                         primary key (id)
);

create table user_role (
                           user_id int8 not null,
                           roles varchar(255)
);

create table user_subscriptions (
                                    subscriber_id int8 not null,
                                    channel_id int8 not null,
                                    primary key (channel_id, subscriber_id)
);

create table usr (
                     id int8 not null,
                     active boolean not null,
                     password varchar(255),
                     username varchar(255),
                     primary key (id)
);

alter table if exists entries_uploads
    add constraint UK_nw6ls4n81luui68b3s43s1mmq unique (uploads_id);

alter table if exists entries
    add constraint entries_topics_id_fk
        foreign key (topic_id)
            references topics
            on delete cascade;

alter table if exists entries_uploads
    add constraint entries_uploads_uploads_id_fk
        foreign key (uploads_id)
            references uploads;

alter table if exists entries_uploads
    add constraint entries_uploads_entry_id_fk
        foreign key (entry_id)
            references entries;

alter table if exists topics
    add constraint topics_user_id_fk
        foreign key (user_id)
            references usr
            on delete cascade;

alter table if exists user_role
    add constraint user_role_user_id_fk
        foreign key (user_id)
            references usr;

alter table if exists user_subscriptions
    add constraint user_subscriptions_channel_id_fk
        foreign key (channel_id)
            references usr;

alter table if exists user_subscriptions
    add constraint user_subscriptions_subscriber_id_fk
        foreign key (subscriber_id)
            references usr;