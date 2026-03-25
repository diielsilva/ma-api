DROP TABLE manual_confirmations;

CREATE TABLE IF NOT EXISTS manual_confirmations
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    enrollment_id BIGINT   NOT NULL,
    confirmed_by  BIGINT   NOT NULL,
    created_at    DATETIME NOT NULL,
    FOREIGN KEY (enrollment_id) REFERENCES enrollments (id),
    FOREIGN KEY (confirmed_by) REFERENCES users (id)
);