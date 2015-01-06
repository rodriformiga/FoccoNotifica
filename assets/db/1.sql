CREATE TABLE tmensagens(
  id         integer primary key autoincrement
, titulo     varchar(200) not null
, mensagem   varchar(500) not null
, status     varchar(1) not null
, resposta   varchar(500)
);