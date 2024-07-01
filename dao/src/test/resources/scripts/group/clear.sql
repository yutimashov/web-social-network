DELETE
FROM account_data.account;
ALTER TABLE account_data.account
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM group_data.groups;
ALTER TABLE group_data.groups
    ALTER COLUMN id RESTART WITH 1;