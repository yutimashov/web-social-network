-- 1. optimizing 'login' logic
-- 1.1 getting account by provided email
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts
WHERE email = 'www@gmail.com';

CREATE INDEX ON account_data.accounts (email);
/*
 | before | 6308.520 ms |
 | after  | 0.101    ms |
 _______________________
 index creation time: 2 min 58 secs
 boost:               62 460.6 times
 */

-- 2. optimizing 'getting account info after login' logic
-- 2.1 getting account phone numbers
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.account_phones
WHERE account_id = 100000001
  AND phone_type = 'PERSONAL';

CREATE INDEX ON account_data.account_phones (id, phone_type);
/*
 | before | 3398.844 ms |
 | after  | 0.026    ms |
 _______________________
 index creation time: 5 min 12 secs
 boost:               130 724.8 times
 */