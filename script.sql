DROP SCHEMA restomanage CASCADE;
CREATE SCHEMA restomanage;
CREATE EXTENSION pgcrypto;

create type userType as  enum ('admin', 'user');
create table users (
    id serial primary key,
    name varchar(255) not null check (name <> ''),
    password char(60) not null check (password <>''),
    user_type userType not null,
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

create table restomanage.places (
    id serial primary key,
    name varchar(200),
    id_affiliate int references restomanage.affiliates(id)
);

create table restomanage.product_categories (
    id serial primary key,
    name varchar(200) not null check (name <> ''),
    id_affiliate int references restomanage.affiliates(id)
);

create table restomanage.products (
    id serial primary key,
    name varchar(200) not null check (name <> ''),
    price double precision not null check (price >= 0),
    id_category int references restomanage.product_categories(id),
    id_affiliate int references restomanage.affiliates(id)
);


/* INITIAL DATA */
insert into restomanage.users (name, password, user_type) values ('admin', crypt('FPO_12p)([]', gen_salt('bf')), 'admin');
insert into restomanage.users (name, password, user_type) values ('user', crypt('FPO_12p)([]', gen_salt('bf')), 'user');

insert into restomanage.companies (name) values ('enxaneta');

insert into restomanage.affiliates (id_company,  name) values (1, 'majunga be');

insert into restomanage.product_categories (name, id_affiliate) values ('crêpe', 1);
insert into restomanage.product_categories (name, id_affiliate) values ('burger', 1);

insert into restomanage.products (name, price, id_category, id_affiliate) values ('crêpe sucré', 15000, 1, 1);
insert into restomanage.products (name, price, id_category, id_affiliate) values ('crêpe salé', 25000, 1, 1);
insert into restomanage.products (name, price, id_category, id_affiliate) values ('crêpe poulet', 30000, 1, 1);