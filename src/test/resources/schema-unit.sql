/* JUnit実行用 */
CREATE SCHEMA IF NOT EXISTS unit;

/* ユーザー */
CREATE TABLE IF NOT EXISTS unit.users(
	username VARCHAR(50),
	password VARCHAR(500) NOT NULL,
	enabled boolean NOT NULL,
  PRIMARY KEY(username)
);

/* 権限 */
CREATE TABLE IF NOT EXISTS unit.authorities (
	username VARCHAR(50) NOT NULL,
	authority VARCHAR(50) NOT NULL,
	CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username ON unit.authorities (username,authority);

/* データクラス定義 */
CREATE TABLE IF NOT EXISTS unit.dataclass_definitions (
    id INT NOT NULL,
    name VARCHAR(30) NOT NULL,
	type VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

/* データプロパティ定義 */
CREATE TABLE IF NOT EXISTS unit.dataproperty_definitions (
    id INT NOT NULL,
    name VARCHAR(30) NOT NULL,
	type_class_id INT NOT NULL,
    PRIMARY KEY(id)
);

/* データクラス・データプロパティ関係定義 */
CREATE TABLE IF NOT EXISTS unit.dataclass_dataproperty_relation_definitions (
    id INT NOT NULL,
    dataclass_id INT NOT NULL,
	dataproperty_id INT NOT NULL,
    PRIMARY KEY(id)
);

/* データオブジェクト定義 */
CREATE TABLE IF NOT EXISTS unit.dataobject_definitions (
    id INT NOT NULL,
    dataclass_id INT NOT NULL,
    PRIMARY KEY(id)
);

/* データプロパティ・値関係定義 */
CREATE TABLE IF NOT EXISTS unit.dataproperty_value_relation_definitions (
    id INT NOT NULL,
    dataproperty_id INT NOT NULL,
    datavalue_id INT NOT NULL,
	saved_date_time DATETIME NOT NULL,
    PRIMARY KEY(id)
);

/* 値定義 */
CREATE TABLE IF NOT EXISTS unit.datavalue_definitions (
    id INT NOT NULL,
    data_content TEXT NOT NULL,
	saved_date_time DATETIME NOT NULL,
    PRIMARY KEY(id)
);

/* データオブジェクト・データプロパティ・値関係定義 */
CREATE TABLE IF NOT EXISTS unit.dataobject_dataproperty_value_relation_definitions (
    id INT NOT NULL,
	dataobject_id INT NOT NULL,
	dataproperty_value_relation_id INT NOT NULL,
	saved_date_time DATETIME NOT NULL,
    PRIMARY KEY(id)
);
