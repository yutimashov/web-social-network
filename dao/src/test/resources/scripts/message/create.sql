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
CREATE SCHEMA group_data;
SET SCHEMA group_data;
CREATE TABLE group_data.groups
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    group_name    VARCHAR(255) NOT NULL UNIQUE,
    description   TEXT,
    creation_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    owner_id      INT REFERENCES account_data.accounts (id) ON DELETE CASCADE,
    group_status  VARCHAR(50)
);
CREATE SCHEMA message_data;
SET SCHEMA message_data;
CREATE TABLE message_data.group_messages
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    account_author_id INT REFERENCES account_data.accounts (id) ON DELETE CASCADE,
    group_id          INT REFERENCES group_data.groups (id) ON DELETE CASCADE,
    creation_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    message_text      TEXT,
    message_image     BLOB
);
