-- batch inserting birthdays for accounts
CREATE OR REPLACE FUNCTION random_birth_date() RETURNS DATE AS
$$
BEGIN
    RETURN '1900-01-01'::date +
           FLOOR(RANDOM() * (('2024-01-01'::date - '1900-01-01'::date)::integer))::integer;
END;
$$ LANGUAGE plpgsql;

DO
$$
    DECLARE
        batch_size INT := 100000;
        total_rows INT := 25000000;
        iterations INT;
        start_id   INT;
        end_id     INT;
    BEGIN
        iterations := CEIL(total_rows / batch_size);
        RAISE NOTICE 'Batch size: %, Iterations: %', batch_size, iterations;
        FOR i IN 1..iterations
            LOOP
                start_id := (i - 1) * batch_size + 1;
                end_id := LEAST(i * batch_size, total_rows);
                UPDATE account_data.accounts
                SET birth_date = random_birth_date()
                WHERE id BETWEEN start_id AND end_id;
                RAISE NOTICE 'Updated rows with IDs from % to % (batch % of %)', start_id, end_id, i, iterations;
            END LOOP;
        RAISE NOTICE 'All records have been successfully updated!';
    END
$$;