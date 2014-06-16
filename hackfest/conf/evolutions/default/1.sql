# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table evento (
  id                        bigint not null,
  titulo                    varchar(255),
  descricao                 varchar(255),
  date                      varchar(255),
  constraint pk_evento primary key (id))
;

create table tema (
  tema                      varchar(255) not null,
  constraint pk_tema primary key (tema))
;


create table evento_tema (
  evento_id                      bigint not null,
  tema_tema                      varchar(255) not null,
  constraint pk_evento_tema primary key (evento_id, tema_tema))
;

create table tema_evento (
  tema_tema                      varchar(255) not null,
  evento_id                      bigint not null,
  constraint pk_tema_evento primary key (tema_tema, evento_id))
;
create sequence evento_seq;

create sequence tema_seq;




alter table evento_tema add constraint fk_evento_tema_evento_01 foreign key (evento_id) references evento (id) on delete restrict on update restrict;

alter table evento_tema add constraint fk_evento_tema_tema_02 foreign key (tema_tema) references tema (tema) on delete restrict on update restrict;

alter table tema_evento add constraint fk_tema_evento_tema_01 foreign key (tema_tema) references tema (tema) on delete restrict on update restrict;

alter table tema_evento add constraint fk_tema_evento_evento_02 foreign key (evento_id) references evento (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists evento;

drop table if exists evento_tema;

drop table if exists tema;

drop table if exists tema_evento;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists evento_seq;

drop sequence if exists tema_seq;

