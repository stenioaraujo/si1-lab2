# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table meta (
  id                        bigint not null,
  week_id                   bigint not null,
  objective                 varchar(255),
  priority                  integer,
  complete                  boolean,
  constraint pk_meta primary key (id))
;

create table week (
  id                        bigint not null,
  constraint pk_week primary key (id))
;

create sequence meta_seq;

create sequence week_seq;

alter table meta add constraint fk_meta_week_1 foreign key (week_id) references week (id) on delete restrict on update restrict;
create index ix_meta_week_1 on meta (week_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists meta;

drop table if exists week;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists meta_seq;

drop sequence if exists week_seq;

