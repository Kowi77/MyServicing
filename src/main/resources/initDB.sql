/*CREATE SCHEMA  point DEFAULT CHARACTER SET utf8;*/

DROP TABLE IF EXISTS points;

CREATE TABLE points (
  id       integer NOT NULL PRIMARY KEY,
  country varchar(30) NOT NULL,
  sity     varchar(30) NOT NULL,
  adress   varchar(30) NOT NULL,
  name     varchar(50) NOT NULL,
  phone    varchar(18) NOT NULL,
  type     varchar(20) NOT NULL
);

INSERT INTO points (id, country, sity, adress, name, phone, type)
  VALUES (1, 'Russia','Nsk','Pirogova', 'SuperPoint', '8-999-231-33', 'sending');


SELECT * FROM points;




