# --- First database schema

# --- !Ups

create table account (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (email)
);

create table matrixunit (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_matrixunit primary key (id))
;

create table risk (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  introduced                timestamp,
  matrixunit_id             bigint,
  constraint pk_risk primary key (id))
;

create sequence matrixunit_seq start with 1000;

create sequence risk_seq start with 1000;

alter table risk add constraint fk_risk_matrixunit_1 foreign key (matrixunit_id) references matrixunit (id) on delete restrict on update restrict;
create index ix_risk_matrixunit_1 on risk (matrixunit_id);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists matrixunit;

drop table if exists risk;

drop table if exists account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists matrixunit_seq;

drop sequence if exists risk_seq;

