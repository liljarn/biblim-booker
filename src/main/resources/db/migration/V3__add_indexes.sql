CREATE INDEX IF NOT EXISTS idx_comments_book_id ON comments(book_id);

CREATE INDEX IF NOT EXISTS idx_books_author_id ON books(author_id);

CREATE INDEX IF NOT EXISTS idx_book_genre_composite ON book_genre(book_id, genre_id);

CREATE INDEX IF NOT EXISTS idx_book_units_book_id ON book_units(book_id);

CREATE INDEX IF NOT EXISTS idx_book_rent_queue_book_user ON book_rent_queue(book_id, user_id) WHERE deleted_at IS NULL;

CREATE INDEX IF NOT EXISTS idx_book_reservation_queue_due_date ON book_reservation_queue(due_date);
CREATE INDEX IF NOT EXISTS idx_book_reservation_queue_user_book ON book_reservation_queue(user_id, book_id);
