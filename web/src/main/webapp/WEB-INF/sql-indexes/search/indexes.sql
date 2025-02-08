-- 1. optimizing 'searching' logic

-- 1.1 searching accounts
SELECT *
FROM account_data.accounts a
WHERE LOWER(a.first_name)
    LIKE LOWER(:searchQuery)
   OR LOWER(a.last_name) LIKE LOWER(:searchQuery);

ALTER TABLE account_data.accounts
    ADD COLUMN fullname TEXT
        GENERATED ALWAYS AS (LOWER(first_name || ' ' || last_name)) STORED;

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_accounts_full_name_trgm ON account_data.accounts USING GIN (fullname gin_trgm_ops);

SELECT *
FROM account_data.accounts a
WHERE account_data.similarity(a.fullname, 'ivan' :: text) > 0.3
  AND id > 12345
ORDER BY id
LIMIT 100;

/*
  | before | 148258.863 ms   |
  | after  | 44.924 ms       |
  | boost  | 3300.21 times   |
 */
