DROP SCHEMA restomanage CASCADE;
CREATE SCHEMA restomanage;
CREATE EXTENSION pgcrypto;

/*create type restomanage.userType as  enum ('admin', 'user');*/
create table restomanage.users (
    id serial primary key,
    name varchar(255) not null check (name <> ''),
    password char(60) not null check (password <>''),
    user_type char(3) not null,
    unique (name)
);

create table restomanage.companies (
	id serial primary key,
	name varchar(200)
);

create table restomanage.affiliates (
	id serial primary key,
	id_company int references restomanage.companies(id),
	name varchar(200),
    unique (id_company, name)
);

create table restomanage.point_of_sales (
    id serial primary key,
    id_affiliate int references restomanage.affiliates(id),
    date_start timestamp not null
);

create table restomanage.tables (
    id serial primary key,
    name varchar(200),
    id_affiliate int references restomanage.affiliates(id),
    unique (name, id_affiliate)
);

create table restomanage.product_categories (
    id serial primary key,
    name varchar(200) not null check (name <> ''),
    id_affiliate int references restomanage.affiliates(id),
    unique (name, id_affiliate)
);

create table restomanage.products (
    id serial primary key,
    name varchar(200) not null check (name <> ''),
    price double precision not null check (price >= 0),
    id_category int references restomanage.product_categories(id),
    id_affiliate int references restomanage.affiliates(id)
);

create table restomanage.orders (
    id_order serial primary key,
    date_order timestamp not null,
    id_point_of_sale int references restomanage.point_of_sales(id),
    id_user int references restomanage.users(id),
    id_table int references restomanage.tables(id),
    date_payment timestamp null check(date_payment >= date_order)
);

create table restomanage.order_details (
    id serial primary key,
    id_order int references restomanage.orders(id_order),
    id_product int references restomanage.products(id),
    quantity int not null check (quantity > 0),
    unit_price double precision not null check (unit_price >= 0),
    amount double precision not null check (amount >= 0),
    unique (id_order, id_product)
);

create table restomanage.open_cashiers (
    id serial primary key,
    date_open timestamp not null,
    fund double precision not null check (fund >= 0),
    id_point_of_sale int references restomanage.point_of_sales(id),
    id_user int references restomanage.users(id),
    date_closed timestamp null check (date_closed >= date_open)
);

/* Statistics */
create view restomanage.stat_sell_amount_by_prod as select restomanage.order_details.id_product, sum(restomanage.order_details.amount) as amount from restomanage.order_details group by restomanage.order_details.id_product;
create view restomanage.stat_sell_amount_by_prod_full as select restomanage.products.name, restomanage.products.id, restomanage.stat_sell_amount_by_prod.amount from restomanage.stat_sell_amount_by_prod join restomanage.products on restomanage.products.id = restomanage.stat_sell_amount_by_prod.id_product order by restomanage.stat_sell_amount_by_prod.amount desc;

create view restomanage.stat_sell_count_by_prod as select restomanage.order_details.id_product, count(*) as numbers from restomanage.order_details group by restomanage.order_details.id_product;
create view restomanage.stat_sell_count_by_prod_full as select restomanage.products.name, restomanage.products.id, restomanage.stat_sell_count_by_prod.numbers from restomanage.stat_sell_count_by_prod join restomanage.products on restomanage.products.id = restomanage.stat_sell_count_by_prod.id_product order by restomanage.stat_sell_count_by_prod.numbers desc;

/* INITIAL DATA */
insert into restomanage.users (name, password, user_type) values ('admin', crypt('FPO_12p)([]', gen_salt('bf')), 'adm');
insert into restomanage.users (name, password, user_type) values ('user', crypt('FPO_12p)([]', gen_salt('bf')), 'usr');

insert into restomanage.companies (name) values ('enxaneta');

insert into restomanage.affiliates (id_company,  name) values (1, 'majunga be');

insert into restomanage.product_categories (name, id_affiliate) values ('crêpe', 1);
insert into restomanage.product_categories (name, id_affiliate) values ('burger', 1);

insert into restomanage.products (name, price, id_category, id_affiliate) values ('crêpe sucré', 15000, 1, 1);
insert into restomanage.products (name, price, id_category, id_affiliate) values ('crêpe salé', 25000, 1, 1);
insert into restomanage.products (name, price, id_category, id_affiliate) values ('crêpe poulet', 30000, 1, 1);
insert into restomanage.products (name, price, id_category, id_affiliate) values ('Burger fromage poulet', 50000, 2, 1);

insert into restomanage.tables (name, id_affiliate) values ('table 1', 1);

insert into restomanage.orders (date_order, id_table, date_payment) values ('2021-12-01 08:00', 1, '2021-12-01 08:01');

insert into restomanage.order_details (id_order, id_product, quantity, unit_price, amount) values (1, 1, 5, 15000, 75000), (1, 2, 5, 25000, 125000);

