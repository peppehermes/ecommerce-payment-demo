DELETE FROM payment;

-- SQL script to generate 1 million random INSERT statements
DO
'
DECLARE
    i INT := 0;
    ts TIMESTAMP;
    sender_id INT;
    receiver_id INT;
    amount NUMERIC(10, 2);
BEGIN
    WHILE i < 100000 LOOP
        ts := ''2022-01-01 12:00:00.000''::timestamp + (random() * (NOW() - ''2022-01-01 12:00:00.000'')::interval);
        sender_id := floor(random() * 1000 + 1);
        receiver_id := floor(random() * 1000 + 1);
        amount := round(1000, 2);

        IF sender_id != receiver_id THEN
            EXECUTE format(''INSERT INTO payment ("timestamp", sender_id, receiver_id, amount) VALUES (''''%s'''', %s, %s, %s);'', ts, sender_id, receiver_id, amount);
            i := i + 1;
        END IF;
    END LOOP;
END;
' LANGUAGE PLPGSQL;