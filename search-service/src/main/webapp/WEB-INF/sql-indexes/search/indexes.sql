-- 1. optimizing 'searching' logic

-- 1.1 initial query without optimizing
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts a
WHERE LOWER(a.first_name)
    LIKE '%egor%'
   OR LOWER(a.last_name) LIKE '%egor%';
-- Execution Time: 7446.967 ms

-- 1.2 add 2 indexes for prefix search
CREATE INDEX idx_lower_first_name ON account_data.accounts (LOWER(first_name) varchar_pattern_ops);
CREATE INDEX idx_lower_last_name ON account_data.accounts (LOWER(last_name) varchar_pattern_ops);
-- Execution Time: 6486.966 ms (+12.9% comparing with initial query)

-- 1.3 add pg_trgm extension with 2 separate columns
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX idx_first_name_trgm ON account_data.accounts USING GIN (LOWER(first_name) account_data.gin_trgm_ops);
CREATE INDEX idx_last_name_trgm ON account_data.accounts USING GIN (LOWER(last_name) account_data.gin_trgm_ops);
-- Execution Time: 6626.045 ms (+10.9% comparing with initial query)

-- 1.4 pg_trgm with 1 separate column
ALTER TABLE account_data.accounts
    ADD COLUMN fullname TEXT
        GENERATED ALWAYS AS (LOWER(first_name || ' ' || last_name)) STORED;

CREATE INDEX idx_accounts_full_name_trgm ON account_data.accounts USING GIN (LOWER(fullname) account_data.gin_trgm_ops);

EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts a
WHERE LOWER(a.fullname) LIKE '%egor%';
-- Execution Time: 4938.098 ms (+33.7% comparing with initial query)

-- in production using key set pagination (ajax requests)
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts a
WHERE LOWER(a.fullname) LIKE '%egor%'
  AND id > 12345
ORDER BY id
LIMIT 100;
-- Execution Time: 3.362 ms
