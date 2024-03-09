/* サーバー起動実行用 */
CREATE SCHEMA IF NOT EXISTS datamanipulation;

/* ユーザー */
CREATE TABLE IF NOT EXISTS datamanipulation.users(
	username VARCHAR(50),
	password VARCHAR(500) NOT NULL,
	enabled boolean NOT NULL,
  PRIMARY KEY(username)
);

/* 権限 */
CREATE TABLE IF NOT EXISTS datamanipulation.authorities (
	username VARCHAR(50) NOT NULL,
	authority VARCHAR(50) NOT NULL,
	CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username ON datamanipulation.authorities (username,authority);

/* データクラス定義 */
CREATE TABLE IF NOT EXISTS datamanipulation.dataclass_definitions (
    id INT NOT NULL,
    name VARCHAR(30) NOT NULL,
	type VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

/* データプロパティ定義 */
CREATE TABLE IF NOT EXISTS datamanipulation.dataproperty_definitions (
    id INT NOT NULL,
    name VARCHAR(30) NOT NULL,
	type_class_id INT NOT NULL,
    PRIMARY KEY(id)
);

