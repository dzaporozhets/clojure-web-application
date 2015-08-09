CREATE TABLE IF NOT EXISTS users (
  id    SERIAL PRIMARY KEY,
  email varchar(255) NOT NULL UNIQUE,
  name  varchar(255) NOT NULL,
  encrypted_password varchar(255),
  timestamp timestamp DEFAULT current_timestamp
);
