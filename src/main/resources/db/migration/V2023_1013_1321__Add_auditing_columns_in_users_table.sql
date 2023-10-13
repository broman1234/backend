alter table users
    add column created_at timestamp not null,
    add column updated_at timestamp not null;