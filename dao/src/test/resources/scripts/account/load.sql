INSERT INTO account_data.accounts (first_name,
                                   last_name,
                                   middle_name,
                                   birth_date,
                                   personal_address,
                                   work_address,
                                   email,
                                   icq,
                                   skype,
                                   additional_info,
                                   role_type)
VALUES ('test', 'test', 'test', '2000-01-01', 'test', 'test', 'test', 'test', 'test', 'test', 'REGULAR'),
       ('test1', 'test1', 'test1', '2000-01-01', 'test1', 'test1', 'test1', 'test1', 'test1', 'test1', 'REGULAR'),
       ('test2', 'test2', 'test2', '2000-01-01', 'test2', 'test2', 'test2', 'test2', 'test2', 'test2', 'REGULAR'),
       ('test3', 'test3', 'test3', '2000-01-01', 'test3', 'test3', 'test3', 'test3', 'test3', 'test3', 'REGULAR');
INSERT INTO account_data.account_phones (account_id, phone_type, phone_number)
VALUES ('1', 'PERSONAL', '+375291112233');
INSERT INTO account_data.account_passwords (id, hash_password, salt)
VALUES ('1', 'test', 'test');