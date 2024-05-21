INSERT INTO account_data.accounts (first_name, last_name, middle_name, birth_date,
                                   personal_address, work_address, email, icq, skype, additional_info)
VALUES ('test', 'test', 'test', '2000-01-01', 'test', 'test', 'test', 'test', 'test', 'test'),
       ('test1', 'test1', 'test1', '2000-01-01', 'test1', 'test1', 'test1', 'test1', 'test1', 'test1'),
       ('test2', 'test2', 'test2', '2000-01-01', 'test2', 'test2', 'test2', 'test2', 'test2', 'test2'),
       ('test3', 'test3', 'test3', '2000-01-01', 'test3', 'test3', 'test3', 'test3', 'test3', 'test3');
INSERT INTO account_data.account_phones (account_id, phone_type, phone_number)
VALUES ('1', 'PERSONAL', '+375291112233'),
       ('1', 'WORKING', '+375291112233');
INSERT INTO account_data.account_passwords (account_id, hash_password, salt)
VALUES ('1', 'test', 'test');