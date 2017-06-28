CREATE TABLE access_level (
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	created DATETIME NOT NULL,
	last_updated DATETIME NOT NULL,
	role_name VARCHAR(32) NOT NULL
);

CREATE TABLE account (
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	created DATETIME NOT NULL,
	last_updated DATETIME NOT NULL,
	username VARCHAR(32) NOT NULL,
	password CHAR(60) NOT NULL,
	email VARCHAR(64) NOT NULL,
	is_email_approved BIT(1) NOT NULL,
	is_blocked BIT(1) NOT NULL,
	al_id BIGINT NOT NULL
);

ALTER TABLE account
	ADD FOREIGN KEY (al_id) REFERENCES access_level(id) ON UPDATE CASCADE ON DELETE RESTRICT;

INSERT INTO access_level (created, last_updated, role_name) VALUES
	(now(), now(), 'GUEST'),
	(now(), now(), 'USER'),
	(now(), now(), 'CONTENT_ADMIN'),
	(now(), now(), 'SERVER_ADMIN'),
	(now(), now(), 'GANDALF');