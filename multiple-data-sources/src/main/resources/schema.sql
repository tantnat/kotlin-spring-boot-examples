create schema if not exists first;
create schema if not exists second;

create table first.user
(
    id         identity,
    firstName  varchar2,
    secondName varchar2
);

create table second.user
(
    id         identity,
    firstName  varchar2,
    secondName varchar2,
    date       date
);

insert into FIRST.USER (id, firstName, secondName) values (null, 'Alex', 'Alexeev');
insert into FIRST.USER (id, firstName, secondName) values (null, 'Andrey', 'Andreev');
insert into FIRST.USER (id, firstName, secondName) values (null, 'Anna', 'Ivanova');
insert into FIRST.USER (id, firstName, secondName) values (null, 'Emili', 'Lon');
insert into FIRST.USER (id, firstName, secondName) values (null, 'Petr', 'Petrov');