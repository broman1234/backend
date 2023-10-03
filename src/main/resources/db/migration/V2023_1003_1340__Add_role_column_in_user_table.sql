alter table users
    add column role varchar(20) not null;

drop table roles;

drop table user_roles;