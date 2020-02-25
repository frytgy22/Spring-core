  create table if not exists groups (
     id int primary key auto_increment,
     name varchar(50) not null unique);

  create table if not exists students (
      id int primary key auto_increment,
      first_name varchar(50) not null,
      last_name varchar(50) not null,
      age int not null,
      group_id int not null,
      FOREIGN KEY(group_id) REFERENCES groups (id));