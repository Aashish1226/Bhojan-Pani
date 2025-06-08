CREATE TABLE order_config
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    tax_rate         DECIMAL(10, 2) NOT NULL,
    default_discount DOUBLE NOT NULL,
    is_active        BOOLEAN        NOT NULL DEFAULT TRUE,
    delivery_charges DECIMAL(10, 2) NOT NULL
);

INSERT INTO order_config (tax_rate, default_discount, is_active, delivery_charges)
VALUES (18.00, 10.0, TRUE, 20.00);
