create table entry_likes (
    entry_id bigint not null references entries,
    user_id bigint not null references usr,
    primary key (entry_id, user_id)
);
