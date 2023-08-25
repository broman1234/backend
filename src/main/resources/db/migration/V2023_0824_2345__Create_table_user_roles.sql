create table user_roles (
    id bigint primary key auto_increment not null,
    user_id bigint,
    role_id bigint
)