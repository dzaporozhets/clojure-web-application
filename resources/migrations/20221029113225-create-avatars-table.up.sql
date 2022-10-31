CREATE TABLE IF NOT EXISTS avatars (
  id    SERIAL PRIMARY KEY,
  name  varchar(255) NOT NULL,
  user_id integer NOT NULL,
  timestamp timestamp DEFAULT current_timestamp
);
