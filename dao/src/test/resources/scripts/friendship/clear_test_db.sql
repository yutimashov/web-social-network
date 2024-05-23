DELETE
FROM account_data.account;
ALTER TABLE account_data.account
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM friend_data.friendship;