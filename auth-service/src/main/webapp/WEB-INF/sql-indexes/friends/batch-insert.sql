DO
$$
    DECLARE
        batch_size   INT := 100000;
        total_users  INT := 20000000;
        friend_count INT := 5;
        user_id      INT := 11;
        friend_id    INT;
        status       BOOLEAN;
        requester_id INT;
        accepter_id  INT;
        progress     INT := 0;
    BEGIN
        FOR user_id IN 1..total_users
            LOOP
                friend_id := user_id + 1;
                FOR i IN 1..friend_count
                    LOOP
                        status := (i % 2 = 0);
                        IF i % 2 = 0 THEN
                            requester_id := user_id;
                            accepter_id := friend_id;
                        ELSE
                            requester_id := friend_id;
                            accepter_id := user_id;
                        END IF;
                        INSERT INTO friend_data.friendship (id_1, id_2, status, requester_id, accepter_id)
                        VALUES (user_id, friend_id, status, requester_id, accepter_id)
                        ON CONFLICT (id_1, id_2) DO NOTHING;
                        progress := progress + 1;
                        IF progress % batch_size = 0 THEN
                            RAISE NOTICE 'Inserted % rows', progress;
                        END IF;
                        friend_id := friend_id + 1;
                    END LOOP;
            END LOOP;
        RAISE NOTICE 'Finished inserting % rows', progress;
    END
$$;