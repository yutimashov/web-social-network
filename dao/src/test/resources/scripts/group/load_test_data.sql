INSERT INTO account_data.account (first_name, last_name, middle_name, birth_date, personal_address,
                                  work_address, email, icq, skype, additional_info)
VALUES ('test', 'test', 'test', '1800-01-01', 'test', 'test', 'test', 'test', 'test', 'test'),
       ('test1', 'test1', 'test1', '1800-01-01', 'test1', 'test1', 'test1', 'test1', 'test1', 'test1'),
       ('test2', 'test2', 'test2', '1800-01-01', 'test2', 'test2', 'test2', 'test2', 'test2', 'test2'),
       ('test3', 'test3', 'test3', '1800-01-01', 'test3', 'test3', 'test3', 'test3', 'test3', 'test3');
INSERT INTO group_data.groups (group_name, description, owner_id, group_status)
VALUES ('test', 'test', '1', 'test');
