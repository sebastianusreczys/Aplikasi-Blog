CREATE TABLE s_permission (
  id               VARCHAR(255) NOT NULL,
  permission_label VARCHAR(255) NOT NULL,
  permission_value VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (permission_value)
);

CREATE TABLE s_role (
    id          VARCHAR(255) NOT NULL,
    description VARCHAR(255) DEFAULT NULL,
    name        VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE s_role_permission (
   id_role       VARCHAR(255) NOT NULL,
   id_permission VARCHAR(255) NOT NULL,
   PRIMARY KEY (id_role, id_permission),
   FOREIGN KEY (id_permission) REFERENCES s_permission (id),
   FOREIGN KEY (id_role) REFERENCES s_role (id)
);

CREATE TABLE s_user (
    id       VARCHAR(36),
    username VARCHAR(255) NOT NULL,
    active   BOOLEAN      NOT NULL,
    id_role  VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username),
    FOREIGN KEY (id_role) REFERENCES s_role (id)
);

create table s_user_password (
     id varchar(36),
     id_user varchar(36) not null,
     password varchar(255) not null,
     primary key (id),
     foreign key (id_user) references s_user (id)
);

create table authors(
    id varchar(36),
    id_user varchar(36) not null,
    fullname varchar(255) not null ,
    email varchar(255) not null ,
    picture varchar(255),
    primary key (id),
    foreign key (id_user) references s_user (id)
);

create table categories(
   id varchar(36),
   name varchar(100) not null ,
   slug varchar(100) not null ,
   picture varchar(100),
   primary key (id)
);

create table posts(
  id varchar(36),
  id_category varchar(36) not null,
  id_author varchar(36) not null ,
  title varchar(255) not null unique ,
  slug varchar(255) not null ,
  quote varchar(255) not null ,
  body text not null ,
  photo varchar(100),
  created_at DATE not null ,
  primary key (id),
  foreign key (id_category) references categories(id),
  foreign key (id_author) references authors(id)
);

