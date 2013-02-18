# --- Sample dataset

# --- !Ups

--- Companies
insert into matrixunit (id,name) values (  1,'Company A');
insert into matrixunit (id,name) values (  2,'Company B');
insert into matrixunit (id,name) values (  3,'Company C');
insert into matrixunit (id,name) values (  4,'Company D');
insert into matrixunit (id,name) values (  5,'Company E');

--- Roles
insert into role (id,name) values (  1,'ADMIN');
insert into role (id,name) values (  2,'CREATE');
insert into role (id,name) values (  3,'VALIDATE');

--- Users
insert into account (id,email,name,password,role_id) values (1,'daniel.vladescu@gmail.com','Admin', 'admin', 1);
insert into account (id,email,name,password,role_id) values (2,'daniel.vladescu@gmail.com','Creator', 'admin', 2);
insert into account (id,email,name,password,role_id) values (3,'pauldani24@yahoo.com','Validator', 'admin', 3);


--- Risks
insert into risk (id,name,description,introduced,matrixunit_id) values (  1,'Risk 1','Risk 1 desc',null,1);

# --- !Downs

delete from matrixunit;
delete from risk;
delete from account;


