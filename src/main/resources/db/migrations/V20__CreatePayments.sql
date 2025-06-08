CREATE TABLE payments
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_uuid           VARCHAR(255)   NOT NULL,
    amount              DECIMAL(10, 2) NOT NULL,
    currency            VARCHAR(255)   NOT NULL DEFAULT 'INR',
    status              VARCHAR(255)   NOT NULL,
    razorpay_status     VARCHAR(255),
    razorpay_method     VARCHAR(255),
    bank_code           VARCHAR(255),
    vpa                 VARCHAR(255),
    wallet_name         VARCHAR(255),
    error_code          VARCHAR(255),
    error_description   VARCHAR(500),
    error_source        VARCHAR(255),
    error_step          VARCHAR(255),
    error_reason        VARCHAR(255),
    razorpay_fee        INT,
    razorpay_tax        INT,
    amount_refunded     INT,
    refund_status       VARCHAR(255),
    customer_email      VARCHAR(255),
    customer_contact    VARCHAR(255),
    method              VARCHAR(255),
    failure_reason      VARCHAR(255),
    razorpay_order_id   VARCHAR(255) UNIQUE,
    razorpay_payment_id VARCHAR(255),
    razorpay_signature  VARCHAR(255),
    provider_response   TEXT,
    created_at          DATETIME       NOT NULL,
    updated_at          DATETIME       NOT NULL,
    order_id            BIGINT,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE INDEX idx_payment_status ON payments (status);
CREATE INDEX idx_payment_method ON payments (method);
CREATE INDEX idx_user_uuid ON payments (user_uuid);
CREATE INDEX idx_razorpay_order_id ON payments (razorpay_order_id);
CREATE INDEX idx_failure_reason ON payments (failure_reason);
