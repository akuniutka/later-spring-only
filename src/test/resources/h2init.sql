CREATE TABLE IF NOT EXISTS users
(
  id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  first_name        VARCHAR(40)              NOT NULL,
  last_name         VARCHAR(40)              NOT NULL,
  email             VARCHAR(255)             NOT NULL,
  state             VARCHAR(40)              NOT NULL,
  registration_date TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS items
(
  id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id BIGINT       NOT NULL REFERENCES users (id),
  url     VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tags
(
  item_id BIGINT      NOT NULL REFERENCES items (id),
  name    VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS item_notes
(
  id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  item_id      BIGINT                   NOT NULL REFERENCES items (id),
  text         VARCHAR(2000),
  date_of_note TIMESTAMP WITH TIME ZONE NOT NULL
);
