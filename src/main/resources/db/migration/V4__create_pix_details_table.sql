CREATE TABLE IF NOT EXISTS pix_details
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id        BIGINT       NOT NULL,
    copy_and_paste_code TEXT         NOT NULL,
    qr_code_url     VARCHAR(255) NOT NULL,
    created_at      DATETIME     NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);