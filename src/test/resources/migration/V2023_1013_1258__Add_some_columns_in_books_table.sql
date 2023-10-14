ALTER TABLE books
    ADD COLUMN cover_image VARCHAR(255) NOT NULL;
ALTER TABLE books
    ADD COLUMN rating INT;
ALTER TABLE books
    ADD COLUMN rating_count BIGINT;
ALTER TABLE books
    ADD COLUMN want_to_read_count BIGINT;
ALTER TABLE books
    ADD COLUMN have_read_count BIGINT;
ALTER TABLE books
    ADD COLUMN created_at TIMESTAMP NOT NULL;
ALTER TABLE books
    ADD COLUMN updated_at TIMESTAMP NOT NULL;

