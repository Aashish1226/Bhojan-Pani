CREATE TABLE `food_variants`
(
    `id`               BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `create_date`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_date`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `label`            VARCHAR(255)  DEFAULT NULL,
    `quantity_info`    VARCHAR(255)  DEFAULT NULL,
    `price`            DOUBLE        NOT NULL,
    `serving_size`     VARCHAR(20)   DEFAULT NULL,
    `is_available`     BIT(1)        DEFAULT TRUE,
    `serving_unit`     VARCHAR(20)   DEFAULT NULL,
    `is_active`        BIT(1)        DEFAULT TRUE,
    `serving_quantity` INT           DEFAULT NULL,
    `food_id`          BIGINT        NOT NULL,
    CONSTRAINT fk_food_variant_food FOREIGN KEY (food_id) REFERENCES foods (id)
);
