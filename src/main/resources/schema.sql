-- Using serial as primary key in payment and account for auto increment and for simplicity
-- In production, we should use UUID for primary key

CREATE TABLE IF NOT EXISTS payment (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sender_id INTEGER NOT NULL,
    receiver_id INTEGER NOT NULL,
    amount DECIMAL(19, 2) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_sender_id ON payment(sender_id);
CREATE INDEX IF NOT EXISTS idx_receiver_id ON payment(receiver_id);