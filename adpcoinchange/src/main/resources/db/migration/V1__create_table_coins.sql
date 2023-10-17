CREATE TABLE IF NOT EXISTS coin
(
    id               uuid PRIMARY KEY,
    value            numeric NOT NULL UNIQUE,
    available_amount numeric NOT NULL
);