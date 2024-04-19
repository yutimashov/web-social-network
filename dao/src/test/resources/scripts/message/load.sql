INSERT INTO account_data.account (first_name, last_name, middle_name, birth_date, personal_address,
                                  work_address, email, icq, skype, additional_info)
VALUES ('test', 'test', 'test', '1800-01-01', 'test', 'test', 'test', 'test', 'test', 'test'),
       ('test1', 'test1', 'test1', '1800-01-01', 'test1', 'test1', 'test1', 'test1', 'test1', 'test1'),
       ('test2', 'test2', 'test2', '1800-01-01', 'test2', 'test2', 'test2', 'test2', 'test2', 'test2'),
       ('test3', 'test3', 'test3', '1800-01-01', 'test3', 'test3', 'test3', 'test3', 'test3', 'test3');
INSERT INTO message_data.message_type (message_type)
VALUES ('ACCOUNT_PERSONAL'),
       ('GROUP'),
       ('ACCOUNT_WALL');
INSERT INTO message_data.messages (account_author_id, creation_date, message_text, destination_type)
VALUES ('1', '2000-01-01', 'test', '1');
INSERT INTO message_data.message_images (image_blob, message_id)
VALUES ('test', '1');