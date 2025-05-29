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

-- 2. optimizing 'outgoing requests' logic
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts a
         JOIN (SELECT accepter_id AS acc_id
               FROM friend_data.friendship
               WHERE status = false
                 AND requester_id = 1) f ON a.id = f.acc_id;

CREATE INDEX ON friend_data.friendship (requester_id, status, accepter_id);

/*
  | before | 29321.349 ms  |
  | after  | 127 ms        |
  | boost  | 230.8 times   |
 */

-- 3. optimizing 'incoming requests' logic
EXPLAIN (ANALYZE)
SELECT *
FROM account_data.accounts a
         JOIN (SELECT requester_id AS req_id
               FROM friend_data.friendship
               WHERE status = false
                 AND accepter_id = 1) f ON a.id = f.req_id;

CREATE INDEX ON friend_data.friendship (accepter_id, status, requester_id);
/*
  | before | 6886.111 ms  |
  | after  | 1910.262 ms  |
  | boost  | 3.6  times   |
 */