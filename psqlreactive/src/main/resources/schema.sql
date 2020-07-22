-- CREATE ROLE orders WITH LOGIN PASSWORD '0rd3rz' ;
-- ALTER ROLE orders CREATEDB ;
-- CREATE DATABASE orders;

drop table customer;

create table customer (
    id serial not null primary key,
    email varchar not null
);
