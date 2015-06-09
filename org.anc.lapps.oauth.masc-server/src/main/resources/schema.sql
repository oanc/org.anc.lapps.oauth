drop table if exists users;
drop table if exists authorities;
drop table if EXISTS user_details;
drop table if exists roles;

create table roles (
  role varchar(256) PRIMARY KEY
);

create table users (
  username varchar(256) PRIMARY KEY,
  password varchar(256),
  enabled boolean
);

create table authorities (
  username varchar(256),
  authority varchar(256),
  CONSTRAINT pk_authorities PRIMARY KEY (username,authority),
  CONSTRAINT fk_authorities_username FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
  CONSTRAINT fk_authorities_role FOREIGN KEY (authority) REFERENCES roles(role) ON DELETE CASCADE
);

create table user_details (
  username VARCHAR(256) PRIMARY KEY,
  email VARCHAR(256),
  dateCreated DATE,
  lastUpdated DATE,
  CONSTRAINT fk_details_username FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

