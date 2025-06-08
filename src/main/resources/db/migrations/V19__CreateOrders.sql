CREATE TABLE orders
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,

    order_number        VARCHAR(255)   NOT NULL UNIQUE,

    cart_id             BIGINT         NOT NULL,

    order_status        VARCHAR(255)   NOT NULL DEFAULT 'PLACED',

    payment_attempts    INT                     DEFAULT 0,

    total_amount        DECIMAL(10, 2) NOT NULL DEFAULT 0.00,

    tax                 DECIMAL(10, 2) NOT NULL DEFAULT 0.00,

    discount            DECIMAL(10, 2) NOT NULL DEFAULT 0.00,

    final_amount        DECIMAL(10, 2) NOT NULL DEFAULT 0.00,

    create_date         DATETIME       NOT NULL,

    update_date         DATETIME       NOT NULL,

    delivery_address_id BIGINT,

    order_placed_at     DATETIME       NOT NULL,

    order_delivered_at  DATETIME,

    delivery_charges    DECIMAL(10, 2) NOT NULL DEFAULT 0.00,

    is_active           BOOLEAN        NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES carts (id),

    CONSTRAINT fk_delivery_address FOREIGN KEY (delivery_address_id) REFERENCES addresses (id)
);

-- Indexes
CREATE INDEX idx_order_status ON orders (order_status);
CREATE INDEX idx_cart_id ON orders (cart_id);
CREATE INDEX idx_order_number ON orders (order_number);
CREATE INDEX idx_order_placed_at ON orders (order_placed_at);
CREATE INDEX idx_order_delivered_at ON orders (order_delivered_at);
