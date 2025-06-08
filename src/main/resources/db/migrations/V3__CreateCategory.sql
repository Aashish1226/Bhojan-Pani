CREATE TABLE categories
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_date  DATETIME,
    restored_date DATETIME,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    image_url     VARCHAR(255) NOT NULL,
    is_active     BIT(1)       NOT NULL DEFAULT TRUE,
    is_default    BIT(1)       NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_category_is_active ON categories (is_active);
CREATE INDEX idx_category_name ON categories (name);