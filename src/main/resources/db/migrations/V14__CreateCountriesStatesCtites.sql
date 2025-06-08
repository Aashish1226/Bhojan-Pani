CREATE TABLE IF NOT EXISTS countries
(
    id              BIGINT PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    iso2            VARCHAR(10)  NOT NULL,
    iso3            VARCHAR(10)  NOT NULL,
    phone_code      VARCHAR(40),
    capital         VARCHAR(150),
    currency        VARCHAR(50),
    currency_symbol VARCHAR(10),
    tld             VARCHAR(10),
    native          VARCHAR(150),
    emoji           VARCHAR(10),
    emojiu          VARCHAR(20),
    latitude        VARCHAR(50),
    longitude       VARCHAR(50),
    region_id       INT,
    subregion_id    INT,
    create_date     TIMESTAMP    NOT NULL,
    update_date     TIMESTAMP    NOT NULL
    );

CREATE TABLE IF NOT EXISTS states
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    state_code  VARCHAR(15)  NOT NULL,
    latitude    VARCHAR(20),
    longitude   VARCHAR(20),
    country_id  BIGINT,
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP    NOT NULL,
    CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS cities
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    latitude    VARCHAR(50),
    longitude   VARCHAR(50),
    state_id    BIGINT,
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP    NOT NULL,
    CONSTRAINT fk_state_id FOREIGN KEY (state_id) REFERENCES states (id) ON DELETE CASCADE
    );