-- auto-generated definition
create table comment
(
  id           bigint auto_increment
  comment '主键
'
    primary key,
  parent_id    bigint             not null
  comment '父类id',
  type         int                not null
  comment '类型',
  commentator  int                null
  comment '评论人id',
  gmt_create   bigint             not null,
  gmt_modified bigint             not null,
  like_count   bigint default '0' null
  comment '点赞数',
  content      varchar(1024)      null
  comment '评论内容'
);
-- auto-generated definition
create table question
(
  id            int auto_increment
    primary key,
  title         varchar(50)     null,
  description   text            null,
  gmt_create    bigint          null,
  gmt_modified  bigint          null,
  creator       int             null,
  comment_count int default '0' null,
  view_count    int default '0' null,
  like_count    int default '0' null,
  tag           varchar(256)    null
);
-- auto-generated definition
create table user
(
  id           int auto_increment
    primary key,
  account_id   varchar(100) null,
  name         varchar(50)  null,
  token        char(36)     null,
  gmt_create   bigint(255)  null,
  gmt_modified bigint(255)  null,
  bio          varchar(255) null,
  avatar_url   varchar(100) null
);
