CREATE SCHEMA account_data;
SET SCHEMA account_data;
CREATE TABLE account_data.accounts
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    first_name        VARCHAR(100) NOT NULL,
    last_name         VARCHAR(100) NOT NULL,
    email             VARCHAR(100) NOT NULL,
    middle_name       VARCHAR(100),
    birth_date        DATE,
    personal_address  TEXT,
    work_address      TEXT,
    icq               VARCHAR(50),
    skype             VARCHAR(50),
    additional_info   TEXT,
    role_type         VARCHAR(32) DEFAULT 'REGULAR',
    registration_date TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT account_data_unique_fields UNIQUE (email, icq, skype)
);
CREATE SCHEMA message_data;
SET SCHEMA message_data;
CREATE TABLE message_data.messages
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    account_author_id INT REFERENCES account_data.accounts (id) ON DELETE CASCADE,
    creation_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    message_text      TEXT
);
SET SCHEMA message_data;
CREATE TABLE message_data.message_types
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    message_type VARCHAR(50) NOT NULL,
    message_id   INT REFERENCES message_data.messages (id) ON DELETE CASCADE
);
SET SCHEMA message_data;
CREATE TABLE message_data.message_images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    image_blob BLOB,
    message_id INT REFERENCES message_data.messages (id) ON DELETE CASCADE
);
