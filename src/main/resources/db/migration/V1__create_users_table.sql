CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    cpf          VARCHAR(255) NOT NULL UNIQUE,
    phone        VARCHAR(255) NOT NULL UNIQUE,
    gender       VARCHAR(10)  NOT NULL,
    role         VARCHAR(10)  NOT NULL,
    birth_date   DATE         NOT NULL,
    disabilities VARCHAR(255),
    created_at   DATETIME     NOT NULL
);