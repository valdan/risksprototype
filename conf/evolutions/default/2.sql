# --- Sample dataset

# --- !Ups

insert into matrixunit (id,name) values (  1,'Matrixunit 1');

insert into risk (id,name,description,introduced,matrixunit_id) values (  1,'Risk 1','Risk 1 desc',null,1);

insert into account (id,email,name,password) values (1,'a@x.com','a', 'a');
insert into account (id,email,name,password) values (2,'b@x.com','b', 'b');
insert into account (id,email,name,password) values (3,'c@x.com','c', 'c');
insert into account (id,email,name,password) values (4,'d@x.com','d', 'd');

# --- !Downs

delete from matrixunit;
delete from risk;
delete from account;
