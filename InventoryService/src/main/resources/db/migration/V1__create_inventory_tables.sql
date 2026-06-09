CREATE TABLE IF NOT EXISTS product_inventory (
    product_id BIGINT PRIMARY KEY,
    available_quantity INTEGER,
    reserved_quantity INTEGER
);

CREATE TABLE IF NOT EXISTS inventory_reservations (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INTEGER
);