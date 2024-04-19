DELETE
FROM account_data.accounts;
ALTER TABLE account_data.accounts
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.messages;
ALTER TABLE message_data.messages
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.message_types;
ALTER TABLE message_data.message_types
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM message_data.message_images;
ALTER TABLE message_data.message_images
    ALTER COLUMN id RESTART WITH 1;