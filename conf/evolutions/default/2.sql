# --- Sample dataset

# --- !Ups

insert into matrixunit (id,name) values (  1,'Matrixunit 1');

insert into risk (id,name,description,introduced,matrixunit_id) values (  1,'Risk 1','Risk 1 desc',null,1);

insert into account (email,name,password) values (  'a@b.com','a', 'a');
insert into account (email,name,password) values (  'b@b.com','b', 'b');

# --- !Downs

delete from matrixunit;
delete from risk;
delete from account;
