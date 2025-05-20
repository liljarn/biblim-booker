CREATE TABLE authors
(
    author_id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    author_name        VARCHAR(256) UNIQUE NOT NULL,
    author_description TEXT                NOT NULL,
    author_photo_url   TEXT                NOT NULL
);

CREATE TABLE books
(
    book_id      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    book_name    VARCHAR(256) NOT NULL,
    author_id    BIGINT       NOT NULL REFERENCES authors (author_id),
    release_year SMALLINT     NOT NULL,
    age_limit    SMALLINT     NOT NULL,
    description  TEXT         NOT NULL,
    rating       NUMERIC(2, 1) CHECK (rating >= 0.0 AND rating <= 5.0) DEFAULT 0.0,
    downloadable BOOLEAN      NOT NULL,
    photo_url    TEXT         NOT NULL
);

CREATE TABLE book_units
(
    book_unit_id UUID PRIMARY KEY,
    status       VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    book_id      BIGINT REFERENCES books (book_id)
);

CREATE TABLE genres
(
    genre_id   INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    genre_name VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE book_genre
(
    book_id  BIGINT NOT NULL REFERENCES books (book_id),
    genre_id INT    NOT NULL REFERENCES genres (genre_id),

    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE comments
(
    comment_id UUID PRIMARY KEY,
    user_id    UUID   NOT NULL,
    comment    TEXT   NOT NULL,
    rating     INT    NOT NULL CHECK (rating >= 0 AND rating <= 5),
    book_id    BIGINT NOT NULL REFERENCES books (book_id)
);

CREATE TABLE book_rent_queue
(
    rent_id      UUID PRIMARY KEY,
    book_id      BIGINT NOT NULL REFERENCES books (book_id),
    book_unit_id UUID   NOT NULL REFERENCES book_units (book_unit_id),
    user_id      UUID   NOT NULL,
    due_date     DATE   NOT NULL,
    deleted_at   TIMESTAMP
);

CREATE TABLE book_reservation_queue
(
    reservation_id UUID PRIMARY KEY,
    book_id        BIGINT      NOT NULL REFERENCES books (book_id),
    book_unit_id   UUID UNIQUE NOT NULL REFERENCES book_units (book_unit_id),
    user_id        UUID        NOT NULL,
    due_date       DATE        NOT NULL,
    deleted_at     TIMESTAMP
);
