DELETE
FROM account_data.accounts;
ALTER TABLE account_data.accounts
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM group_data."group";
ALTER TABLE group_data."group"
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.group_messages;
ALTER TABLE message_data.group_messages
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.personal_messages;
ALTER TABLE message_data.personal_messages
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.personal_wall_messages;
ALTER TABLE message_data.personal_wall_messages
    ALTER COLUMN id RESTART WITH 1;