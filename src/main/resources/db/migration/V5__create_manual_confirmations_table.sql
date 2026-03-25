CREATE TABLE IF NOT EXISTS manual_confirmations
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id     BIGINT   NOT NULL,
    confirmed_by BIGINT   NOT NULL,
    created_at   DATETIME NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (confirmed_by) REFERENCES users (id)
);