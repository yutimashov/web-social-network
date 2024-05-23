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
