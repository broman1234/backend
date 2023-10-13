ALTER TABLE books
    ADD COLUMN cover_image VARCHAR(255) NOT NULL,
    ADD COLUMN rating INT,
    ADD COLUMN rating_count BIGINT,
    ADD COLUMN want_to_read_count BIGINT,
    ADD COLUMN have_read_count BIGINT,
    ADD COLUMN created_at TIMESTAMP NOT NULL,
    ADD COLUMN updated_at TIMESTAMP NOT NULL;
