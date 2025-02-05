-- 1. optimizing 'login' logic

-- 1.1 getting account by provided email
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts
WHERE email = 'www@gmail.com';

CREATE INDEX ON account_data.accounts (email);
/*
  | before | 1548.653 ms    |
  | after  | 0.113    ms    |
  | boost  | 13 704.9 times |
 */

-- 1.2 getting account phone numbers
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.account_phones
WHERE account_id = 100000001
  AND phone_type = 'PERSONAL';

CREATE INDEX ON account_data.account_phones (account_id, phone_type);
/*
  | before | 4967.146 ms    |
  | after  | 0.067    ms    |
  | boost  | 74 136.5 times |
 */

/*
    Total improvements 'login logic':
    | before | 2.52 s.     |
    | after  | 0.281 s.    |
    | boost  | 8.9 times   |
 */