DELETE
FROM account_data.accounts;
ALTER TABLE account_data.accounts
    ALTER COLUMN id RESTART WITH 1;
DELETE
FROM account_data.account_phones;
ALTER TABLE account_data.account_phones
    ALTER COLUMN id RESTART WITH 1;