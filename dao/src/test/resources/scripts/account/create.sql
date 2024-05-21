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
    avatar            BYTEA,
    CONSTRAINT account_data_unique_fields UNIQUE (email, icq, skype)
);
SET SCHEMA account_data;
CREATE TABLE account_data.account_phones
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    account_id   INT,
    phone_type   VARCHAR(10) NOT NULL,
    phone_number VARCHAR(32) NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account_data.accounts (id) ON DELETE CASCADE
);
SET SCHEMA account_data;
CREATE TABLE account_data.account_passwords
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    account_id    INT,
    hash_password VARCHAR(64),
    salt          VARCHAR(32),
    CONSTRAINT fk_pass_account FOREIGN KEY (account_id) REFERENCES account_data.accounts (id) ON DELETE CASCADE
);
