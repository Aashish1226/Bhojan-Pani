CREATE TABLE `foods`
(
    `id`                           BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    create_date                    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date                    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `name`                         VARCHAR(255)  NOT NULL,
    `description`                  VARCHAR(1000) NOT NULL,
    `price` DOUBLE NOT NULL,
    `image_url`                    VARCHAR(255)  NOT NULL,
    `is_available`                 BIT(1)                 DEFAULT TRUE,
    `food_type`                    VARCHAR(20)   NOT NULL,
    `category_id`                  BIGINT        NOT NULL,
    `is_active`                    BIT(1)                 DEFAULT TRUE,
    `has_variants`                 BIT(1)        NOT NULL,
    `total_order_count`            INT           NOT NULL DEFAULT 0,
    `average_prep_time_in_minutes` INT           NOT NULL DEFAULT 0,
    CONSTRAINT fk_food_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE INDEX `idx_food_category_active` ON `foods` (`category_id`, `is_active`);
CREATE INDEX `idx_food_type_available` ON `foods` (`food_type`, `is_available`);
CREATE INDEX `idx_food_price_range` ON `foods` (`price`);
CREATE INDEX `idx_food_name_search` ON `foods` (`name`);
CREATE INDEX `idx_food_composite` ON `foods` (`is_active`, `category_id`, `food_type`, `is_available`, `price`);
CREATE INDEX `idx_food_order_count` ON `foods` (`total_order_count`);

