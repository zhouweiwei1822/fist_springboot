
drop table if exists tbl_user;
CREATE TABLE tbl_user(
id integer not null IDENTITY(1,1) ,
name char(20),
age integer,
primary key(id)
) charset=utf8;
