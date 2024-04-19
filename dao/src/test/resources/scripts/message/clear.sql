DELETE
FROM account_data.account;
ALTER TABLE account_data.account
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.messages;
ALTER TABLE message_data.messages
    ALTER COLUMN id RESTART WITH 1;