drop table if exists flyway_member;

create table flyway_member (
  id bigint auto_increment,
  name varchar(255),
  primary key (id)
);