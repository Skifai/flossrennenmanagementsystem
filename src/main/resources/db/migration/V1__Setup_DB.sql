CREATE TABLE IF NOT EXISTS benutzer
(
    id            BIGSERIAL PRIMARY KEY,
    vorname       VARCHAR(100)        NOT NULL,
    nachname      VARCHAR(100)        NOT NULL,
    telefonnummer VARCHAR(15) UNIQUE  NOT NULL,
    email         VARCHAR(254) UNIQUE NOT NULL,
    passwordhash  VARCHAR(100)        NOT NULL,
    rolle         VARCHAR(50)         NOT NULL
);

CREATE TABLE IF NOT EXISTS ressort
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(100) UNIQUE NOT NULL,
    beschreibung   VARCHAR(300),
    zustaendigkeit VARCHAR(300),
    ressortleitung BIGINT REFERENCES benutzer (id)
);

CREATE TABLE IF NOT EXISTS helfer
(
    id            BIGSERIAL PRIMARY KEY,
    vorname       VARCHAR(100)        NOT NULL,
    nachname      VARCHAR(100)        NOT NULL,
    email         VARCHAR(254) UNIQUE NOT NULL,
    telefonnummer VARCHAR(15) UNIQUE  NOT NULL,
    ressort       BIGINT              NOT NULL REFERENCES ressort (id)
);
