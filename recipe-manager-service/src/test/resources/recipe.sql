insert into user_profile (user_id, passcode)
values ('emailid@domain.com','Qwerty123$');
insert into user_profile (user_id, passcode)
values ('emailid1@domain.com','Qwerty1234$');
insert into ingredient (ingredient_name)
values ('tomato');
insert into scale_unit (unit_value)
values ('kg');
insert into recipe (recipe_id, cooking_instructions, create_date_time, dish_type, no_of_people_suitable, recipe_name,
                    update_date_time, user_id)
values ('001','cook the food','2022-05-18 15:46:19',false,10,'chicken',null,'emailid@domain.com');
insert into recipe (recipe_id, cooking_instructions, create_date_time, dish_type, no_of_people_suitable, recipe_name,
                    update_date_time, user_id)
values ('002','cook the food','2022-05-16 15:46:19',false,10,'bread',null,'emailid1@domain.com');
insert into recipe_ingrediant_quantity (ingredient_name, recipe_id, quantity, unit_value)
values ('tomato','001',20.0,'kg');
insert into recipe_ingrediant_quantity (ingredient_name, recipe_id, quantity, unit_value)
values ('tomato','002',20.0,'kg');