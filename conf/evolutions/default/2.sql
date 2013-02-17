# --- Sample dataset

# --- !Ups

insert into matrixunit (id,name) values (  1,'Company A');
insert into matrixunit (id,name) values (  2,'Company B');
insert into matrixunit (id,name) values (  3,'Company C');
insert into matrixunit (id,name) values (  4,'Company D');
insert into matrixunit (id,name) values (  5,'Company E');

insert into role (id,name) values (  1,'ADMIN');
insert into role (id,name) values (  2,'CREATE');
insert into role (id,name) values (  3,'VALIDATE');

insert into risk (id,name,description,introduced,matrixunit_id) values (  1,'Risk 1','Risk 1 desc',null,1);

insert into account (id,email,name,password,role_id) values (1,'a@x.com','a', 'a', 1);
insert into account (id,email,name,password,role_id) values (2,'b@x.com','b', 'b', 2);
insert into account (id,email,name,password,role_id) values (3,'c@x.com','c', 'c', 2);
insert into account (id,email,name,password,role_id) values (4,'d@x.com','d', 'd', 3);

# --- !Downs

delete from matrixunit;
delete from risk;
delete from account;
