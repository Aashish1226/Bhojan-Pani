INSERT INTO roles (name, description, create_date, update_date, is_active, is_default)
VALUES ('ADMIN', 'Administrator with full access', NOW(), NOW(), TRUE, TRUE),
       ('USER', 'Regular user with limited access', NOW(), NOW(), TRUE, TRUE);

INSERT INTO permissions (name, description, create_date, update_date, is_active, is_default)
VALUES ('CATEGORY_CREATE', 'Create categories', NOW(), NOW(), TRUE, TRUE),
       ('CATEGORY_UPDATE', 'Update categories', NOW(), NOW(), TRUE, TRUE),
       ('CATEGORY_DELETE', 'Delete categories', NOW(), NOW(), TRUE, TRUE),
       ('CATEGORY_GET', 'Get categories', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_CREATE', 'Create food items', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_UPDATE', 'Update food items', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_DELETE', 'Delete food items', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_GET', 'Get food items', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_VARIANT_CREATE', 'Create food variants', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_VARIANT_UPDATE', 'Update food variants', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_VARIANT_DELETE', 'Delete food variants', NOW(), NOW(), TRUE, TRUE),
       ('ROLE_GET', 'Get roles', NOW(), NOW(), TRUE, TRUE),
       ('ROLE_UPDATE', 'Update roles', NOW(), NOW(), TRUE, TRUE),
       ('ROLE_DELETE', 'Delete roles', NOW(), NOW(), TRUE, TRUE),
       ('CART_ADD', 'Add items to cart', NOW(), NOW(), TRUE, TRUE),
       ('CART_UPDATE', 'Update cart items', NOW(), NOW(), TRUE, TRUE),
       ('CART_DELETE', 'Delete cart items', NOW(), NOW(), TRUE, TRUE),
       ('CART_GET', 'Get cart items', NOW(), NOW(), TRUE, TRUE),
       ('FOOD_GET_USER', 'Get food items (user access)', NOW(), NOW(), TRUE, TRUE);

INSERT INTO role_permissions (role_id, permission_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14);

INSERT INTO role_permissions (role_id, permission_id)
VALUES (2, 15),
       (2, 16),
       (2, 17),
       (2, 18),
       (2, 19);