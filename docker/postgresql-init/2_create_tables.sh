
#!/bin/bash
psql -U root -d test_db << "EOSQL"

create schema ec AUTHORIZATION root;
create schema experiment AUTHORIZATION root;

create table public.users
(
  id         bigint       not null,
  account    varchar(255) not null,
  given_name  varchar(255) not null,
  family_name varchar(255) not null,
  created_at timestamp with time zone    not null,
  primary key (id)
);

create table public.roles
(
  id   bigint       not null,
  name varchar(255) not null,
  primary key (id)
);

create table public.permissions
(
  id   bigint       not null,
  name varchar(255) not null,
  primary key (id)
);

create table public.user_roles
(
  user_id bigint not null,
  role_id bigint not null,
  primary key (user_id, role_id),
  foreign key (user_id) references public.users (id),
  foreign key (role_id) references public.roles (id)
);

create table public.role_permissions
(
  role_id       bigint not null,
  permission_id bigint not null,
  primary key (role_id, permission_id),
  foreign key (role_id) references public.roles (id),
  foreign key (permission_id) references public.permissions (id)
);

create table public.issues
(
  id          bigint       not null,
  user_id     bigint       not null,
  subject     varchar(255) not null,
  description varchar(255) not null,
  created_at  timestamp with time zone    not null,
  primary key (id),
  foreign key (user_id) references public.users (id)
);



create table ec.customers
(
  id         bigint       not null,
  given_name  varchar(255) not null,
  family_name varchar(255) not null,
  email varchar(255) not null,
  created_at timestamp with time zone    not null,
  primary key (id),
  unique (email)
);

INSERT INTO ec.customers (id, given_name, family_name, email, created_at) VALUES (1, 'Nobita', 'Nobi', 'nobita@example.com', '2019-03-18 12:24:53.632000');
INSERT INTO ec.customers (id, given_name, family_name, email, created_at) VALUES (2, 'Okometubu', 'Maruyama', 'fu-min@example.com', '2019-03-18 12:25:53.181000');
INSERT INTO ec.customers (id, given_name, family_name, email, created_at) VALUES (3, 'Light', 'Yagami', 'light@example.com', '2019-03-18 12:27:01.873000');


create table ec.products
(
  id   bigint       not null,
  name varchar(255) not null,
  sales_start_date timestamp with time zone not null,
  sales_end_date timestamp with time zone,
  primary key (id)
);

INSERT INTO ec.products (id, name, sales_start_date, sales_end_date) VALUES (1, 'note', '2019-03-17 13:42:24.154000', null);
INSERT INTO ec.products (id, name, sales_start_date, sales_end_date) VALUES (2, 'pen', '2017-03-17 13:42:51.066000', '2018-03-18 13:43:02.044000');
INSERT INTO ec.products (id, name, sales_start_date, sales_end_date) VALUES (3, 'pen', '2019-03-18 13:45:16.257000', null);


create table ec.customer_favorite_products
(
  customer_id bigint not null,
  products_id bigint not null,
  comment varchar(255),
  primary key (customer_id, products_id),
  foreign key (customer_id) references ec.customers (id),
  foreign key (products_id) references ec.products (id)
);

INSERT INTO ec.customer_favorite_products (customer_id, products_id, comment) VALUES (1, 1, 'Good');


create table ec.orders
(
  id   bigint       not null,
  customer_id bigint not null,
  total bigint not null,
  primary key (id),
  foreign key (customer_id) references ec.customers (id)
);

INSERT INTO ec.orders (id, customer_id, total) VALUES (1, 1, 200);
INSERT INTO ec.orders (id, customer_id, total) VALUES (2, 2, 300);
INSERT INTO ec.orders (id, customer_id, total) VALUES (3, 1, 100);

create table ec.order_details
(
  id   bigint       not null,
  order_id bigint not null,
  product_id bigint not null,
  price int not null,
  quantity int not null,
  subtotal int not null,
  primary key (id),
  foreign key (order_id) references ec.orders (id),
  foreign key (product_id) references ec.products (id)
);

INSERT INTO ec.order_details (id, order_id, product_id, price, quantity, subtotal) VALUES (1, 1, 1, 100, 2, 200);
INSERT INTO ec.order_details (id, order_id, product_id, price, quantity, subtotal) VALUES (2, 2, 1, 100, 1, 100);
INSERT INTO ec.order_details (id, order_id, product_id, price, quantity, subtotal) VALUES (3, 2, 2, 100, 2, 200);
INSERT INTO ec.order_details (id, order_id, product_id, price, quantity, subtotal) VALUES (4, 3, 3, 100, 1, 100);

create table experiment.num_types
(
  bigserial_c   bigserial        not null,
  serial_c      serial           not null,
  smallserial_c smallserial      not null,
  smallint_c    smallint         not null,
  integer_c     integer          not null,
  bigint_c      bigint           not null,
  decimal_c     numeric          not null,
  numeric_c     numeric          not null,
  real_c        real             not null,
  double_c      double precision not null
);

alter table experiment.num_types
  owner to root;

INSERT INTO experiment.num_types (bigserial_c, serial_c, smallserial_c, smallint_c, integer_c, bigint_c, decimal_c, numeric_c, real_c, double_c) VALUES (1, 1, 1, 1, 2, 3, 4, 5, 6, 7);


create table experiment.str_types
(
  varchar_c   varchar      not null,
  varchar_n_c varchar(128) not null,
  char_c      char         not null,
  char_n_c    char(4)      not null,
  text_c      text         not null
);

alter table experiment.str_types
  owner to root;

INSERT INTO experiment.str_types (varchar_c, varchar_n_c, char_c, char_n_c, text_c) VALUES ('v', 'vn', 'c', 'cn  ', 't');


create table experiment.date_types
(
  timestamp_c    timestamp                not null,
  timestamp_tz_c timestamp with time zone not null,
  date_c         date                     not null,
  time_c         time                     not null,
  time_tz_c      time with time zone      not null,
  interval_c     interval                 not null
);

alter table experiment.date_types
  owner to root;

INSERT INTO experiment.date_types (timestamp_c, timestamp_tz_c, date_c, time_c, time_tz_c, interval_c) VALUES ('2019-03-15 11:53:17.901000', '2019-03-15 15:53:20.724000 +05:00', '2019-03-15', '21:00:00', '21:00:00.000000 +05:00', '0 years 0 mons 0 days 0 hours 0 mins 2.00 secs');

CREATE TYPE mood AS ENUM ('sad', 'ok', 'happy');

create table experiment.other_types
(
  money_c    money  not null,
  bytea_c    bytea  not null,
  enum_c     mood   not null,
  bit_c      bit(3) not null,
  xml_c      xml,
  json_c     json,
  jsonb_c    jsonb,
  tsquery_c  tsquery,
  tsvector_c tsvector,
  uuid_c     uuid
);

alter table experiment.other_types
  owner to root;

INSERT INTO experiment.other_types (money_c, bytea_c, enum_c, bit_c, xml_c, json_c, jsonb_c, tsquery_c, tsvector_c, uuid_c) VALUES ('$12.00', '1111', 'happy', '101', '<book><title>Manual</title><chapter>test</chapter></book>', '{"bar": "baz", "balance": 7.77, "active": false}', '{"bar": "baz", "active": false, "balance": 7.77}', 'fat& rat', 'a fat cat sat on a mat and ate a fat rat', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11');

EOSQL