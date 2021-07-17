insert into usr (id, active, password, username) values
    (1, true, '341021dan', 'll_admin');

insert into user_role (user_id, roles) values
    (1, 'ADMIN'),
    (1, 'USER');

alter sequence hibernate_sequence restart with 2;
