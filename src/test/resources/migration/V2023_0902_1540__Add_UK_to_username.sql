ALTER TABLE users
    ADD CONSTRAINT UK_users_username UNIQUE (username);
