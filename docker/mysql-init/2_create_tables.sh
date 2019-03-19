
#!/bin/bash
mysql -h localhost -u root -ppassword -P 3306 test_db1 << "EOSQL"

create table users
(
  id         bigint       not null,
  account    varchar(255) not null,
  last_name  varchar(255) not null,
  first_name varchar(255) not null,
  created_at timestamp    not null,
  primary key (id)
);

create table roles
(
  id   bigint       not null,
  name varchar(255) not null,
  primary key (id)
);

create table permissions
(
  id   bigint       not null,
  name varchar(255) not null,
  primary key (id)
);

create table user_roles
(
  user_id bigint not null,
  role_id bigint not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

create table role_permissions
(
  role_id       bigint not null,
  permission_id bigint not null,
  primary key (role_id, permission_id),
  foreign key (role_id) references roles (id),
  foreign key (permission_id) references permissions (id)
);

create table issues
(
  id          bigint       not null,
  user_id     bigint       not null,
  subject     varchar(255) not null,
  description varchar(255) not null,
  created_at  timestamp    not null,
  primary key (id),
  foreign key (user_id) references users (id)
);


EOSQL