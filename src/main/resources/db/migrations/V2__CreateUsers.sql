CREATE TABLE users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_uuid     VARCHAR(255) UNIQUE NOT NULL,
    create_date   DATETIME            NOT NULL,
    update_date   DATETIME,
    date_of_birth DATE,
    first_name    VARCHAR(255),
    middle_name   VARCHAR(255),
    last_name     VARCHAR(255),
    email         VARCHAR(255)        NOT NULL,
    phone_number  VARCHAR(255)        NOT NULL,
    password      VARCHAR(255)        NOT NULL,
    country_code  VARCHAR(255)        NOT NULL,
    is_active     BIT(1)              NOT NULL DEFAULT TRUE,
    role_id       BIGINT,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE INDEX idx_user_uuid ON users (user_uuid);
