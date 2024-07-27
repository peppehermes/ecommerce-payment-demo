DELETE FROM payment;

INSERT INTO payment ("timestamp", sender_id, receiver_id, amount) VALUES('2022-01-01 12:00:00', 10, 70, 100.00);
INSERT INTO payment ("timestamp", sender_id, receiver_id, amount) VALUES('2022-01-02 12:00:00', 10, 80, 50.00);
INSERT INTO payment ("timestamp", sender_id, receiver_id, amount) VALUES('2022-01-03 12:00:00', 20, 30, 75.00);
INSERT INTO payment ("timestamp", sender_id, receiver_id, amount) VALUES('2022-01-04 12:00:00', 40, 20, 25.00);
INSERT INTO payment ("timestamp", sender_id, receiver_id, amount) VALUES('2022-01-05 12:00:00', 20, 50, 15.00);