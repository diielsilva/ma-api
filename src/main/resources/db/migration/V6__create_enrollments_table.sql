CREATE TABLE IF NOT EXISTS enrollments
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id   BIGINT      NOT NULL,
    type       VARCHAR(20) NOT NULL,
    created_at DATETIME    NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);