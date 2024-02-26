CREATE SCHEMA IF NOT EXISTS unit;

CREATE TABLE IF NOT EXISTS unit.users(
	username VARCHAR(50),
	password VARCHAR(500) NOT NULL,
	enabled boolean NOT NULL,
  PRIMARY KEY(username)
);

CREATE TABLE IF NOT EXISTS unit.authorities (
	username VARCHAR(50) NOT NULL,
	authority VARCHAR(50) NOT NULL,
	CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username ON unit.authorities (username,authority);

CREATE TABLE IF NOT EXISTS unit.dataclass_definitions (
    id INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

