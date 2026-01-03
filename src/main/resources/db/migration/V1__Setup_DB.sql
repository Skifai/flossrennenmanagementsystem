CREATE TABLE benutzer
(
    id            BIGSERIAL PRIMARY KEY,
    vorname       VARCHAR(100)        NOT NULL,
    nachname      VARCHAR(100)        NOT NULL,
    telefonnummer VARCHAR(15) UNIQUE  NOT NULL,
    email         VARCHAR(254) UNIQUE NOT NULL,
    passwordhash  VARCHAR(100)        NOT NULL,
    rolle         VARCHAR(50)         NOT NULL
);

CREATE TABLE ressort
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(100) UNIQUE NOT NULL,
    beschreibung   VARCHAR(300),
    zustaendigkeit VARCHAR(300),
    ressortleitung BIGINT REFERENCES benutzer (id)
);

CREATE TABLE helfer
(
    id            BIGSERIAL PRIMARY KEY,
    vorname       VARCHAR(100)        NOT NULL,
    nachname      VARCHAR(100)        NOT NULL,
    email         VARCHAR(254) UNIQUE NOT NULL,
    telefonnummer VARCHAR(15) UNIQUE  NOT NULL,
    ressort       BIGINT              NOT NULL REFERENCES ressort (id)
);

CREATE TABLE einsatz
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    beschreibung      VARCHAR(300) NOT NULL,
    startzeit         TIMESTAMP    NOT NULL,
    endzeit           TIMESTAMP    NOT NULL,
    ort               VARCHAR(150) NOT NULL,
    einsatzmittel     VARCHAR(200),
    benoetigte_helfer INTEGER      NOT NULL,
    status            VARCHAR(50)  NOT NULL,
    ressort           BIGINT       NOT NULL REFERENCES ressort (id)
);

CREATE TABLE einsatz_zuweisung
(
    id         BIGSERIAL PRIMARY KEY,
    einsatz_id BIGINT NOT NULL REFERENCES einsatz (id),
    helfer_id  BIGINT NOT NULL REFERENCES helfer (id)
);

CREATE TABLE log
(
    id        BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP   NOT NULL,
    type      VARCHAR(50) NOT NULL,
    log_level VARCHAR(20) NOT NULL,
    benutzer  VARCHAR(255),
    message   TEXT        NOT NULL
);