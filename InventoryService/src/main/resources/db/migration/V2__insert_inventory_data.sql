INSERT INTO product_inventory
(product_id, available_quantity, reserved_quantity)
VALUES
(1, 100, 0),
(2, 50, 0),
(3, 25, 0)
ON CONFLICT (product_id) DO NOTHING;