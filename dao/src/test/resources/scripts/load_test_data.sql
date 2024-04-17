INSERT INTO account_data.account (first_name, last_name, middle_name, birth_date, personal_address,
                                  work_address, email, icq, skype, additional_info)
VALUES ('test', 'test', 'test', '1800-01-01', 'test', 'test', 'test', 'test', 'test', 'test'),
       ('test1', 'test1', 'test1', '1800-01-01', 'test1', 'test1', 'test1', 'test1', 'test1', 'test1'),
       ('test2', 'test2', 'test2', '1800-01-01', 'test2', 'test2', 'test2', 'test2', 'test2', 'test2'),
       ('test3', 'test3', 'test3', '1800-01-01', 'test3', 'test3', 'test3', 'test3', 'test3', 'test3');
INSERT INTO group_data."group" (group_name, description, owner_id, group_status)
VALUES ('test', 'test', '1', 'test');
INSERT INTO friend_data.friendship (id_1, id_2, status, requester_id, accepter_id)
VALUES ('1', '2', TRUE, '1', '2'),
       ('3', '4', FALSE, '3', '4');
INSERT INTO message_data.message_type (message_type)
VALUES ('ACCOUNT_PERSONAL'), ('GROUP'), ('ACCOUNT_WALL');