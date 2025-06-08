CREATE TABLE carts
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,

    create_date DATETIME NOT NULL,

    update_date DATETIME NOT NULL,

    user_id     BIGINT   NOT NULL,

    is_active   BOOLEAN  NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users (id)
);
