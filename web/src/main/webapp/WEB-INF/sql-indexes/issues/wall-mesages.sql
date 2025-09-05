explain analyze
insert into message_data.personal_wall_messages
    (account_author_id, account_receiver_id, message_text)
values (25000001, 25000001, 'how do you do?');
-------------------------------------------------------------------------
/*
    Planning Time : 0.078 ms
    Execution Time : 0.204 ms
    (4 rows)
*/

explain analyze
select count(*)
from friend_data.friendship
where id_1 = 25000001
   or id_2 = 25000001;
---------------------------------------------------------------------
/*
QUERY PLAN
   ->  Gather  (cost=1250266.99..1250267.20 rows=2 width=8)
	   (actual time=8662.043..8762.603 rows=3 loops=1)
         Workers Planned: 2
         Workers Launched: 2
         ->  Partial Aggregate  (cost=1249266.99..1249267.00 rows=1 width=8)
	         (actual time=8565.520..8565.724 rows=1 loops=3)
               ->  Parallel Seq Scan on friendship
	               (cost=0.00..1249266.35 rows=256 width=0)
	               (actual time=5741.245..8563.035 rows=2 loops=3)
                     Filter: ((id_1 = 25000001) OR (id_2 = 25000001))
                     Rows Removed by Filter: 32996816
 Planning Time: 0.672 ms
 Execution Time: 8766.861 ms
(14 rows)
 */
---------------------------------------------------------------------
explain analyze
select count(*)
from friend_data.friendship
where id_1 = 25000001
   or id_2 = 25000001;
---------------------------------------------------------------------
/*
QUERY PLAN
 Aggregate  (cost=593.97..593.98 rows=1 width=8) (actual time=0.078..0.081 rows=1 loops=1)
   ->  Bitmap Heap Scan on friendship  (cost=10.31..593.61 rows=147 width=0) (actual time=0.068..0.070 rows=0 loops=1)
         Recheck Cond: ((id_1 = 25000001) OR (id_2 = 25000001))
         ->  BitmapOr  (cost=10.31..10.31 rows=147 width=0) (actual time=0.054..0.056 rows=0 loops=1)
               ->  Bitmap Index Scan on friendship_id_1_status_id_2_idx  (cost=0.00..5.10 rows=71 width=0) (actual time=0.039..0.039 rows=0 loops=1)
                     Index Cond: (id_1 = 25000001)
               ->  Bitmap Index Scan on friendship_id_2_status_id_1_idx  (cost=0.00..5.14 rows=76 width=0) (actual time=0.013..0.014 rows=0 loops=1)
                     Index Cond: (id_2 = 25000001)
 Planning Time: 0.290 ms
 Execution Time: 0.319 ms
(10 rows)
 */