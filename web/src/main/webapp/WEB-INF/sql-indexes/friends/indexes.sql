-- 1. optimizing 'all accounts' logic

-- 1.1 getting account's friends
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts a
         JOIN (SELECT id_2 AS friend_id
               FROM friend_data.friendship
               WHERE id_1 = 1
                 AND status = true
               UNION ALL
               SELECT id_1 AS friend_id
               FROM friend_data.friendship
               WHERE id_2 = 1
                 AND status = true) f ON a.id = f.friend_id;

CREATE INDEX ON friend_data.friendship (id_1, status, id_2);
CREATE INDEX ON friend_data.friendship (id_2, status, id_1);

/*
  | before | 45849.952 ms  |
  | after  | 4796.842 ms   |
  | boost  | 9.56 times    |
 */

/*
    Total improvements 'all accounts':
    | before | 13.83 s.     |
    | after  | 8.37 s.    |
    | boost  |  times   |
 */