CREATE TABLE cart_items
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date     DATETIME            NOT NULL,
    update_date     DATETIME            NOT NULL,
    food_id         BIGINT              NOT NULL,
    food_variant_id BIGINT,
    quantity        INT                 NOT NULL,
    is_active       BIT(1) DEFAULT TRUE NOT NULL,
    cart_id         BIGINT,

    CONSTRAINT fk_food_id FOREIGN KEY (food_id) REFERENCES foods (id),
    CONSTRAINT fk_food_variant_id FOREIGN KEY (food_variant_id) REFERENCES food_variants (id),
    CONSTRAINT fk_cart_id FOREIGN KEY (cart_id) REFERENCES carts (id)
);
