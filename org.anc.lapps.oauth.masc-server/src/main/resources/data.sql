insert into roles (role) values ('ROLE_USER');
insert into roles (role) values ('ROLE_DEV');
insert into roles (role) values ('ROLE_ADMIN');

insert into users (username, password, enabled) values ('anonymous', 'anonymous', true);
insert into users (username, password, enabled) values ('admin', 'password', true);

insert into authorities (username, authority) values ('anonymous', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');