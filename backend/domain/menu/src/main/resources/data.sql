INSERT INTO menu.menu_item_ingredients
VALUES (nextval('menu.menu_item_ingredients_seq'), true, 'Cheese', '7c2738f8-7b88-4f77-a906-11882f6c8209',
        '9cbbee7d-b026-469d-903f-3429fd0b4fc2');
INSERT INTO menu.menu_item_ingredients
VALUES (nextval('menu.menu_item_ingredients_seq'), true, 'Bread', '83fce91a-61af-4269-94b3-a8c366b1a810',
        '9cbbee7d-b026-469d-903f-3429fd0b4fc2');
INSERT INTO menu.menu_item_ingredients
VALUES (nextval('menu.menu_item_ingredients_seq'), true, 'Meat', '15992da3-4629-4687-9905-fe220fa9375e',
        '9cbbee7d-b026-469d-903f-3429fd0b4fc2');
INSERT INTO menu.menu_item_ingredients
VALUES (nextval('menu.menu_item_ingredients_seq'), true, 'Tomato', 'bb79f788-9982-4feb-b3a8-ce59a0e96d86',
        '9cbbee7d-b026-469d-903f-3429fd0b4fc2');


INSERT INTO menu.menu
VALUES (nextval('menu.menu_seq'), 'New description', true, 'Main menu', 'd74c022a-c4f9-43e2-9c48-ba21b5b5e956',
        '82001835-3065-4993-85ed-ea70f40948de', '9cbbee7d-b026-469d-903f-3429fd0b4fc2');
INSERT INTO menu.menu
VALUES (nextval('menu.menu_seq'), 'New description2', false, 'Main menu2', 'e197ebd2-9615-4eca-b752-4d7362dfb914',
        '82001835-3065-4993-85ed-ea70f40948de', '9cbbee7d-b026-469d-903f-3429fd0b4fc2');


INSERT INTO menu.menu_items
VALUES (nextval('menu.menu_items_seq'), 'Item(dish) description', 'htttp:/image.com', true, true, true, true, true,
        true, true, true, true, true, true, true, true, 33.33, 'd4e6bef9-4134-423c-a822-c9f440cde210', 'BigBurger',
        '9cbbee7d-b026-469d-903f-3429fd0b4fc2', 51,
        201);

INSERT INTO menu.menu_items
VALUES (nextval('menu.menu_items_seq'), 'Item(dish) description2', 'htttp:/image.com', true, true, true, true, true,
        true, true, true, true, true, true, true, true, 33.33, '20609ca3-4ca6-41fd-98f0-d7f16098fbd6', 'BigBurger',
        '9cbbee7d-b026-469d-903f-3429fd0b4fc2', 101,
        201);


INSERT INTO menu.menu_items
VALUES (nextval('menu.menu_items_seq'), 'Item(dish) description', 'htttp:/image.com', true, true, true, true, true,
        true, true, true, true, true, true, true, true, 33.33, '21325b63-b6a1-423a-8e39-aef84d396e8b', 'BigBurger',
        '9cbbee7d-b026-469d-903f-3429fd0b4fc2', 101,
        null);

INSERT INTO menu.menu_items_to_ingredients
VALUES(151, 101);
INSERT INTO menu.menu_items_to_ingredients
VALUES(151, 51);