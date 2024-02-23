CREATE SCHEMA account_data;
SET SCHEMA account_data;
CREATE TABLE account_data.account
(
    id                    INT PRIMARY KEY AUTO_INCREMENT,
    first_name            VARCHAR(100) NOT NULL,
    last_name             VARCHAR(100) NOT NULL,
    middle_name           VARCHAR(100),
    birth_date            DATE         NOT NULL,
    personal_phone_number VARCHAR(20)  NOT NULL UNIQUE,
    work_phone_number     VARCHAR(20),
    personal_address      TEXT,
    work_address          TEXT,
    email                 VARCHAR(100) NOT NULL UNIQUE,
    icq                   VARCHAR(50) UNIQUE,
    skype                 VARCHAR(50) UNIQUE,
    additional_info       TEXT
);
CREATE SCHEMA group_data;
SET SCHEMA group_data;
CREATE TABLE group_data."group"
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    group_name    VARCHAR(255) NOT NULL UNIQUE,
    description   TEXT,
    creation_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    owner_id      INT REFERENCES account_data.account (id) ON DELETE CASCADE,
    group_status  VARCHAR(50)
);
CREATE SCHEMA friend_data;
SET SCHEMA friend_data;
CREATE TABLE friend_data.friendship
(
    id_1         INT,
    id_2         INT,
    status       BOOLEAN NOT NULL DEFAULT FALSE,
    requester_id INT,
    accepter_id  INT,
    CONSTRAINT friendship_pk PRIMARY KEY (id_1, id_2),
    CONSTRAINT friendship_to_id1_fk FOREIGN KEY (id_1) REFERENCES account_data.account (id) ON DELETE CASCADE,
    CONSTRAINT friendship_to_id2_fk FOREIGN KEY (id_2) REFERENCES account_data.account (id) ON DELETE CASCADE,
    CONSTRAINT friendship_to_requester_fk FOREIGN KEY (requester_id) REFERENCES account_data.account (id) ON DELETE CASCADE,
    CONSTRAINT friendship_to_accepter_fk FOREIGN KEY (accepter_id) REFERENCES account_data.account (id) ON DELETE CASCADE,
    CONSTRAINT friends_are_distinct_ck CHECK (id_1 < id_2)
);