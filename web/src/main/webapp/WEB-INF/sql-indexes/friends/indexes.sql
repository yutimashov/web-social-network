-- 1. optimizing 'all accounts' logic

-- 1.1 getting account's friends
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts
WHERE id IN (SELECT CASE
                        WHEN f.id_1 = 1 THEN id_2
                        WHEN f.id_2 = 1 THEN id_1
                        END
             FROM friend_data.friendship f
             WHERE (1 IN (f.id_1, f.id_2))
               AND f.status = true);

CREATE INDEX ON friend_data.friendship (id_1, status);
CREATE INDEX ON friend_data.friendship (id_2, status);

/*
  | before | 15758.695 ms    |
  | after  | 429.033   ms    |
  | boost  | 36.7 times      |
 */

/*
    Total improvements 'all accounts':
    | before | 13.83 s.     |
    | after  | 8.37 s.    |
    | boost  |  times   |
 */