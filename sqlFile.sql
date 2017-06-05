/*создание базы данных*/
CREATE SCHEMA IF NOT EXISTS avgustbeldb;


/*Создание таблицы "state"*/
CREATE TABLE IF NOT EXISTS avgustbeldb.state (
  id    INT         NOT NULL AUTO_INCREMENT,
  state VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE InnoDB;

INSERT INTO avgustbeldb.state (id, state) VALUES
  ('1', 'зарег.'),
  ('2', 'вызван'),
  ('3', 'прибыл'),
  ('4', 'обслужен'),
  ('5', 'возврат'),
  ('6', 'error'),
  ('7', 'info'),
  ('8', 'въехал'),
  ('9', 'выехал');

/*Создание таблицы "archive_car"*/
CREATE TABLE IF NOT EXISTS avgustbeldb.car_queue (
  id          INT         NOT NULL AUTO_INCREMENT,
  carN        VARCHAR(45) NOT NULL DEFAULT "",
  phoneN      VARCHAR(45) NOT NULL DEFAULT "",
  destination VARCHAR(45) NOT NULL DEFAULT "",
  company     VARCHAR(45) NOT NULL DEFAULT "",
  regTime     VARCHAR(45) NOT NULL DEFAULT "",
  callTime    VARCHAR(45) NOT NULL DEFAULT "",
  stock       VARCHAR(45) NOT NULL DEFAULT "",
  ramp        VARCHAR(45) NOT NULL DEFAULT "",
  arrivedTime VARCHAR(45) NOT NULL DEFAULT "",
  servedTime  VARCHAR(45) NOT NULL DEFAULT "",
  returnTime  VARCHAR(45) NOT NULL DEFAULT "",
  enterTime   VARCHAR(45) NOT NULL DEFAULT "",
  leaveTime   VARCHAR(45) NOT NULL DEFAULT "",
  stateId     INT         NOT NULL,
  UNIQUE INDEX carN_UNIQUE (carN ASC),
  PRIMARY KEY (id),
  FOREIGN KEY (stateId) REFERENCES avgustbeldb.state (id)
)
  ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS avgustbeldb.car_archive (
  id          INT         NOT NULL AUTO_INCREMENT,
  carN        VARCHAR(45) NOT NULL DEFAULT " ",
  phoneN      VARCHAR(45) NOT NULL DEFAULT " ",
  destination VARCHAR(45) NOT NULL DEFAULT " ",
  company     VARCHAR(45) NOT NULL DEFAULT " ",
  regTime     VARCHAR(45) NOT NULL DEFAULT " ",
  callTime    VARCHAR(45) NOT NULL DEFAULT " ",
  stock       VARCHAR(45) NOT NULL DEFAULT " ",
  ramp        VARCHAR(45) NOT NULL DEFAULT " ",
  arrivedTime VARCHAR(45) NOT NULL DEFAULT " ",
  servedTime  VARCHAR(45) NOT NULL DEFAULT " ",
  returnTime  VARCHAR(45) NOT NULL DEFAULT " ",
  enterTime   VARCHAR(45) NOT NULL DEFAULT "",
  leaveTime   VARCHAR(45) NOT NULL DEFAULT "",
  stateId     INT         NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (stateId) REFERENCES avgustbeldb.state (id)
)
  ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS avgustbeldb.users (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL,
  enabled  TINYINT     NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
)
  ENGINE InnoDB;

INSERT INTO avgustbeldb.users (username, password, enabled) VALUES
  ('admin', '$2a$10$Xi/hyIsAQFt7PnmFnLU23.VfMJKRBJwqZwG51QDA6qcgRa2wphrTK', TRUE),
  ('operator_load', '$2a$10$tHDyAjiLNcFyql.NUFcYIuAb0vCxHI0Z1zCmnKTpJ.jRmEGW.Jmdi', TRUE),
  ('operator_unload', '$2a$10$0MtVOylFVHnLbpCN2AqFGuTUxNaLqkQsgCAOOYTDpU1Fi7PMT5NRq', TRUE),
  ('security', '$2a$10$KcaJ4v8aKnfYtRhcEw1anuC5kTbyHxaioMsFqbayoX2Gb4gsC23eC', TRUE);

CREATE TABLE IF NOT EXISTS avgustbeldb.user_roles (
  user_role_id INT(11)     NOT NULL AUTO_INCREMENT,
  username     VARCHAR(45) NOT NULL,
  role         VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role, username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
)
  ENGINE InnoDB;

INSERT INTO avgustbeldb.user_roles (username, role) VALUES
  ('admin', 'ROLE_ADMIN'),
  ('admin', 'ROLE_OPERATOR_LOAD'),
  ('admin', 'ROLE_OPERATOR_UNLOAD'),
  ('admin', 'ROLE_SECURITY'),
  ('operator_load', 'ROLE_OPERATOR_LOAD'),
  ('operator_unload', 'ROLE_OPERATOR_UNLOAD'),
  ('security', 'ROLE_SECURITY');


