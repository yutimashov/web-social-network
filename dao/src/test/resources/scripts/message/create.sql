CREATE SCHEMA account_data;
SET SCHEMA account_data;
CREATE TABLE account_data.account
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
SET SCHEMA account_data;
CREATE TABLE account_data.account_phones
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    account_id   INT,
    phone_type   VARCHAR(10) NOT NULL,
    phone_number VARCHAR(32) NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account_data.account (id) ON DELETE CASCADE
);
CREATE SCHEMA message_data;
SET SCHEMA message_data;
CREATE TABLE message_data.message_type
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    message_type VARCHAR(50) NOT NULL
);
SET SCHEMA message_data;
CREATE TABLE message_data.messages
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    account_author_id INT REFERENCES account_data.account (id) ON DELETE CASCADE,
    creation_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    message_text      TEXT,
    destination_type  INT REFERENCES message_data.message_type (id) ON DELETE CASCADE
);
SET SCHEMA message_data;
CREATE TABLE message_data.message_images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    image_blob BLOB,
    message_id INT REFERENCES message_data.messages (id) ON DELETE CASCADE
);
