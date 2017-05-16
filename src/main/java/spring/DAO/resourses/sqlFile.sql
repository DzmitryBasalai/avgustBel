/*создание базы данных*/
CREATE SCHEMA IF NOT EXISTS avgustbeldb;

/*Создание таблицы "order"*/
CREATE TABLE IF NOT EXISTS avgustbeldb.order (
  id     INT         NOT NULL AUTO_INCREMENT,
  orderN VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);
/*Создание таблицы "stateId"*/
CREATE TABLE IF NOT EXISTS avgustbeldb.state (
  id    INT         NOT NULL AUTO_INCREMENT,
  state VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
) ENGINE InnoDB;

INSERT INTO avgustbeldb.state (id, state) VALUES ('1','зарег.');
INSERT INTO avgustbeldb.state (id, state) VALUES ('2', 'вызван');
INSERT INTO avgustbeldb.state (id, state) VALUES ('3', 'прибыл');
INSERT INTO avgustbeldb.state (id, state) VALUES ('4', 'обслужен');
INSERT INTO avgustbeldb.state (id, state) VALUES ('5', 'возврат');
INSERT INTO avgustbeldb.state (id, state) VALUES ('6', 'уже зарег.');

/*Создание таблицы "archive_car"*/
CREATE TABLE IF NOT EXISTS avgustbeldb.car_queue (
  id          INT         NOT NULL AUTO_INCREMENT,
  carN        VARCHAR(45) NOT NULL,
  phoneN      VARCHAR(45) NOT NULL,
  destination VARCHAR(45) NOT NULL,
  orderN      VARCHAR(45) NOT NULL,
  regTime     VARCHAR(45) NOT NULL,
  callTime    VARCHAR(45) NOT NULL,
  stock     VARCHAR(45) NOT NULL,
  ramp VARCHAR (45) not NULL,
  arrivedTime VARCHAR (45) not NULL,
  servedTime VARCHAR (45) not NULL,
  returnTime VARCHAR (45) not NULL,
  stateId INT not NULL,
  UNIQUE INDEX carN_UNIQUE (carN ASC),
  PRIMARY KEY (id),
  FOREIGN KEY (stateId) REFERENCES avgustbeldb.state(id)
)ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS avgustbeldb.car_archive(
  id          INT         NOT NULL AUTO_INCREMENT,
  carN        VARCHAR(45) NOT NULL,
  phoneN      VARCHAR(45) NOT NULL,
  destination VARCHAR(45) NOT NULL,
  orderN      VARCHAR(45) NOT NULL,
  regTime     VARCHAR(45) NOT NULL,
  callTime    VARCHAR(45) NOT NULL,
  stock     VARCHAR(45) NOT NULL,
  ramp VARCHAR (45) not NULL,
  arrivedTime VARCHAR (45) not NULL,
  servedTime VARCHAR (45) not NULL,
  returnTime VARCHAR (45) not NULL,
  stateId INT not NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (stateId) REFERENCES avgustbeldb.state(id)
)ENGINE InnoDB;
