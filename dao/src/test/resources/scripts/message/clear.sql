DELETE
FROM account_data.accounts;
ALTER TABLE account_data.accounts
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM group_data.groups;
ALTER TABLE group_data.groups
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.group_messages;
ALTER TABLE message_data.group_messages
    ALTER COLUMN id RESTART WITH 1;