CREATE TABLE addresses
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date    DATETIME     NOT NULL,
    update_date    DATETIME     NOT NULL,
    address_line_1 VARCHAR(255) NOT NULL,
    address_line_2 VARCHAR(255),
    landmark       VARCHAR(255),
    postal_code    VARCHAR(255) NOT NULL,
    address_type   VARCHAR(50)  NOT NULL,
    is_active      BIT(1) DEFAULT TRUE NOT NULL,
    city_id        BIGINT       NOT NULL,
    state_id       BIGINT       NOT NULL,
    country_id     BIGINT       NOT NULL,
    user_id        BIGINT       NOT NULL,

    CONSTRAINT fk_addresses_city FOREIGN KEY (city_id) REFERENCES cities (id),
    CONSTRAINT fk_addresses_state FOREIGN KEY (state_id) REFERENCES states (id),
    CONSTRAINT fk_addresses_country FOREIGN KEY (country_id) REFERENCES countries (id),
    CONSTRAINT fk_addresses_user FOREIGN KEY (user_id) REFERENCES users (id)
);
