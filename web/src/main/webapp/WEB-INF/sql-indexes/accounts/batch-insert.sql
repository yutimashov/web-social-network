-- function for generating account first name
CREATE OR REPLACE FUNCTION random_first_name() RETURNS TEXT AS
$$
DECLARE
    names TEXT[] := ARRAY ['Ivan', 'Petr', 'Dmitriy', 'Anton', 'Egor', 'Vasiliy', 'Yuriy', 'Sergey', 'Vitaliy', 'Artur',
        'Semen', 'Mihail', 'Eduard', 'Aleksandr', 'Nikolay', 'Pavel', 'Aleksey', 'Anna', 'Elena', 'Olga', 'Tatiana',
        'Irina', 'Ekaterina', 'Maria', 'Lisa', 'Yulia', 'Stanislav', 'Alena', 'Denis'];
BEGIN
    RETURN names[1 + floor(random() * array_length(names, 1))];
END;
$$ LANGUAGE plpgsql;

-- function for generating account last name
CREATE OR REPLACE FUNCTION random_last_name() RETURNS TEXT AS
$$
DECLARE
    names TEXT[] := ARRAY ['Grib', 'Stanislavchenko', 'Philin', 'Semenko', 'Meher', 'Gonchar', 'Nasteruk', 'Medved',
        'Nikonenko', 'Pavelchitelli', 'German', 'Leshenko', 'Petrenko', 'Zinolenko', 'Fedorenko', 'Onishenko',
        'Plovets', 'Yukki', 'Yabloko', 'Kalinichenko', 'Grom', 'Prohor', 'Telpuk', 'Blok', 'Rimchenko', 'Mirobor',
        'Kalenko', 'Semenko'];
BEGIN
    RETURN names[1 + floor(random() * array_length(names, 1))];
END;
$$ LANGUAGE plpgsql;

-- function for generating account phone number
CREATE OR REPLACE FUNCTION generate_phone_number() RETURNS TEXT AS
$$
DECLARE
    prefix TEXT;
    number TEXT;
BEGIN
    prefix := '+375' || (ARRAY ['25', '29', '33', '44', '17'])[1 + floor(random() * 5)];
    number := (floor(random() * 9000000) + 1000000)::TEXT;
    RETURN prefix || number;
END;
$$ LANGUAGE plpgsql;

-- function for inserting data into tables
DO
$$
    DECLARE
        batch_size INT := 100000;
        total_rows INT := 100000000;
        iterations INT := total_rows / batch_size;
    BEGIN
        FOR i IN 1..iterations
            LOOP
                BEGIN
                    -- insert data into accounts tables and returning inserted email and id
                    WITH inserted_accounts AS (
                        INSERT INTO account_data.accounts (first_name, last_name, email, birth_date,
                                                           skype, role_type, registration_date)
                            SELECT random_first_name()                                       AS first_name,
                                   random_last_name()                                        AS last_name,
                                   LOWER(random_first_name() || '.' || random_last_name() || '.' || s ||
                                         '@gmail.com')                                       AS email,
                                   DATE '1960-01-01' +
                                   (random() * (DATE '2010-12-31' - DATE '1960-01-01'))::int AS birth_date,
                                   LOWER(random_first_name() || '.' || random_last_name())   AS skype,
                                   CASE WHEN random() < 0.2 THEN 'ADMIN' ELSE 'REGULAR' END  AS role_type,
                                   CURRENT_TIMESTAMP - (random() * INTERVAL '365 days')      AS registration_date
                            FROM generate_series(1, batch_size) AS s
                            RETURNING id, email),
                         -- insert data into password table
                         inserted_passwords AS (
                             INSERT INTO account_data.account_passwords (id, hash_password)
                                 SELECT id, md5(email)
                                 FROM inserted_accounts
                                 RETURNING id)
                         -- insert data into phones table
                    INSERT
                    INTO account_data.account_phones (account_id, phone_type, phone_number)
                    SELECT id,
                           CASE WHEN random() < 0.5 THEN 'PERSONAL' ELSE 'WORKING' END AS phone_type,
                           generate_phone_number()                                     AS phone_number
                    FROM inserted_passwords;

                    -- printing progress of inserting batches
                    RAISE NOTICE 'Inserted % rows (batch % of %)', batch_size, i, iterations;
                END;
            END LOOP;
    END
$$;