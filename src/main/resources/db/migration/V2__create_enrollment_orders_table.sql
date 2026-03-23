CREATE TABLE IF NOT EXISTS enrollment_orders
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id        BIGINT           NOT NULL,
    value          INTEGER UNSIGNED NOT NULL,
    due_date       DATETIME         NOT NULL,
    payment_method VARCHAR(20)      NOT NULL,
    status         VARCHAR(20)      NOT NULL,
    paid_at        DATETIME,
    created_at     DATETIME         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);