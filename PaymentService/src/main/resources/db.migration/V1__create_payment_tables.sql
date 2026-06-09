CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT,
    customer_id BIGINT,
    amount NUMERIC(19,2),
    status VARCHAR(50),
    created_at TIMESTAMP
);